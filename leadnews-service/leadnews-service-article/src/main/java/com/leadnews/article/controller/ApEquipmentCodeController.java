package com.leadnews.article.controller;


import com.leadnews.article.pojo.ApEquipmentCode;
import com.leadnews.article.service.ApEquipmentCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP设备码信息</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApEquipmentCodeController",tags = "APP设备码信息")
@RestController
@RequestMapping("/apEquipmentCode")
public class ApEquipmentCodeController extends AbstractCoreController<ApEquipmentCode> {

    private ApEquipmentCodeService apEquipmentCodeService;

    @Autowired
    public ApEquipmentCodeController(ApEquipmentCodeService apEquipmentCodeService) {
        super(apEquipmentCodeService);
        this.apEquipmentCodeService=apEquipmentCodeService;
    }

}

