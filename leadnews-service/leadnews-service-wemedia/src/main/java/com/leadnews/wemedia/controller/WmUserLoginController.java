package com.leadnews.wemedia.controller;


import com.leadnews.wemedia.pojo.WmUserLogin;
import com.leadnews.wemedia.service.WmUserLoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>自媒体用户登录行为信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmUserLoginController",tags = "自媒体用户登录行为信息")
@RestController
@RequestMapping("/wmUserLogin")
public class WmUserLoginController extends AbstractCoreController<WmUserLogin> {

    private WmUserLoginService wmUserLoginService;

    @Autowired
    public WmUserLoginController(WmUserLoginService wmUserLoginService) {
        super(wmUserLoginService);
        this.wmUserLoginService=wmUserLoginService;
    }

}

