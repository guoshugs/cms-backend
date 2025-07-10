package com.leadnews.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.admin.dto.ChannelPageRequestDto;
import com.leadnews.admin.mapper.AdChannelMapper;
import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.admin.service.AdChannelService;
import com.leadnews.common.vo.PageResultVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class AdChannelServiceImpl extends ServiceImpl<AdChannelMapper, AdChannel> implements AdChannelService {
    /**
     * 频道列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageResultVo findPage(ChannelPageRequestDto dto) {
        // 1 构建分页参数对象
        // 2 构建查询条件
        // 3 查询
        // 4 封装结果返回
        Page<AdChannel> adChannelPage = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<AdChannel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(dto.getName()), AdChannel::getName, dto.getName())
                    .eq(dto.getStatus() != null, AdChannel::getStatus, dto.getStatus());
        page(adChannelPage, queryWrapper);

        return PageResultVo.pageResult(dto.getPage(), dto.getSize(),
                adChannelPage.getTotal(), adChannelPage.getRecords());

        /* page(adChannelPage, queryWrapper)
        * 调用page方法后返回的对象，就是参数里的adChannelPage对象，==，地址值相同*/
    }

    /**
     * 添加频道
     *
     * @param adChannel
     */
    @Override
    public void add(AdChannel adChannel) {
/*        adChannel.setIsDefault(false);
        adChannel.setCreatedTime(new Date());*/
        adChannel.setIsDefault(false);
        adChannel.setCreatedTime(LocalDateTime.now());
        save(adChannel);
    }

}
