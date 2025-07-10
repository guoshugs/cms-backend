package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApHotArticles;
import com.leadnews.article.service.ApHotArticlesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>热点文章</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApHotArticlesController",tags = "热点文章")
@RestController
@RequestMapping("/apHotArticles")
public class ApHotArticlesController extends AbstractCoreController<ApHotArticles> {

    private ApHotArticlesService apHotArticlesService;

    @Autowired
    public ApHotArticlesController(ApHotArticlesService apHotArticlesService) {
        super(apHotArticlesService);
        this.apHotArticlesService=apHotArticlesService;
    }

}

