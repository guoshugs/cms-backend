package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdUserOpertion;
import com.leadnews.admin.service.AdUserOpertionService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>管理员操作行为信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdUserOpertionController",tags = "管理员操作行为信息")
@RestController
@RequestMapping("/adUserOpertion")
public class AdUserOpertionController extends AbstractCoreController<AdUserOpertion> {

    private AdUserOpertionService adUserOpertionService;

    @Autowired
    public AdUserOpertionController(AdUserOpertionService adUserOpertionService) {
        super(adUserOpertionService);
        this.adUserOpertionService=adUserOpertionService;
    }

}

