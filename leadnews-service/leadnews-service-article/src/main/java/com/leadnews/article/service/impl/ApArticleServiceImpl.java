package com.leadnews.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leadnews.article.dto.ArticleBehaviourDtoQuery;
import com.leadnews.article.dto.ArticleHomeDto;
import com.leadnews.article.dto.ArticleInfoDto;
import com.leadnews.article.dto.ArticleVisitStreamMess;
import com.leadnews.article.mapper.ApArticleConfigMapper;
import com.leadnews.article.mapper.ApArticleContentMapper;
import com.leadnews.article.mapper.ApCollectionMapper;
import com.leadnews.article.pojo.ApArticle;
import com.leadnews.article.mapper.ApArticleMapper;
import com.leadnews.article.pojo.ApArticleConfig;
import com.leadnews.article.pojo.ApArticleContent;
import com.leadnews.article.pojo.ApCollection;
import com.leadnews.article.service.ApArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.article.vo.ArticleInfoVo;
import com.leadnews.article.vo.HotArticleVo;
import com.leadnews.behaviour.feign.ApBehaviorEntryFeign;
import com.leadnews.behaviour.feign.ApLikesBehaviorFeign;
import com.leadnews.behaviour.feign.ApUnlikesBehaviorFeign;
import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.pojo.ApLikesBehavior;
import com.leadnews.behaviour.pojo.ApUnlikesBehavior;
import com.leadnews.common.constants.BC;
import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import com.leadnews.common.vo.PageResultVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.feign.ApUserFollowFeign;
import com.leadnews.user.pojo.ApUserFollow;
import com.leadnews.wemedia.feign.WmNewsFeign;
import com.leadnews.wemedia.pojo.WmNews;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description <p>已发布的文章信息 业务实现</p>
 * 这是view database，视图数据库是为了查询使用，有很多张表，但某张表的其中一行记录line item并不是vo对象
 * vo指的是一个聚合中不可变的部分，不包括可变的部分。
 * @version 1.0
 * @package com.leadnews.article.service.impl
 */
@Service
@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    @Resource
    private ApArticleContentMapper apArticleContentMapper;

    @Resource
    private ApArticleConfigMapper apArticleConfigMapper;

    @Resource
    private WmNewsFeign wmNewsFeign;

    /*---------------------day08_分布式任务与文章信息同步------------------------------------*/

    @Override
    @Transactional
    public void saveArticleInfo(ArticleInfoDto articleInfoDto) {
        ApArticle apArticle = articleInfoDto.getApArticle();
        // articleInfoDto第一次同步是没有id的。后续修改状态时的同步传递的articleid就有值了。在admin组装的时候专门设置了
        // 判断是新发表的文章还是下架修改之后的文章
        Long oldArticleId = apArticle.getId();
        log.debug("保存文章信息,oldArticleId={}", oldArticleId);
        if (null != oldArticleId) {
            deleteOldArticle(oldArticleId);
        }

        apArticle.setId(null);// 为了让添加数据时 mybatisplus能够生成新的id
        save(apArticle); // 同步保存进去的过程是新增，不是更新，不能放id

        Long newArticleId = apArticle.getId();
        log.debug("保存文章信息,newArticleId={}", newArticleId);

        ApArticleContent apArticleContent = articleInfoDto.getApArticleContent();
        apArticleContent.setId(newArticleId);
        apArticleContentMapper.insert(apArticleContent);
        log.debug("保存文章内容,newArticleId={}", newArticleId);


        ApArticleConfig apArticleConfig = articleInfoDto.getApArticleConfig();
        apArticleConfig.setId(newArticleId);
        apArticleConfigMapper.insert(apArticleConfig);
        log.debug("保存文章配置,newArticleId={}", newArticleId);


        // 文章同步：三、更新wmnews的文章状态
        WmNews updatePojo = new WmNews();
        updatePojo.setArticleId(newArticleId);
        updatePojo.setStatus(BC.WmNewsConstants.STATUS_PUBLISHED);
        updatePojo.setId(articleInfoDto.getWmNewsId());//更新条件

        ResultVo updateResult = wmNewsFeign.updateWmNews(updatePojo);
        if (!updateResult.isSuccess()) {
            log.error("远程调用自媒体更新文章状态失败:" + updateResult.getErrorMessage());
            throw new LeadNewsException("远程调用自媒体更新文章状态失败:" + updateResult.getErrorMessage());
        }
    }



    /**
     * 文章微服添加文章数据前要判断是否有文章id,有则要删除旧数据
     * 把旧文章标记为已删除
     * @param oldArticleId
     */
    private void deleteOldArticle(Long oldArticleId) {
        ApArticleConfig updatePojo = new ApArticleConfig();
        updatePojo.setId(oldArticleId);
        //updatePojo.setIsDown(1);// 上下架的状态不在读服务管理。后面同步上下架操作时，会专门传来旧id下架！
        // 具体上不上架需要重新走一遍审核流程！上架指令会在wnnews被改为submit状态后重新审核，再来判断！
        // 所以只有下架指令会被传递状态，上架指令可以下达，但是不会被传递状态。
        // 所以读服务只关心是否下架！
        updatePojo.setIsDelete(1); // 已删除
        apArticleConfigMapper.updateById(updatePojo);
    }



    /*---------------------day09_app端基本功能展示------------------------------------*/
    /**
     * APP端深度分页查询
     * @param dto
     * @return
     */
    @Override
    public PageResultVo<ApArticle> findPage(ArticleHomeDto dto, int loadType) {
        dto.checkSize();
        // 用BaseMapper查询，只查历史结果，不需要进行分页。
        List<ApArticle> list = getBaseMapper().findPage(dto, loadType);
        return PageResultVo.pageResult(dto.getLoaddir(), dto.getSize(), 1000L, list);
    }



    /**
     * 加载文章 详情
     * @param paramMap 请求参数只有1个，就是articleId，并没有放路径当中（@PathVariable)，
     *                 而是放在了载荷中(@RequestBody)，所以这里用map来接收的。
     * @return
     */
    @Override
    public ArticleInfoVo loadArticleInfo(Map<String, Long> paramMap) {
        /**
         * 方法一：在2张表查询1次，数据量小的时候适合，因为设计到sql的join
         */
        //1. 获取文章的id
        Long articleId = paramMap.get("articleId"); // 不是article_id
        ArticleInfoVo vo = baseMapper.loadArticleInfo(articleId);
        return vo;

        /**
         * 方法二：在2张表查询2次，数据量大的时候适合
         */
        /*
        //2. 查询文章配置表
        ApArticleConfig apArticleConfig = apArticleConfigMapper.selectById(articleId);
        if(null == apArticleConfig){
            throw new LeadNewsException("文章不存在!");
        }
        //3. 判断是否下架或是否删除
        if(apArticleConfig.getIsDown()==BC.ArticleConstants.IS_DOWN){
            throw new LeadNewsException("文章已下架!");
        }
        if(apArticleConfig.getIsDelete()==BC.ArticleConstants.IS_DELETE){
            throw new LeadNewsException("文章已删除!");
        }
        //4. 查询文章内容
        ApArticleContent content = apArticleContentMapper.selectById(articleId);

        //5. 组装vo返回
        ArticleInfoVo vo = new ArticleInfoVo();
        vo.setConfig(apArticleConfig);
        vo.setContent(apArticleContent);

        return vo;*/
    }




    /*-------------------day10用户关系行为展示------------------------------*/
    /**
     * 加载用户对文章的行为
     * @param dto
     * @return
     */
    @Override
    public Map<String, Boolean> loadArticleBehaviour(ArticleBehaviourDtoQuery dto) {
        /**
         * {
         *   "islike": false, //是否点赞
         *   "isunlike": false, //是否不喜欢
         *   "iscollection": false, // 是否收藏
         *   "isfollow": false //是否关注了作者
         * }
         */
        // 默认都为false
        boolean islike = false;
        boolean isunlike = false;
        boolean iscollection = false;
        boolean isfollow = false;
        //1. 查询行为实体，先找人！
        ApBehaviorEntry entry = queryEntry(dto.getEquipmentId());
        if(null != entry){ // 人存在才去查
            //2. 查询是否不喜欢
            isunlike = queryIsUnlike(dto.getArticleId(),entry.getId().intValue());
            //3. 查询是否关注
            isfollow = queryIsFollow(RequestContextUtil.getUserId().toString(), dto.getAuthorId());
            //4. 查询是否点赞
            islike = queryIsLike(dto.getArticleId(),entry.getId().intValue());
            //5. 查询是否收藏
            iscollection = queryIsCollection(dto,entry.getId().intValue());
        }

        Map<String, Boolean> resultMap = new HashMap<String,Boolean>();
        resultMap.put("islike",islike);
        resultMap.put("isunlike",isunlike);
        resultMap.put("iscollection",iscollection);
        resultMap.put("isfollow",isfollow);
        return resultMap;
    }

    @Autowired
    private ApBehaviorEntryFeign apBehaviorEntryFeign;

    /**
     * 查询行为实体
     * @param eqId
     * @return
     */
    private ApBehaviorEntry queryEntry(Integer eqId){
        // 判断操作实体是匿名还是登录用户
        Long loginUserId = RequestContextUtil.getUserId();
        // 假设实体为当前登录用户id
        Long entryId = loginUserId;
        // 假设实体类型为用户
        int type = SC.TYPE_USER;
        if(SC.ANONYMOUS_USER_ID == loginUserId){
            //如果是匿名，则实体类型为设备
            type = SC.TYPE_E;
            //那么相应的，实体Id就为设备的id
            entryId = eqId.longValue();
        }
        ResultVo<ApBehaviorEntry> entryResult = apBehaviorEntryFeign.findBehaviorEntryByEntryIdAndType(entryId, type);
        if (!entryResult.isSuccess()) {
            throw new LeadNewsException("查询用户行为失败!");
        }
        return entryResult.getData();
    }

    @Autowired
    private ApLikesBehaviorFeign apLikesBehaviorFeign;
    /**
     * 查询是否点赞
     * @param articleId
     * @param id
     * @return
     */
    private boolean queryIsLike(Long articleId, Integer id) {
        ResultVo<ApLikesBehavior> result = apLikesBehaviorFeign.findApLikesByUserIdOrEquipmentId(articleId, id);
        if(!result.isSuccess()){
            throw new LeadNewsException("查询用户行为失败!");
        }
        ApLikesBehavior data = result.getData();
        if(null != data && data.getOperation() == 1){ //1为点赞
            return true;
        }
        return false;
    }


    @Autowired
    private ApUnlikesBehaviorFeign apUnlikesBehaviorFeign;
    /**
     * 查询用户是否 不喜欢文章
     * @param articleId
     * @param entryId
     * @return
     */
    private boolean queryIsUnlike(Long articleId, Integer entryId){
        if (null == articleId || null == entryId) {
            throw new LeadNewsException("~~~~~~~~~~~~~articleId 或entryId为空");
        }
        long aId = articleId.longValue();
        int eid = entryId.intValue();
        ResultVo<ApUnlikesBehavior> result = apUnlikesBehaviorFeign.findApUnlikesByEntryIdAndArticleId(aId, eid);
        if(!result.isSuccess()){
            throw new LeadNewsException("查询用户行为失败!");
        }
        ApUnlikesBehavior data = result.getData();
        // type=0: 不喜欢，1=取消不喜欢
        if(null == data || data.getType() != 0){
            //没找到数据，即用户没有做过这种行为，则默认为 没有不喜欢
            return false;
        }
        //type=0
        return true;
    }

    @Autowired
    private ApUserFollowFeign apUserFollowFeign;
    /**
     * 查询当前用户是否关注了作者
     * @param loginUserId
     * @param authorId
     * @return
     */
    private boolean queryIsFollow(String loginUserId, Integer authorId) {
        int userId = Integer.parseInt(loginUserId);
        ResultVo<ApUserFollow> result = apUserFollowFeign.findUserFollowByUserIdFollowId(Integer.parseInt(loginUserId),authorId);
        if(!result.isSuccess()){
            throw new LeadNewsException("查询用户行为失败!");
        }
        ApUserFollow data = result.getData();
        if(null != data){
            // 有数据即为关注，因此取消关注时，会删除数据
            return true;
        }
        return false;
    }

    @Autowired
    private ApCollectionMapper apCollectionMapper;
    /**
     * 查询是否收藏
     * @param dto
     * @param id
     * @return
     */
    private boolean queryIsCollection(ArticleBehaviourDtoQuery dto, Integer id) {
        LambdaQueryWrapper<ApCollection> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ApCollection::getArticleId,dto.getArticleId())
                .eq(ApCollection::getEntryId,id)
                .eq(ApCollection::getType,0);
        return apCollectionMapper.selectCount(lqw)>0;
    }



    /*----------------------day12_新热文章计算-----------------------------*/
    /**
     * 热点文章
     */
    @Override
    public void computeAndSave2Redis() {

        List<ApArticle> articleList = list5DaysBefore();

        if (!CollectionUtils.isEmpty(articleList)) {

            List<HotArticleVo> voList = articleList.stream()
                    .map(article -> HotArticleVo.build(article, computeScore(article))).collect(Collectors.toList());

            Map<Integer, List<HotArticleVo>> channelMap = voList.stream().collect(Collectors.groupingBy(ApArticle::getChannelId));

            Set<Integer> channelIds = channelMap.keySet();

            for (Integer channelId : channelIds) {

                List<HotArticleVo> hotArticleVoList = channelMap.get(channelId);

                if (!CollectionUtils.isEmpty(hotArticleVoList)) {

                    List<HotArticleVo> targetChannelHotArticleList = hotArticleVoList.stream()
                            .sorted(Comparator.comparing(HotArticleVo::getScore).reversed())
                            .limit(30).collect(Collectors.toList());

                    save2Redis(channelId, targetChannelHotArticleList);
                }
            }

            List<HotArticleVo> recommendVoList = voList.stream()
                    .sorted(Comparator.comparing(HotArticleVo::getScore).reversed())
                    .limit(30).collect(Collectors.toList());

            save2Redis(BC.ArticleConstants.DEFAULT_TAG, recommendVoList);
        }
    }



    /**
     * 查询前5天的文章
     * @return
     */
    private List<ApArticle> list5DaysBefore(){
        LocalDate fiveDaysBefore = LocalDate.now().minusDays(5);
        List<ApArticle> list = getBaseMapper().selectAfterDate(fiveDaysBefore);
        return list;
    }


    /**
     * 计算文章具体分值
     *
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        Integer score = 0;

        if (apArticle.getLikes() != null) {
            score += apArticle.getLikes() * BC.ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }

        if (apArticle.getViews() != null) {
            score += apArticle.getViews();
        }

        if (apArticle.getComment() != null) {
            score += apArticle.getComment() * BC.ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }

        if (apArticle.getCollection() != null) {
            score += apArticle.getCollection() * BC.ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }

        return score;
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 把文章存入redis的zset, key=文章的频道，如果是推荐，则key为__all__
     * @param channelId
     * @param
     */
    private void save2Redis(Serializable channelId, List<HotArticleVo> targetChannelHotArticleList) {

        String key = BC.ArticleConstants.HOT_ARTICLE_FIRST_PAGE + channelId;
        for (HotArticleVo hotArticleVo : targetChannelHotArticleList) {
            stringRedisTemplate.opsForZSet().add(key, hotArticleVo.getId()+"", hotArticleVo.getScore());
        }

        // 下面的写法也可以
/*        Set<ZSetOperations.TypedTuple<String>> kvSet = targetChannelHotArticleList.stream()
                .map(vo -> new DefaultTypedTuple<String>(vo.getId().toString(), vo.getScore().doubleValue()))
                .collect(Collectors.toSet());
        stringRedisTemplate.opsForZSet().add(key, kvSet);*/
    }



    /**
     * 文章行为数据更新 重新算分
     *
     * @param articleVisitStreamMess
     */
    @Override
    public void computeAndSave2RedisSingle(ArticleVisitStreamMess articleVisitStreamMess) {
        //1. 更新文章中的行为数量
        // 必须使用自增 view = view+num
        getBaseMapper().updateNumber(articleVisitStreamMess);
        //2. 重新算分
        ApArticle apArticle = getById(articleVisitStreamMess.getArticleId());
        int score = computeScore(apArticle) * 3;// 原因：让新文章比较容易的超越旧有的热点文章
        //3. 更新到redis里
        // 频道, 优化: 查询第30名的分数
        String key = BC.ArticleConstants.HOT_ARTICLE_FIRST_PAGE + apArticle.getChannelId();
        stringRedisTemplate.opsForZSet().add(key,apArticle.getId()+"",score);
        // 推荐 优化: 查询第30名的分数
        key = BC.ArticleConstants.HOT_ARTICLE_FIRST_PAGE + BC.ArticleConstants.DEFAULT_TAG;
        stringRedisTemplate.opsForZSet().add(key,apArticle.getId()+"",score);
    }
}
