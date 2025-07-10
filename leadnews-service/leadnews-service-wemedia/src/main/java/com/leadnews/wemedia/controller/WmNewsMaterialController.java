package com.leadnews.wemedia.controller;


import com.leadnews.wemedia.pojo.WmNewsMaterial;
import com.leadnews.wemedia.service.WmNewsMaterialService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>自媒体图文引用素材信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmNewsMaterialController",tags = "自媒体图文引用素材信息")
@RestController
@RequestMapping("/wmNewsMaterial")
public class WmNewsMaterialController extends AbstractCoreController<WmNewsMaterial> {

    private WmNewsMaterialService wmNewsMaterialService;

    @Autowired
    public WmNewsMaterialController(WmNewsMaterialService wmNewsMaterialService) {
        super(wmNewsMaterialService);
        this.wmNewsMaterialService=wmNewsMaterialService;
    }

}

