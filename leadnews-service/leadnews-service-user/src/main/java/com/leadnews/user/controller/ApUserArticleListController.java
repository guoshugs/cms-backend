package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserArticleList;
import com.leadnews.user.service.ApUserArticleListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户文章列</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserArticleListController",tags = "APP用户文章列")
@RestController
@RequestMapping("/apUserArticleList")
public class ApUserArticleListController extends AbstractCoreController<ApUserArticleList> {

    private ApUserArticleListService apUserArticleListService;

    @Autowired
    public ApUserArticleListController(ApUserArticleListService apUserArticleListService) {
        super(apUserArticleListService);
        this.apUserArticleListService=apUserArticleListService;
    }

}

