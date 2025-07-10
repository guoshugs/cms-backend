package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdMenu;
import com.leadnews.admin.service.AdMenuService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>菜单资源信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdMenuController",tags = "菜单资源信息")
@RestController
@RequestMapping("/adMenu")
public class AdMenuController extends AbstractCoreController<AdMenu> {

    private AdMenuService adMenuService;

    @Autowired
    public AdMenuController(AdMenuService adMenuService) {
        super(adMenuService);
        this.adMenuService=adMenuService;
    }

}

