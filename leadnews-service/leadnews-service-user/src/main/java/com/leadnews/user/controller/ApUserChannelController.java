package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserChannel;
import com.leadnews.user.service.ApUserChannelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户频道信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserChannelController",tags = "APP用户频道信息")
@RestController
@RequestMapping("/apUserChannel")
public class ApUserChannelController extends AbstractCoreController<ApUserChannel> {

    private ApUserChannelService apUserChannelService;

    @Autowired
    public ApUserChannelController(ApUserChannelService apUserChannelService) {
        super(apUserChannelService);
        this.apUserChannelService=apUserChannelService;
    }

}

