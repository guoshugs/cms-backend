package com.leadnews.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leadnews.common.constants.BC;
import com.leadnews.common.enums.HttpCodeEnum;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import com.leadnews.common.vo.PageResultVo;
import com.leadnews.wemedia.dto.*;
import com.leadnews.wemedia.mapper.WmMaterialMapper;
import com.leadnews.wemedia.mapper.WmNewsMaterialMapper;
import com.leadnews.wemedia.mapper.WmUserMapper;
import com.leadnews.wemedia.pojo.WmMaterial;
import com.leadnews.wemedia.pojo.WmNews;
import com.leadnews.wemedia.mapper.WmNewsMapper;
import com.leadnews.wemedia.pojo.WmNewsMaterial;
import com.leadnews.wemedia.pojo.WmUser;
import com.leadnews.wemedia.service.WmNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.wemedia.vo.WmNewsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.leadnews.common.constants.BC.WmNewsConstants.ENABLE_ON;

/**
 * @version 1.0
 * @description <p>自媒体图文内容信息 业务实现</p>
 * @package com.leadnews.wemedia.service.impl
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Resource
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Resource
    private WmMaterialMapper wmMaterialMapper;

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Resource
    private WmUserMapper wmUserMapper;


    /*-------------------------day05_自媒体文章发布-------------------------------*/

    /* 先看封面图片空不空
     *        不空：images可以，content不用动
     *        空  ：再看内容图片空不空+有几张
     *                                   大于2张，封面图片取前三
     *                                   大于0张，封面图片取第一张
     *                                   内容图片也为空，存空字符串*/
    /**
     * 发表文章提交：分成2个阶段：
     * 1. 保存数据到wm_news表
     * 2. 添加文章与素材关系wm_news_material
     * @param draft
     * @param dto
     */
    @Override
    @Transactional
    public void submit(WmNewsDtoSave dto, boolean draft) {
        /*--1. 构建接收对象--*/
        WmNews pojo = new WmNews();
        BeanUtils.copyProperties(dto, pojo);
        pojo.setUserId(RequestContextUtil.getUserId());
        if (null == dto.getId()) {
            pojo.setCreatedTime(LocalDateTime.now());
        }
        //前端和后台对草稿的定义有混淆：存草稿前端传过来的是参数是true，status是0；提交前端传过来的参数是false，status=1。别用draft了。自己重新定义
        if (dto.getStatus() == 0) {
            draft = true;
            pojo.setStatus(BC.WmNewsConstants.STATUS_DRAFT);
        } else {
            draft = false;
            pojo.setStatus(BC.WmNewsConstants.STATUS_SUBMIT);
            pojo.setSubmitedTime(LocalDateTime.now());//其实是更新时间
        }

        // 发布时间给予默认为当前时间
        if (null == dto.getPublishTime()) {
            pojo.setPublishTime(LocalDateTime.now());
        }

        // images前端传过来的是数组，db中存储是String
        /*Optional.ofNullable(dto.getImages())
                  .ifPresent(list->pojo.setImages(list.stream().collect(Collectors.joining(","))));*/
        /* 加上ifPresent，就可以利用得到的对象进一步再操作。如果不加ifPresent，就可以抛出异常直接得到对象。看最后要的是什么，是操作还是对象 */
        if (!CollectionUtils.isEmpty(dto.getImages())) {
            // 有封面,则以逗号分割拼接起来
            String images = String.join(",", dto.getImages());
            pojo.setImages(images);
        }

        // type封面处理, 当选择为自动封面时需要处理，自动时值为-1
        // 提取内容中的图片。这里并没有上传的步骤，因为上传在文章发表之前完成了，写draft时使用的是已经上传完毕陈列好的图片，用的是url地址。而陈列图片列表（上一步）响应回来的是完整的图片全部信息，其实前端完全能接收到。但是前端在下一步提交传给后端时只给了完整路径。
        List<String> imageList = getImagesFromContent(dto.getContent()); // 拿到判断语句外面，多处都会使用
        if (dto.getType() == BC.WmNewsConstants.TYPE_AUTO) {
            // 根据图片的数量设置type
            int count = imageList.size();
            if (count > 2) {
                pojo.setType(BC.WmNewsConstants.TYPE_MULTIPLE_IMG);// 多图
                // 设置封面中的图片, 取内容图片的前3张,并以逗号拼接
                pojo.setImages(imageList.stream().limit(3).collect(Collectors.joining(",")));
                /*stream()方法获取流之后，原始的 List不会发生改变。Stream流提供了一种对集合进行流式操作的方式，
                它并不会修改原始集合的内容。流操作通常是通过中间操作和终端操作来完成的，中间操作会生成一个新的流，
                而不会修改原始集合，而终端操作会对流进行最终的处理，例如收集元素、计算结果等。
                最后必须用一个同样的集合来接收，不然就没有了。*/
            } else if (count > 0) {
                pojo.setType(BC.WmNewsConstants.TYPE_SINGLE_IMG);// 单图
                pojo.setImages(imageList.get(0));
            } else {
                pojo.setType(BC.WmNewsConstants.TYPE_NONE_IMG);//无图
            }
        }


        /*--2. 执行save操作，保存到WmNews表：如果没有id，说明是第一次draft，就保存；如果有id，说明是有审核结果，需要修改--*/
        if (null == dto.getId()) { // 添加关系之前判断是否是新增，因为修改需要删除旧关系
            pojo.setType(dto.getType());  //重设type恢复成包括自动
            save(pojo); // save之后才能得到news_id

        } else {//说明更新，在WmNews表需要重新设置每一项pojo属性。再从WmNewsMaterial表中删除每一条映射news_id的数据。然后进入下面阶段，重新添加映射关系
            // 删除任何东西之前都需要先校验是否是自己的
            WmNews oldNews = getById(dto.getId());

            if (oldNews.getUserId() != RequestContextUtil.getUserId()) {
                throw new LeadNewsException("只能更新自己的文章!");
            }

            // 更新
            pojo.setType(dto.getType()); //重设type恢复成包括自动
            updateById(pojo);

            //删除文章与素材的旧关系。这个 Map表示要删除news_id字段等于dto.getId()的记录。使用Collections.singletonMap("news_id", dto.getId())的目的是创建一个只包含一个键值对的不可变的Map对象
            wmNewsMaterialMapper.deleteByMap(Collections.singletonMap("news_id", dto.getId())); // 用了singletonMap就是拿来一个现成的map用
        }


        /*=================进入多表阶段。添加文章与素材关系wm_news_material=======================*/
        /** 保存文章素材关系表时需要设置4个参数：material_id, news_id, type, ord。其中material_id非常难获得
         * 因为在submit request前端传过来的dto中封面images和content中images使用的都是全路径。
         * 而事实上，选择上传图片的那部，前端从dfs能够获得素材库的全部信息。
         * 传递了全部信息，占用了更多带宽，却给后端传了一个最困难的小小的全路径，同时还暴露了存储服务器的地址。
         * 这样传递的dto是不合理的。现在获得material_id的过程是：
         * 先去封面images的图片，再取contents中的图片，因两处图片有重复故而放在集合中去重，根据全路径获得短路径，根据短路径去查material_id
         */
        /*--3. 先取封面images中图片，pojo中images以String存的，dto中images以List传的，故而从dto中取更好 --*/
        List<String> coverList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dto.getImages())) {
            coverList = dto.getImages().stream().map(this::removePrefix).collect(Collectors.toList());
        }

        /*--4. 再取内容中的图片 --*/
        // 上面已经获取一次了，经过流处理后，原始list不变化，会产生新流，必须要接收一下。而且方法已经针对null内容图片做了判断
        imageList = imageList.stream().map(this::removePrefix).collect(Collectors.toList());

        /*--5. 图片去重 --*/
        //再重设images，因为要选择封面，前端传来的是List<String>类型的，数据库需要String
        // content和images的图片可以不一样！不需要去重，因为type类型不一样，在各自的类型内部顺序也不一样，关系表对news和material都是多对一的关系
        // 去重的好处是：重复的图片少查了一次数据库
        Set<String> imageSet = new HashSet<>(coverList);
        imageSet.addAll(imageList);

        /*--6. 根据短地址查询material_id --*/
        // 通过图片的路径，查询素材的id
        // key=url地址，value=素材id
        Map<String, Long> materialUrlIdMap = getMaterialIdMap(imageSet);

        /*--7. 添加文章与素材的关系 --*/
        addRelationship(pojo.getId(), coverList, materialUrlIdMap, BC.WmNewsConstants.WM_COVER_REFERENCE); // 封面
        addRelationship(pojo.getId(), imageList, materialUrlIdMap, BC.WmNewsConstants.WM_CONTENT_REFERENCE);// 内容


        /*---------------------day07_文章提交之后要发消息进行异步审核-----------------------*/
        if(!draft){
            kafkaTemplate.send(BC.MqConstants.WM_NEWS_AUTO_SCAN_TOPIC, pojo.getId().toString());
            System.out.println("~~~~~~~~~~~测试：发送消息给admin审核~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    /**
     * 提取内容中的图片
     * @param content
     * @return
     */
    private List<String> getImagesFromContent(String content) {
        List<String> fullAddressList = new ArrayList<>(); // 先初始化，就可以保证最后返回的结果无论有没有，都不会为null。因为content很可能解析不出来。
        if (!StringUtils.isEmpty(content)) {// 有内容，可能有封面，可能没有
            // 有内容
            // 解析成javaBean对象
            List<WmNewsContentNodeDto> list = JSON.parseArray(content, WmNewsContentNodeDto.class);//json串里面如果是一个数组，可以parseArray
            // 提取类型为image
            // filter过滤，满足条件的留下
            fullAddressList = list.stream()
                    // 留下类型为image的node, 流中元素是WmNewsContentNodeDto
                    .filter(nodeDto -> BC.WmNewsConstants.CONTENT_IMAGE.equals(nodeDto.getType()))
                    // 要的是图片地址
                    .map(WmNewsContentNodeDto::getValue)//"http://192.168.211.128:8080/group1/M00/00/00/wKjTgGJkFYqATko3AABmUSAaQ2s964.jpg"
                    // 流中元素是 字符串 url
                    .collect(Collectors.toList());

        }
        return fullAddressList;
    }


    /**
     * 删除http://ip:port/ 这个前缀
     *
     * @param url
     * @return
     */
    private String removePrefix(String url) {
        //http://192.168.211.128:8080/group1/M00/00/00/wKjTgGK4BGuASyi-AAiWSBNgF8M620.png
        //http://192.168.211.128/group1/M00/00/00/wKjTgGK4BGuASyi-AAiWSBNgF8M620.png
        //http://127.0.0.1/group1/M00/00/00/wKjTgGK4BGuASyi-AAiWSBNgF8M620.png
        // 不支持http://localhost:port/
        return url.replaceAll("http://((\\d){1,3}\\.){3}(\\d){1,3}:?(\\d){0,5}\\/", "");
    }


    /**
     * 通过图片的路径，查询素材的id key=url地址，value=素材id
     * @param imageSet
     * @return
     */
    private Map<String, Long> getMaterialIdMap(Set<String> imageSet) {
        Map<String, Long> map = new HashMap<String, Long>();
        if (!CollectionUtils.isEmpty(imageSet)) {
            // 有图片 构建查询条件
            LambdaQueryWrapper<WmMaterial> lqw = new LambdaQueryWrapper<>();
            // 批量查询 in
            lqw.in(WmMaterial::getUrl, imageSet);
            // 查询
            List<WmMaterial> wmMaterialList = wmMaterialMapper.selectList(lqw);
            if (CollectionUtils.isEmpty(wmMaterialList)) {
                throw new LeadNewsException("文章中的图片可能已被删除了!");
            }
            // 能查询到数据
            map = wmMaterialList.stream().collect(Collectors.toMap(WmMaterial::getUrl, WmMaterial::getId)); // key是url，value是material_id
        }
        return map;
    }

    private void addRelationship(Long wmNewsId, List<String> picList, Map<String, Long> materialUrlIdMap, Integer type) {
        for (int i = 0; i < picList.size(); i++) {
            // 图片的地址
            String url = picList.get(i);
            // 素材的id
            Long materialId = materialUrlIdMap.get(url);
            WmNewsMaterial wmNewsMaterial = new WmNewsMaterial();
            wmNewsMaterial.setType(type);
            wmNewsMaterial.setMaterialId(materialId);
            wmNewsMaterial.setOrd(i + 1);
            wmNewsMaterial.setNewsId(wmNewsId);
            // 保存关系
            wmNewsMaterialMapper.insert(wmNewsMaterial);
        }
    }

    /**
     * ----------------------------------------------------------------------------
     */


    /**
     * 文章列表分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageResultVo findPage(WmNewsPageReqDto dto) {
        IPage<WmNews> pageInfo = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> lqw = new LambdaQueryWrapper<>();
        // 隐含条件，只能查询自己的文章
        lqw.eq(WmNews::getUserId, RequestContextUtil.getUserId());
        lqw.eq(null != dto.getStatus(), WmNews::getStatus, dto.getStatus());
        lqw.like(!StringUtils.isEmpty(dto.getKeyword()), WmNews::getTitle, dto.getKeyword());
        lqw.eq(null != dto.getChannelId(), WmNews::getChannelId, dto.getChannelId());
        lqw.ge(null != dto.getBeginPubDate(), WmNews::getPublishTime, dto.getBeginPubDate());
        lqw.le(null != dto.getEndPubDate(), WmNews::getPublishTime, dto.getEndPubDate());
        page(pageInfo, lqw);
        return PageResultVo.pageResult(dto.getPage(), dto.getSize(), pageInfo.getTotal(), pageInfo.getRecords());
    }


    /**
     * 通过id查询文章信息，信息回显
     * @param wmNewsId
     * @return
     */
    @Override
    public WmNews findOne(Long wmNewsId) {
        WmNews wmNews = getById(wmNewsId);
        if (null == wmNews || wmNews.getUserId() != RequestContextUtil.getUserId()) {
            throw new LeadNewsException("查询的文章不存在!");
        }
        return wmNews;
    }

    /**
     * 通过id删除
     *
     * @param wmNewsId
     */
    @Transactional
    public void removeMyWmNews(Long wmNewsId) {
        //1. 只能删除自己的文章
        WmNews myWmNews = query().eq("user_id", RequestContextUtil.getUserId())
                .eq("id", wmNewsId)
                .one();

        if (null == myWmNews) {
            throw new LeadNewsException(HttpCodeEnum.DATA_NOT_EXIST);
        }
        //已发布且已上架的文章，状态为9且enable=1已上架的文章 是不能删除
        if (myWmNews.getStatus() == BC.WmNewsConstants.STATUS_PUBLISHED
                && myWmNews.getEnable() == ENABLE_ON) {
            throw new LeadNewsException("文章已发布且已上架，不能删除!");
        }

        //2. 删除文章与素材的旧关系
        wmNewsMaterialMapper.deleteByMap(Collections.singletonMap("news_id", wmNewsId));

        //3. 删除文章。【真实场景下是逻辑删除】
        removeById(wmNewsId);
    }

    /**
     * 文章的上架与下架操作
     *
     * @param dto
     */
    @Override
    public void downOrUp(DownOrUpDto dto) {
        // 校验，只能找自己的文章
        WmNews myWmNews = query().eq("user_id", RequestContextUtil.getUserId())
                .eq("id", dto.getId())
                .one();

        if (null == myWmNews) {
            throw new LeadNewsException("文章不存在!");
        }
        if (myWmNews.getStatus() != BC.WmNewsConstants.STATUS_PUBLISHED) {
            throw new LeadNewsException("文章未发布，不能上架或下架操作!");
        }
        // 走到这里，就是对已经上架的文章下达下架指令；或者下架的文章下达上架指令
        WmNews updatePojo = new WmNews();
        //@Min(value = 0, message = "参数不正确") @Max(value = 1, message = "参数不正确") Integer enable = dto.getEnable();
        // wmnews表中，enable是指令，1是上架指令，0是下架指令。从前端传来

        //上下架操作既不涉及存草稿、也不涉及提交，不能盲目改状态。特别是跨系统时，会导致数据不一致。
        // 因为wemedia写服务改了状态，article读服务并没有相应改状态啊！所以下面的不能加！
        //updatePojo.setStatus(BC.WmNewsConstants.STATUS_SUBMIT);
        updatePojo.setEnable(dto.getEnable());
        updatePojo.setId(dto.getId());
        //更新的对象中的属性有值，则这个字段会被执行更新 update table set 字段=值, 包含在update语句中
        boolean flag = updateById(updatePojo);


        /*---------------------day08_分布式任务与文章信息同步-----------------------*/
        if(flag){
            Long articleId = myWmNews.getArticleId(); // 无论上下架，得到的articleid都是旧id！所以传过去的
            Map<String, Long> msgMap = new HashMap<>();
            msgMap.put("articleId", articleId);
            msgMap.put("enable", dto.getEnable().longValue()); // 这里的enable就是状态了。指令是下给wemedia写服务的，读服务article只能做同步
            // myWmNews.getEnable()写错了，得到的永远是旧结果，指令并没有传过来，应该是dto.getEnable()
            String jsonString = JSON.toJSONString(msgMap);
            kafkaTemplate.send(BC.MqConstants.WM_NEWS_DOWN_OR_UP_TOPIC, jsonString);
        }
        /**文章上架与下架的数据涉及多个微服务联动，只能由写服务传递到读服务。才能保证数据一致性。
         * 不能在读服务中私自修改！和配置表article_config中isDelete字段是不一样的性质。
         * isDelete字段是由于读服务不删除只增加的设计决定的，所以读服务可以决定自己修改。
         * isDown属性是可不是自己修改了就可以的事情，涉及到多平台跨系统。*/
    }




    /*------------------------day07_自媒体文章审核---------------------------*/

    /**
     * 文章列表 分页查询 给后台管理人员，人工审核, 需要带上作者名称
     * @param dto
     * @return
     */
    @Override
    public PageResultVo<WmNewsVo> listWithAuthor(WmNewsPageReqDto dto) {
        IPage<WmNews> pageInfo = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> lqw = new LambdaQueryWrapper<>();
        lqw.like(!StringUtils.isEmpty(dto.getTitle()), WmNews::getTitle, dto.getTitle());
        lqw.eq(null!=dto.getStatus(), WmNews::getStatus, dto.getStatus());
        page(pageInfo, lqw);


        // 拆开组装作者
        List<WmNews> wmNewsList = Optional.ofNullable(pageInfo.getRecords()).orElseThrow(LeadNewsException::new);
        List<WmNewsVo> wmNewsVoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(wmNewsList)) {
            List<Long> userIds = wmNewsList.stream()
                    .map(WmNews::getUserId)
                    .collect(Collectors.toList());

            List<WmUser> wmUserList = wmUserMapper.selectBatchIds(userIds);
            // 把上面的单列表扩展成双列表
            Map<Long, WmUser> idUserMap = wmUserList.stream().collect(Collectors.toMap(WmUser::getId, Function.identity())); //map是很多键值对的集合

            wmNewsVoList= wmNewsList.stream()
                    .map(wmNews -> WmNewsVo.build(wmNews, idUserMap.get(wmNews.getUserId())))
                    .collect(Collectors.toList());
        }
        return PageResultVo.pageResult(pageInfo.getPages(), pageInfo.getSize(), pageInfo.getTotal(), wmNewsVoList);
    }

    @Override
    public void authPass(AuthReqDto authReqDto) {
        WmNews updatePojo = new WmNews();
        updatePojo.setId(authReqDto.getId());
        updatePojo.setStatus(BC.WmNewsConstants.STATUS_MANUAL_PASS);
        updateById(updatePojo);
    }

    @Override
    public void authFail(AuthReqDto authReqDto) {
        WmNews updatePojo = new WmNews();
        updatePojo.setId(authReqDto.getId());
        updatePojo.setStatus(BC.WmNewsConstants.STATUS_FAIL);
        updatePojo.setReason(authReqDto.getMsg());
        updateById(updatePojo);
    }

    @Override
    public WmNewsVo oneVo(Long wmNewsId) {
        WmNews wmNews = getById(wmNewsId);
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        return WmNewsVo.build(wmNews, wmUser);
    }



    /*---------------------day08_分布式任务与文章信息同步-----------------------*/

    @Override
    public List<WmNewsVo> list4ArticleSync(WmNewsPageReqDto dto) {
        List<Integer> passStatusList = Arrays.asList(BC.WmNewsConstants.STATUS_MANUAL_PASS, BC.WmNewsConstants.STATUS_PASS);
        LambdaQueryWrapper<WmNews> lqw = new LambdaQueryWrapper<>();
        lqw.in(WmNews::getStatus, passStatusList);
        lqw.le(WmNews::getPublishTime, dto.getEndPubDate());
        List<WmNews> qualifiedNewsList = list(lqw);

        List<WmNewsVo> wmNewsVoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(qualifiedNewsList)) {
            Map<Long, WmUser> idUserMap = qualifiedNewsList.stream()
                    .map(WmNews::getUserId)
                    .map(userId -> wmUserMapper.selectById(userId))
                    .collect(Collectors.toMap(WmUser::getId, Function.identity()));

            wmNewsVoList = qualifiedNewsList.stream()
                    .map(wmNews -> WmNewsVo.build(wmNews, idUserMap.get(wmNews.getUserId())))
                    .collect(Collectors.toList());
        }
        return wmNewsVoList;
    }

}
