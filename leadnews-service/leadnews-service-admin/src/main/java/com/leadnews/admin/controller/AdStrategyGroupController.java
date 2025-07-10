package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdStrategyGroup;
import com.leadnews.admin.service.AdStrategyGroupService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>推荐策略分组信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdStrategyGroupController",tags = "推荐策略分组信息")
@RestController
@RequestMapping("/adStrategyGroup")
public class AdStrategyGroupController extends AbstractCoreController<AdStrategyGroup> {

    private AdStrategyGroupService adStrategyGroupService;

    @Autowired
    public AdStrategyGroupController(AdStrategyGroupService adStrategyGroupService) {
        super(adStrategyGroupService);
        this.adStrategyGroupService=adStrategyGroupService;
    }

}

