package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.service.ApBehaviorEntryService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP行为实体,一个行为实体可能是用户或者设备，或者其它</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApBehaviorEntryController",tags = "APP行为实体,一个行为实体可能是用户或者设备，或者其它")
@RestController
@RequestMapping("/apBehaviorEntry")
public class ApBehaviorEntryController extends AbstractCoreController<ApBehaviorEntry> {

    private ApBehaviorEntryService apBehaviorEntryService;

    @Autowired
    public ApBehaviorEntryController(ApBehaviorEntryService apBehaviorEntryService) {
        super(apBehaviorEntryService);
        this.apBehaviorEntryService=apBehaviorEntryService;
    }

}

