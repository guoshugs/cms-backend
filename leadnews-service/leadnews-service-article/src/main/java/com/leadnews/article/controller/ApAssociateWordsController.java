package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApAssociateWords;
import com.leadnews.article.service.ApAssociateWordsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>联想词</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApAssociateWordsController",tags = "联想词")
@RestController
@RequestMapping("/apAssociateWords")
public class ApAssociateWordsController extends AbstractCoreController<ApAssociateWords> {

    private ApAssociateWordsService apAssociateWordsService;

    @Autowired
    public ApAssociateWordsController(ApAssociateWordsService apAssociateWordsService) {
        super(apAssociateWordsService);
        this.apAssociateWordsService=apAssociateWordsService;
    }

}

