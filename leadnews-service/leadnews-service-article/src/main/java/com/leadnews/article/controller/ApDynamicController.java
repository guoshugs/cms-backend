package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApDynamic;
import com.leadnews.article.service.ApDynamicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户动态信息</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApDynamicController",tags = "APP用户动态信息")
@RestController
@RequestMapping("/apDynamic")
public class ApDynamicController extends AbstractCoreController<ApDynamic> {

    private ApDynamicService apDynamicService;

    @Autowired
    public ApDynamicController(ApDynamicService apDynamicService) {
        super(apDynamicService);
        this.apDynamicService=apDynamicService;
    }

}

