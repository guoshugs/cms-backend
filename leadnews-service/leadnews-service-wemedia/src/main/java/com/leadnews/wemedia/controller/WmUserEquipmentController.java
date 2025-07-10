package com.leadnews.wemedia.controller;


import com.leadnews.wemedia.pojo.WmUserEquipment;
import com.leadnews.wemedia.service.WmUserEquipmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>自媒体用户设备信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmUserEquipmentController",tags = "自媒体用户设备信息")
@RestController
@RequestMapping("/wmUserEquipment")
public class WmUserEquipmentController extends AbstractCoreController<WmUserEquipment> {

    private WmUserEquipmentService wmUserEquipmentService;

    @Autowired
    public WmUserEquipmentController(WmUserEquipmentService wmUserEquipmentService) {
        super(wmUserEquipmentService);
        this.wmUserEquipmentService=wmUserEquipmentService;
    }

}

