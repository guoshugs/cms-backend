package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.pojo.ApShareBehavior;
import com.leadnews.behaviour.service.ApShareBehaviorService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP分享行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApShareBehaviorController",tags = "APP分享行为")
@RestController
@RequestMapping("/apShareBehavior")
public class ApShareBehaviorController extends AbstractCoreController<ApShareBehavior> {

    private ApShareBehaviorService apShareBehaviorService;

    @Autowired
    public ApShareBehaviorController(ApShareBehaviorService apShareBehaviorService) {
        super(apShareBehaviorService);
        this.apShareBehaviorService=apShareBehaviorService;
    }

}

