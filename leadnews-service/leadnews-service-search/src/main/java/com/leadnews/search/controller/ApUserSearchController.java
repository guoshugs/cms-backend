package com.leadnews.search.controller;


import com.leadnews.core.controller.AbstractCoreController;
import com.leadnews.search.pojo.ApUserSearch;
import com.leadnews.search.service.ApUserSearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP用户搜索信息</p>
 *
 * @version 1.0
 * @package com.leadnews.search.controller
 */
@Api(value="ApUserSearchController",tags = "APP用户搜索信息")
@RestController
@RequestMapping("/apUserSearch")
public class ApUserSearchController extends AbstractCoreController<ApUserSearch> {

    private ApUserSearchService apUserSearchService;

    @Autowired
    public ApUserSearchController(ApUserSearchService apUserSearchService) {
        super(apUserSearchService);
        this.apUserSearchService=apUserSearchService;
    }

}

