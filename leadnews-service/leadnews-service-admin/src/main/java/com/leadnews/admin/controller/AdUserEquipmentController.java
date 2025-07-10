package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdUserEquipment;
import com.leadnews.admin.service.AdUserEquipmentService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>管理员设备信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdUserEquipmentController",tags = "管理员设备信息")
@RestController
@RequestMapping("/adUserEquipment")
public class AdUserEquipmentController extends AbstractCoreController<AdUserEquipment> {

    private AdUserEquipmentService adUserEquipmentService;

    @Autowired
    public AdUserEquipmentController(AdUserEquipmentService adUserEquipmentService) {
        super(adUserEquipmentService);
        this.adUserEquipmentService=adUserEquipmentService;
    }

}

