package com.leadnews.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.admin.dto.AdSensitivePageRequestDto;
import com.leadnews.admin.mapper.AdSensitiveMapper;
import com.leadnews.admin.pojo.AdSensitive;
import com.leadnews.admin.service.AdSensitiveService;
import com.leadnews.common.vo.PageResultVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @description <p>敏感词信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.service.impl
 */
@Service
public class AdSensitiveServiceImpl extends ServiceImpl<AdSensitiveMapper, AdSensitive> implements AdSensitiveService {
    /**
     * 敏感词列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageResultVo findPage(AdSensitivePageRequestDto dto) {
        //1. 创建分页参数对象
        Page<AdSensitive> pageInfo = new Page<>(dto.getPage(), dto.getSize());
        //2. 构建查询条件
        LambdaQueryWrapper<AdSensitive> lqw = new LambdaQueryWrapper<>();
        // 模糊
        /*lqw.like(!StringUtils.isEmpty(dto.getName()), AdChannel::getName, dto.getName());
        lqw.eq(dto.getStatus()!=null, AdChannel::getStatus, dto.getStatus());*/
        //3. 查询
        IPage<AdSensitive> page = page(pageInfo, lqw);
        //4. 封装结果返回
        return PageResultVo.pageResult(dto.getPage(), dto.getSize(), pageInfo.getTotal(), pageInfo.getRecords());
    }

    /**
     * 添加敏感词
     *
     * @param adSensitive
     */
    @Override
    public void add(AdSensitive adSensitive) {
        // 创建时间
        adSensitive.setCreatedTime(LocalDateTime.now());
        save(adSensitive);
    }
}
