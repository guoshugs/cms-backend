package com.leadnews.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.admin.dto.AdSensitivePageRequestDto;
import com.leadnews.admin.pojo.AdSensitive;
import com.leadnews.common.vo.PageResultVo;

/**
 * @description <p>敏感词信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.service
 */
public interface AdSensitiveService extends IService<AdSensitive> {
    /**
     * 敏感词列表
     * @param dto
     * @return
     */
    PageResultVo findPage(AdSensitivePageRequestDto dto);

    /**
     * 添加敏感词
     * @param adSensitive
     */
    void add(AdSensitive adSensitive);
}
