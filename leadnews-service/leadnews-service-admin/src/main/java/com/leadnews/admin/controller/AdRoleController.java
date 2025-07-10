package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdRole;
import com.leadnews.admin.service.AdRoleService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>角色信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdRoleController",tags = "角色信息")
@RestController
@RequestMapping("/adRole")
public class AdRoleController extends AbstractCoreController<AdRole> {

    private AdRoleService adRoleService;

    @Autowired
    public AdRoleController(AdRoleService adRoleService) {
        super(adRoleService);
        this.adRoleService=adRoleService;
    }

}

