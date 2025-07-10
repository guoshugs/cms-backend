package com.leadnews.user.controller;


import com.leadnews.user.pojo.ApUserSearch;
import com.leadnews.user.service.ApUserSearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP用户搜索信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
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

