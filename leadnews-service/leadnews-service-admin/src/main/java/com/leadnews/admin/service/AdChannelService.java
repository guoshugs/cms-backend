package com.leadnews.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.admin.dto.ChannelPageRequestDto;
import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.common.vo.PageResultVo;

/**
 * @description <p>频道信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.service
 */
public interface AdChannelService extends IService<AdChannel> {
    /**
     * 频道列表
     * @param dto
     * @return
     */
    PageResultVo findPage(ChannelPageRequestDto dto);

    /**
     * 添加频道
     * @param adChannel
     */
    void add(AdChannel adChannel);
}
