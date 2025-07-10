package com.leadnews.wemedia.service;

import com.leadnews.common.vo.PageResultVo;
import com.leadnews.wemedia.dto.AuthReqDto;
import com.leadnews.wemedia.dto.DownOrUpDto;
import com.leadnews.wemedia.dto.WmNewsDtoSave;
import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.pojo.WmNews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.wemedia.vo.WmNewsVo;

import java.util.List;

/**
 * @description <p>自媒体图文内容信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.service
 */
public interface WmNewsService extends IService<WmNews> {

    /**
     * 文章列表分页查询
     * @param dto
     * @return
     */
    PageResultVo findPage(WmNewsPageReqDto dto);

    void submit(WmNewsDtoSave dto, boolean isDraft);

    /**
     * 通过id查询文章信息
     * @param wmNewsId
     * @return
     */
    WmNews findOne(Long wmNewsId);

    /**
     * 通过id删除
     * @param wmNewsId
     */
    void removeMyWmNews(Long wmNewsId);

    /**
     * 文章的上架与下架操作
     * @param dto
     */
    void downOrUp(DownOrUpDto dto);


    /*---------------------day07_自媒体文章审核-------------------------*/
    /**
     * 文章列表 分页查询 给后台管理人员，人工审核, 需要带上作者名称
     * @param dto
     * @return
     */
    PageResultVo<WmNewsVo> listWithAuthor(WmNewsPageReqDto dto);

    void authPass(AuthReqDto authReqDto);

    void authFail(AuthReqDto authReqDto);


    WmNewsVo oneVo(Long wmNewsId);

    /*-------------------day08_分布式任务与文章信息同步--------------------------------*/
    /**
     * 查询需要同步到文章微服的新闻文章
     * @param dto
     * @return
     */
    List<WmNewsVo> list4ArticleSync(WmNewsPageReqDto dto);


}
