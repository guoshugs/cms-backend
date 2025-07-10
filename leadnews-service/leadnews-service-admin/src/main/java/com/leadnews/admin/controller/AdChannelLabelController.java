package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdChannelLabel;
import com.leadnews.admin.service.AdChannelLabelService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>频道标签信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdChannelLabelController",tags = "频道标签信息")
@RestController
@RequestMapping("/adChannelLabel")
public class AdChannelLabelController extends AbstractCoreController<AdChannelLabel> {

    private AdChannelLabelService adChannelLabelService;

    @Autowired
    public AdChannelLabelController(AdChannelLabelService adChannelLabelService) {
        super(adChannelLabelService);
        this.adChannelLabelService=adChannelLabelService;
    }

}

