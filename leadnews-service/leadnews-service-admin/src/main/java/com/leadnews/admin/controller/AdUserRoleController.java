package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdUserRole;
import com.leadnews.admin.service.AdUserRoleService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>管理员角色信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdUserRoleController",tags = "管理员角色信息")
@RestController
@RequestMapping("/adUserRole")
public class AdUserRoleController extends AbstractCoreController<AdUserRole> {

    private AdUserRoleService adUserRoleService;

    @Autowired
    public AdUserRoleController(AdUserRoleService adUserRoleService) {
        super(adUserRoleService);
        this.adUserRoleService=adUserRoleService;
    }

}

