package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdUserLogin;
import com.leadnews.admin.service.AdUserLoginService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>管理员登录行为信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdUserLoginController",tags = "管理员登录行为信息")
@RestController
@RequestMapping("/adUserLogin")
public class AdUserLoginController extends AbstractCoreController<AdUserLogin> {

    private AdUserLoginService adUserLoginService;

    @Autowired
    public AdUserLoginController(AdUserLoginService adUserLoginService) {
        super(adUserLoginService);
        this.adUserLoginService=adUserLoginService;
    }

}

