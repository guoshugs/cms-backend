package com.leadnews.admin.controller;

import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.admin.service.AdChannelService;
import com.leadnews.common.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ApiController {
    @Resource
    private AdChannelService adChannelService;

    /**
     * 查询频道列表
     * @return
     */
    @GetMapping("/api/adChannel/list")
    public ResultVo<List<AdChannel>> listChannel(){
        List<AdChannel> list = adChannelService.list();
        return ResultVo.ok(list);
    }

}
