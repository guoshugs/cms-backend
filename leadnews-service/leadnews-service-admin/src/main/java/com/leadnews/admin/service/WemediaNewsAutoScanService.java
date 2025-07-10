package com.leadnews.admin.service;

import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.vo.WmNewsVo;

import java.util.List;

public interface WemediaNewsAutoScanService {
    /**
     * 自媒体文章自动审核
     * @param wmNewsId
     * @param uuid
     */
    void autoScanWemediaNewsById(long wmNewsId, String uuid);


    /**
     * 需要同步的文章
     * @param dto
     * @return
     */
    List<WmNewsVo> list4ArticleSync(WmNewsPageReqDto dto);

    /**
     * 同步文章数据
     * @param wmNewsVo
     * @return
     */
    void syncArticleInfo(WmNewsVo wmNewsVo);
}
