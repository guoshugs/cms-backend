package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdUser;
import com.leadnews.admin.service.AdUserService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>管理员用户信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdUserController",tags = "管理员用户信息")
@RestController
@RequestMapping("/adUser")
public class AdUserController extends AbstractCoreController<AdUser> {

    private AdUserService adUserService;

    @Autowired
    public AdUserController(AdUserService adUserService) {
        super(adUserService);
        this.adUserService=adUserService;
    }

}

