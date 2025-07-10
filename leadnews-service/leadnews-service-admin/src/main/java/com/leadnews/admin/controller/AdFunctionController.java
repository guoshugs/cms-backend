package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdFunction;
import com.leadnews.admin.service.AdFunctionService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>页面功能信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdFunctionController",tags = "页面功能信息")
@RestController
@RequestMapping("/adFunction")
public class AdFunctionController extends AbstractCoreController<AdFunction> {

    private AdFunctionService adFunctionService;

    @Autowired
    public AdFunctionController(AdFunctionService adFunctionService) {
        super(adFunctionService);
        this.adFunctionService=adFunctionService;
    }

}

