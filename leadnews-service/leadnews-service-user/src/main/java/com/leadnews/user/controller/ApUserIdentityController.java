package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserIdentity;
import com.leadnews.user.service.ApUserIdentityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP身份认证信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserIdentityController",tags = "APP身份认证信息")
@RestController
@RequestMapping("/apUserIdentity")
public class ApUserIdentityController extends AbstractCoreController<ApUserIdentity> {

    private ApUserIdentityService apUserIdentityService;

    @Autowired
    public ApUserIdentityController(ApUserIdentityService apUserIdentityService) {
        super(apUserIdentityService);
        this.apUserIdentityService=apUserIdentityService;
    }

}

