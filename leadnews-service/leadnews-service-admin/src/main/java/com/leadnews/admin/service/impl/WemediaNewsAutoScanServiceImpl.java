package com.leadnews.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import com.leadnews.admin.mapper.AdChannelMapper;
import com.leadnews.admin.mapper.AdSensitiveMapper;
import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.admin.pojo.AdSensitive;
import com.leadnews.admin.service.WemediaNewsAutoScanService;
import com.leadnews.article.dto.ArticleInfoDto;
import com.leadnews.article.feign.ApArticleFeign;
import com.leadnews.article.pojo.ApArticle;
import com.leadnews.article.pojo.ApArticleConfig;
import com.leadnews.article.pojo.ApArticleContent;
import com.leadnews.common.constants.BC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.ali.AliGreenScanner;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.dfs.feign.DfsFeign;
import com.leadnews.wemedia.dto.WmNewsContentNodeDto;
import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.feign.WmNewsFeign;
import com.leadnews.wemedia.pojo.WmNews;
import com.leadnews.wemedia.vo.WmNewsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WemediaNewsAutoScanServiceImpl implements WemediaNewsAutoScanService {
    @Resource
    private WmNewsFeign wmNewsFeign;

    @Resource
    private ApArticleFeign apArticleFeign;

    @Resource
    private AdSensitiveMapper adSensitiveMapper;

    //@Resource 这里跳过图片扫描
    private AliGreenScanner aliGreenScanner;

    @Resource
    private DfsFeign dfsFeign;

    @Resource
    private AdChannelMapper adChannelMapper;


    /*--------------------day07_自媒体文章审核-------------------------------*/

    /**
     * 自媒体文章自动审核
     * 外层AutoScanListener调用的此方法，在Listener中一经做了try catch。所以调用阿里文本失败、图片失败的异常可以往出抛，
     * 因为它们都是阿里失败。都在Listener当中处理了。所以在最后可以不做try catch 捕获。
     * @param wmNewsId
     * @param uuid
     */
    @Override
    public void autoScanWemediaNewsById(long wmNewsId, String uuid) {
        //手上只有文章id，任务确实大到自动审核文章，想步骤
        // 1 远程调用取得文章
        ResultVo<WmNews> getResult = wmNewsFeign.getWmNewsById(wmNewsId);
        if (!getResult.isSuccess()) {
            log.error("远程调用自媒体微服务查询文章信息失败!{}",uuid);
            throw new LeadNewsException("远程调用自媒体微服务查询文章信息失败!");
        }
        WmNews wmNews = getResult.getData();/*原对象*/
        if (null == wmNews) {
            log.error("远程调用自媒体查询文章信息失败! 文章信息不存在! {}", uuid);
            throw new LeadNewsException("远程调用自媒体查询文章信息失败! 文章信息不存在!");
        }
        //Optional.ofNullable(getResult.getData()).orElseThrow(() -> new LeadNewsException("远程调用自媒体查询文章信息失败! 文章信息不存在!"));

        // 幂等性。这步在业务逻辑上是值得讨论的的关键！
        if (wmNews.getStatus() != BC.WmNewsConstants.STATUS_SUBMIT) {
            log.debug("文章状态({})不为待审核，此次不处理! {}", wmNews.getStatus(), uuid);
            return;
        }

        // 2 提取文章内容过敏感词审核，再过阿里审核
        String titleText = null;
        String content = wmNews.getContent();
        WmNews updatePojo = new WmNews(); // 既然用的是updatePojo，肯定是之前已经根据id获得原对象了
        updatePojo.setId(wmNewsId);

        if (!StringUtils.isEmpty(content)) {
            System.out.println("~~~~~~~~~~~文章内容不为空");
            List<WmNewsContentNodeDto> nodeDtoList = JSON.parseArray(content, WmNewsContentNodeDto.class);
            String text = nodeDtoList.stream()
                    .filter(dto -> !BC.WmNewsConstants.CONTENT_IMAGE.equals(dto.getType()))
                    .map(WmNewsContentNodeDto::getValue)
                    .collect(Collectors.joining(","));
            System.out.println("~~~~~~~~~~~~~~~文章文本：" + text);
            if (!StringUtils.isEmpty(text)) {
                titleText=wmNews.getTitle()+","+text;

                boolean containSensitive = sensitiveWordsDetect(titleText);
                System.out.println("文章包含敏感词吗？" + containSensitive);
                if(containSensitive){
                    log.debug("文章包含敏感词!{}",uuid); // 打日志：流程性质的用info，涉及关键词的用debug
                    //wmNews.setStatus(BC.WmNewsConstants.STATUS_FAIL);
                    //admin中能设置从wmmedia过来的对象属性吗？好像不行。。。不行！可以设置值，但没办法保存到人家的数据库！
                    //只能用更新pojo的办法，远程调用update到数据库
                    updatePojo.setStatus(BC.WmNewsConstants.STATUS_FAIL);
                    updatePojo.setReason("文章包含敏感词!");

                }
/*                else {
                    try {
                        // 3 提取文章图片过阿里审核
                        Map<String, String> stringStringMap = aliGreenScanner.greenTextScan(titleText);
                        log.debug("调用阿里文本反垃圾结果:uuid({}),({})", uuid,JSON.toJSONString(stringStringMap));
                        if(null == stringStringMap) {
                            throw new LeadNewsException("调用阿里文本反垃圾失败，文本为空");
                        }
                        if(BC.ScanConstants.PASS.equals(stringStringMap.get("suggestion"))) {
                            List<byte[]> picByteList = getImageUrlByteList(wmNews); // 这里得到的就是下载成功的图片字节码列表了。
                            log.debug("获取文章中的图片，去重后，共{}张. {}", picByteList.size(),uuid);

                            // 4 解析审核结果并写入数据库
                            if (!CollectionUtils.isEmpty(picByteList)) {
                                try {
                                    Map<String, String> imageScan = aliGreenScanner.imageScan(picByteList);
                                    log.debug("调用阿里图片反垃圾结果:uuid({}),({})", uuid, JSON.toJSONString(imageScan));

                                    String suggestionResult = imageScan.get("suggestion");
                                    switch (suggestionResult) {
                                        case BC.ScanConstants.PASS:
                                            updatePojo.setStatus(BC.WmNewsConstants.STATUS_PASS); break;
                                        case BC.ScanConstants.REVIEW:
                                            updatePojo.setStatus(BC.WmNewsConstants.STATUS_MANUAL);
                                            updatePojo.setReason(imageScan.get("label")); break;
                                        case BC.ScanConstants.BLOCK:
                                            updatePojo.setStatus(BC.WmNewsConstants.STATUS_BLOCK);
                                            updatePojo.setReason(imageScan.get("label")); break;
                                    }
                                } catch (Exception e) {
                                    log.error("调用阿里图片反垃圾失败!{}",e,uuid);
                                    throw new LeadNewsException("调用阿里图片反垃圾失败!");
                                }
                            }
                        }
                    } catch (Exception e) {
                        throw new LeadNewsException("~~~~~~~~~~~~~~~~~~~此处为自己调试，跳过阿里文本和图片反垃圾~~~~~~~~~~~~~~");
                        //throw new LeadNewsException("调用阿里文本反垃圾失败");
                    }
                }*/

            }
        }

        System.out.println("---中间代码为了调试，跳过阿里文本和图片反垃圾---");
        updatePojo.setStatus(BC.WmNewsConstants.STATUS_PASS);
        updatePojo.setEnable(1); // 一旦审核通过状态为9，就默认是上架状态
        System.out.println("---中间代码为了调试，跳过阿里文本和图片反垃圾---");

        // 设置更新条件。在刚刚建立updatePojo对象时已经设置news_id了，因此更新对象有id，可以传到wmnews微服务去根据id做更新
        ResultVo updateResult = wmNewsFeign.updateWmNews(updatePojo);

        if (!updateResult.isSuccess()){
            throw new LeadNewsException("远程调用自媒体更新文章状态失败!");
        }
        log.info("远程调用自媒体更新文章状态成功,{}",uuid);
    }


    /**
     * 敏感词检测
     * @param text
     * @return
     */
    private boolean sensitiveWordsDetect(String text) {
        // 构建DFA
        // 1. 查询所有敏感词。把AdSensitive类中敏感词的字符串取出来，组成map
        List<AdSensitive> sensitiveList = adSensitiveMapper.selectList(null); // 查询所有
        Map<String, String> map = sensitiveList.stream().map(AdSensitive::getSensitives)
                .collect(Collectors.toMap(Function.identity(), Function.identity())); // Function.identity()表示对流中的每个元素不进行任何操作，直接返回原始值。
        // 2. 初始化
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(map);
        // 3. 检测敏感词
        return acdat.findFirst(text)!=null;
    }


    private List<byte[]> getImageUrlByteList(WmNews wmNews) {
        Set<String> picUrlSet = new HashSet<>();
        String images = Optional.ofNullable(wmNews.getImages()).orElseThrow(() -> new LeadNewsException(""));
        picUrlSet.addAll(Arrays.asList(images.split(",")));

        String content = Optional.ofNullable(wmNews.getContent()).orElseThrow(() -> new LeadNewsException(""));
        List<WmNewsContentNodeDto> nodeDtoList = JSON.parseArray(content, WmNewsContentNodeDto.class);
        Set<String> contentSet = nodeDtoList.stream()
                .filter(node -> BC.WmNewsConstants.CONTENT_IMAGE.equals(node.getType()))
                .map(WmNewsContentNodeDto::getValue)
                .collect(Collectors.toSet());

        picUrlSet.addAll(contentSet);

        // 得到图片之后需要下载。因为传递给阿里的参数需要是图片的字节码，而不是url链接
        ResultVo<List<byte[]>> downloadResult = dfsFeign.download(picUrlSet);
        if (!downloadResult.isSuccess()) {
            throw new LeadNewsException("远程调用文件微服下载图片失败");
        }
        List<byte[]> picByteList = downloadResult.getData();
        return picByteList;
    }



    /*---------------------day08_分布式任务与文章信息同步-----------------------*/
    /**
     * 需要同步的文章
     *
     * @param dto
     * @return
     */
    @Override
    public List<WmNewsVo> list4ArticleSync(WmNewsPageReqDto dto) {
        ResultVo<List<WmNewsVo>> syncListResult = wmNewsFeign.list4ArticleSync(dto);

        if (!syncListResult.isSuccess()) {
            log.error("远程调用自媒体微服务获取审核通过文章列表失败"+syncListResult.getErrorMessage());
            throw new LeadNewsException("远程调用自媒体微服务获取审核通过文章列表失败"+syncListResult.getErrorMessage());
        }

        return syncListResult.getData();
    }


    /**
     * 同步文章数据
     *
     * @param wmNewsVo
     * @return
     */
    @Override
    public void syncArticleInfo(WmNewsVo wmNewsVo) {

        ArticleInfoDto articleInfoDto = buildArticleAggregate(wmNewsVo);

        ResultVo saveArticleResult = apArticleFeign.saveArticleInfo(articleInfoDto);

        if (!saveArticleResult.isSuccess()) {
            log.error("远程调用文章微服保存文章信息失败:" + saveArticleResult.getErrorMessage());
            throw new LeadNewsException("远程调用文章微服保存文章信息失败:" + saveArticleResult.getErrorMessage());
        }
    }



    public ArticleInfoDto buildArticleAggregate(WmNewsVo wmNewsVo) { // ArticleAggregate集成了三个子类，也不算Aggregate，因为Article不是root
        ArticleInfoDto articleInfoDto = new ArticleInfoDto();

        articleInfoDto.setApArticle(buildApArticle(wmNewsVo));
        articleInfoDto.setApArticleContent(buildApArticleContent(wmNewsVo));
        articleInfoDto.setApArticleConfig(buildApArticleConfig(wmNewsVo));
        // 设置回调的news_id
        articleInfoDto.setWmNewsId(wmNewsVo.getId());

        return articleInfoDto;
    }

    private ApArticle buildApArticle(WmNewsVo wmNewsVo) {
        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(wmNewsVo, apArticle,"id"); //因为wmNews的id是新闻id，而apArticle的id是文章id，二者不同。排除了id属性

        //articleId保存之后取得，之后设置wmnews，其他两个的id在这之后才能取得
        // 设置旧有文章的id, 新闻的文章id  wm_news.article_id， 修改后重新发布
        //apArticle.setId(wmNewsVo.getArticleId()); //Vo哪里来的文章id，先有哪个对象的文章id？

        /* 文章微服添加文章数据前要判断是否有文章id,有则要删除旧数据
         * 把旧文章标记为已删除*/
/*        Long oldArticleId = apArticle.getId();
        if (null != oldArticleId) {
            deleteOldArticle(oldArticleId);
        }
        apArticle.setId(null);*/

        // 下面判断要发布的是新发布的文章还是下架修改的文章
        if (null != wmNewsVo.getArticleId()) { //下架修改的文章是存在文章id的
            apArticle.setId(wmNewsVo.getArticleId()); // 特殊处理article对象的id
        }

        apArticle.setAuthorId(wmNewsVo.getUserId());

        AdChannel adChannel = adChannelMapper.selectById(wmNewsVo.getChannelId());
        apArticle.setChannelName(adChannel.getName());

        if(BC.WmNewsConstants.TYPE_MULTIPLE_IMG == wmNewsVo.getType()){
            apArticle.setLayout(2);// 代表多图
        }else {
            apArticle.setLayout(wmNewsVo.getType());
        }

        apArticle.setFlag(BC.ArticleConstants.FLAG_NORMAL);// 普通文章
        apArticle.setLikes(0); // 点赞数
        apArticle.setCollection(0);// 收藏数
        apArticle.setComment(0);// 评论数
        apArticle.setViews(0); // 阅读数
        apArticle.setSyncStatus(0);// 未同步给ES
        apArticle.setOrigin(BC.ArticleConstants.ORIGIN_WEMEDIA);

        return apArticle;

    }

    private ApArticleContent buildApArticleContent(WmNewsVo wmNewsVo) {

        ApArticleContent content = new ApArticleContent();
        content.setContent(wmNewsVo.getContent());
        return content;
    }

    private ApArticleConfig buildApArticleConfig(WmNewsVo wmNewsVo) {

        ApArticleConfig config = new ApArticleConfig();
        config.setIsDelete(0);// 未删除
        config.setIsDown(0); // 未下架
        config.setIsComment(1);// 是否允许评论
        config.setIsForward(1);//是否允许转发
        return config;

    }

}