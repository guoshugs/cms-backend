package com.leadnews.wemedia.controller;


import com.leadnews.wemedia.pojo.WmUser;
import com.leadnews.wemedia.service.WmUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>自媒体用户信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmUserController",tags = "自媒体用户信息")
@RestController
@RequestMapping("/wmUser")
public class WmUserController extends AbstractCoreController<WmUser> {

    private WmUserService wmUserService;

    @Autowired
    public WmUserController(WmUserService wmUserService) {
        super(wmUserService);
        this.wmUserService=wmUserService;
    }



}

