package com.leadnews.user.controller;

import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.dto.LoginDto;
import com.leadnews.user.service.ApUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**day09_app端基本功能展示
 * app端登录校验
 */
@RestController
@RequestMapping("login")
@Api(value="LoginController",tags = "APP端登录校验")
public class LoginController {

    @Resource
    private ApUserService apUserService;

    /**
     * 登录校验
     * @param dto
     * @return
     */
    @PostMapping("login_auth")
    public ResultVo loginCheck(@RequestBody LoginDto dto){
        LoginUserVo vo = apUserService.loginCheck(dto);
        return ResultVo.ok(vo);
    }
}
