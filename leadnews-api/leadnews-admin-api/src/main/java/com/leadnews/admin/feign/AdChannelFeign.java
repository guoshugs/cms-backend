package com.leadnews.admin.feign;

import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.common.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "leadnews-admin", contextId = "adChannelFeign")
public interface AdChannelFeign {

    /**
     * 查询频道列表
     * @return
     */
    // 要的是list，但基于统一响应的原则，就需要封装list
    @GetMapping("/api/adChannel/list")
    ResultVo<List<AdChannel>> listChannel();


    @GetMapping("channel/getByChannelId/{channelId}")
    ResultVo<AdChannel> getByChannelId(@PathVariable Integer channelId);

}
