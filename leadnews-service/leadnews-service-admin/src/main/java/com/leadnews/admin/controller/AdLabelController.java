package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdLabel;
import com.leadnews.admin.service.AdLabelService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>标签信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdLabelController",tags = "标签信息")
@RestController
@RequestMapping("/adLabel")
public class AdLabelController extends AbstractCoreController<AdLabel> {

    private AdLabelService adLabelService;

    @Autowired
    public AdLabelController(AdLabelService adLabelService) {
        super(adLabelService);
        this.adLabelService=adLabelService;
    }

}

