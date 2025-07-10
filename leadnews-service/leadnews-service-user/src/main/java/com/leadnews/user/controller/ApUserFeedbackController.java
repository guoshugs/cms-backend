package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserFeedback;
import com.leadnews.user.service.ApUserFeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户反馈信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserFeedbackController",tags = "APP用户反馈信息")
@RestController
@RequestMapping("/apUserFeedback")
public class ApUserFeedbackController extends AbstractCoreController<ApUserFeedback> {

    private ApUserFeedbackService apUserFeedbackService;

    @Autowired
    public ApUserFeedbackController(ApUserFeedbackService apUserFeedbackService) {
        super(apUserFeedbackService);
        this.apUserFeedbackService=apUserFeedbackService;
    }

}

