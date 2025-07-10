package com.leadnews.wemedia.controller;


import com.leadnews.wemedia.pojo.WmFansPortrait;
import com.leadnews.wemedia.service.WmFansPortraitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>自媒体粉丝画像信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmFansPortraitController",tags = "自媒体粉丝画像信息")
@RestController
@RequestMapping("/wmFansPortrait")
public class WmFansPortraitController extends AbstractCoreController<WmFansPortrait> {

    private WmFansPortraitService wmFansPortraitService;

    @Autowired
    public WmFansPortraitController(WmFansPortraitService wmFansPortraitService) {
        super(wmFansPortraitService);
        this.wmFansPortraitService=wmFansPortraitService;
    }

}

