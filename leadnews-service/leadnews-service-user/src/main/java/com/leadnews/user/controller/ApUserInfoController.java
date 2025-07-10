package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserInfo;
import com.leadnews.user.service.ApUserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserInfoController",tags = "APP用户信息")
@RestController
@RequestMapping("/apUserInfo")
public class ApUserInfoController extends AbstractCoreController<ApUserInfo> {

    private ApUserInfoService apUserInfoService;

    @Autowired
    public ApUserInfoController(ApUserInfoService apUserInfoService) {
        super(apUserInfoService);
        this.apUserInfoService=apUserInfoService;
    }

}

