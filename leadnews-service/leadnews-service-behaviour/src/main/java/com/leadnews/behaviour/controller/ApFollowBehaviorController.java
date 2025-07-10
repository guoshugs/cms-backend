package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.pojo.ApFollowBehavior;
import com.leadnews.behaviour.service.ApFollowBehaviorService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP关注行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApFollowBehaviorController",tags = "APP关注行为")
@RestController
@RequestMapping("/apFollowBehavior")
public class ApFollowBehaviorController extends AbstractCoreController<ApFollowBehavior> {

    private ApFollowBehaviorService apFollowBehaviorService;

    @Autowired
    public ApFollowBehaviorController(ApFollowBehaviorService apFollowBehaviorService) {
        super(apFollowBehaviorService);
        this.apFollowBehaviorService=apFollowBehaviorService;
    }

}

