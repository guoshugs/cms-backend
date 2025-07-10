package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserMessage;
import com.leadnews.user.service.ApUserMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户消息通知信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserMessageController",tags = "APP用户消息通知信息")
@RestController
@RequestMapping("/apUserMessage")
public class ApUserMessageController extends AbstractCoreController<ApUserMessage> {

    private ApUserMessageService apUserMessageService;

    @Autowired
    public ApUserMessageController(ApUserMessageService apUserMessageService) {
        super(apUserMessageService);
        this.apUserMessageService=apUserMessageService;
    }

}

