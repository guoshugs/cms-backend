package com.leadnews.wemedia.controller;

import com.leadnews.common.dto.LoginUserDto;
import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.service.WmUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.wemedia.controller
 */
@RestController
@RequestMapping("/login")
public class LoginController { // 本身与自媒体业务没什么关系，只是用来处理登录和退出的，因此把检查controller单独写出来

    @Resource
    private WmUserService wmUserService;

    /**
     * 自媒体登录校验
     * @param dto
     * @return
     */
    @PostMapping("/in")
    public ResultVo loginCheck(@RequestBody LoginUserDto dto){
        LoginUserVo vo = wmUserService.loginCheck(dto);
        return ResultVo.ok(vo);
    }
}
