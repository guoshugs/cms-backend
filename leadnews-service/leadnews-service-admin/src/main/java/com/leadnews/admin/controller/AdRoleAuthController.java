package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdRoleAuth;
import com.leadnews.admin.service.AdRoleAuthService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>角色权限信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdRoleAuthController",tags = "角色权限信息")
@RestController
@RequestMapping("/adRoleAuth")
public class AdRoleAuthController extends AbstractCoreController<AdRoleAuth> {

    private AdRoleAuthService adRoleAuthService;

    @Autowired
    public AdRoleAuthController(AdRoleAuthService adRoleAuthService) {
        super(adRoleAuthService);
        this.adRoleAuthService=adRoleAuthService;
    }

}

