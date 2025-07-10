package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApArticleConfig;
import com.leadnews.article.service.ApArticleConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP已发布文章配置</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApArticleConfigController",tags = "APP已发布文章配置")
@RestController
@RequestMapping("/articleConfig")
public class ApArticleConfigController extends AbstractCoreController<ApArticleConfig> {

    private ApArticleConfigService apArticleConfigService;

    @Autowired
    public ApArticleConfigController(ApArticleConfigService apArticleConfigService) {
        super(apArticleConfigService);
        this.apArticleConfigService=apArticleConfigService;
    }

}

