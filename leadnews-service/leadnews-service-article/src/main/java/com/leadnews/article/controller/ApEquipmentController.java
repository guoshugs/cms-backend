package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApEquipment;
import com.leadnews.article.service.ApEquipmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP设备信息</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApEquipmentController",tags = "APP设备信息")
@RestController
@RequestMapping("/apEquipment")
public class ApEquipmentController extends AbstractCoreController<ApEquipment> {

    private ApEquipmentService apEquipmentService;

    @Autowired
    public ApEquipmentController(ApEquipmentService apEquipmentService) {
        super(apEquipmentService);
        this.apEquipmentService=apEquipmentService;
    }

}

