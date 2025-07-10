package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.pojo.ApForwardBehavior;
import com.leadnews.behaviour.service.ApForwardBehaviorService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP转发行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApForwardBehaviorController",tags = "APP转发行为")
@RestController
@RequestMapping("/apForwardBehavior")
public class ApForwardBehaviorController extends AbstractCoreController<ApForwardBehavior> {

    private ApForwardBehaviorService apForwardBehaviorService;

    @Autowired
    public ApForwardBehaviorController(ApForwardBehaviorService apForwardBehaviorService) {
        super(apForwardBehaviorService);
        this.apForwardBehaviorService=apForwardBehaviorService;
    }

}

