package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserFollow;
import com.leadnews.user.service.ApUserFollowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户关注信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserFollowController",tags = "APP用户关注信息")
@RestController
@RequestMapping("/apUserFollow")
public class ApUserFollowController extends AbstractCoreController<ApUserFollow> {

    private ApUserFollowService apUserFollowService;

    @Autowired
    public ApUserFollowController(ApUserFollowService apUserFollowService) {
        super(apUserFollowService);
        this.apUserFollowService=apUserFollowService;
    }

}

