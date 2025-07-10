package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.pojo.ApShowBehavior;
import com.leadnews.behaviour.service.ApShowBehaviorService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP文章展现行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApShowBehaviorController",tags = "APP文章展现行为")
@RestController
@RequestMapping("/show_behavior")
public class ApShowBehaviorController extends AbstractCoreController<ApShowBehavior> {

    private ApShowBehaviorService apShowBehaviorService;

    @Autowired
    public ApShowBehaviorController(ApShowBehaviorService apShowBehaviorService) {
        super(apShowBehaviorService);
        this.apShowBehaviorService=apShowBehaviorService;
    }

}

