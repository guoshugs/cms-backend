package com.leadnews.admin.controller;

import com.leadnews.admin.service.AdUserService;
import com.leadnews.common.dto.LoginUserDto;
import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.common.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.admin.controller
 */
@RestController
@RequestMapping("/login")
/* 任何人都能登录，所以LoginController独立与后台系统，网关请求地址就是/admin/login，方法是in和out*/
public class LoginController {

    @Resource
    private AdUserService adUserService;

    /**
     * 登录校验
     * @param dto
     * @return
     */
    @PostMapping("/in")
    public ResultVo loginCheck(@RequestBody @Valid LoginUserDto dto){
        LoginUserVo vo = adUserService.loginCheck(dto);
        return ResultVo.ok(vo);
    }
}
