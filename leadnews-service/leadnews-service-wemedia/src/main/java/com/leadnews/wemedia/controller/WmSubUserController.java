package com.leadnews.wemedia.controller;


import com.leadnews.wemedia.pojo.WmSubUser;
import com.leadnews.wemedia.service.WmSubUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>自媒体子账号信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmSubUserController",tags = "自媒体子账号信息")
@RestController
@RequestMapping("/wmSubUser")
public class WmSubUserController extends AbstractCoreController<WmSubUser> {

    private WmSubUserService wmSubUserService;

    @Autowired
    public WmSubUserController(WmSubUserService wmSubUserService) {
        super(wmSubUserService);
        this.wmSubUserService=wmSubUserService;
    }

}

