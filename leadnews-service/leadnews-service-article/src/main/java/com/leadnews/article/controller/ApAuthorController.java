package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApAuthor;
import com.leadnews.article.service.ApAuthorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP文章作者信息</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApAuthorController",tags = "APP文章作者信息")
@RestController
@RequestMapping("/apAuthor")
public class ApAuthorController extends AbstractCoreController<ApAuthor> {

    private ApAuthorService apAuthorService;

    @Autowired
    public ApAuthorController(ApAuthorService apAuthorService) {
        super(apAuthorService);
        this.apAuthorService=apAuthorService;
    }

}

