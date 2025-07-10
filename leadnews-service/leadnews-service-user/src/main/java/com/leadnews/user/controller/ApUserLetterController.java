package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserLetter;
import com.leadnews.user.service.ApUserLetterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户私信信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserLetterController",tags = "APP用户私信信息")
@RestController
@RequestMapping("/apUserLetter")
public class ApUserLetterController extends AbstractCoreController<ApUserLetter> {

    private ApUserLetterService apUserLetterService;

    @Autowired
    public ApUserLetterController(ApUserLetterService apUserLetterService) {
        super(apUserLetterService);
        this.apUserLetterService=apUserLetterService;
    }

}

