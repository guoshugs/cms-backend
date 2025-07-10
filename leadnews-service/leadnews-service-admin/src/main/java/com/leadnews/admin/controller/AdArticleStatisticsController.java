package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdArticleStatistics;
import com.leadnews.admin.service.AdArticleStatisticsService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>文章数据统计</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdArticleStatisticsController",tags = "文章数据统计")
@RestController
@RequestMapping("/adArticleStatistics")
public class AdArticleStatisticsController extends AbstractCoreController<AdArticleStatistics> {

    private AdArticleStatisticsService adArticleStatisticsService;

    @Autowired
    public AdArticleStatisticsController(AdArticleStatisticsService adArticleStatisticsService) {
        super(adArticleStatisticsService);
        this.adArticleStatisticsService=adArticleStatisticsService;
    }

}

