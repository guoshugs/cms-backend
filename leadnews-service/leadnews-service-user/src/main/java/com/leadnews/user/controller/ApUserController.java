package com.leadnews.user.controller;


import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.dto.UserRelationDto;
import com.leadnews.user.pojo.ApUser;
import com.leadnews.user.service.ApUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/** day09 APP端用户关注行为
 * @description <p>APP用户信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserController",tags = "APP用户信息")
@RestController
@RequestMapping("/user")
public class ApUserController extends AbstractCoreController<ApUser> {

    private ApUserService apUserService;

    @Autowired
    public ApUserController(ApUserService apUserService) {
        super(apUserService);
        this.apUserService=apUserService;
    }

    /**
     * 关注作者与取消关注
     * @param dto
     * @return
     */
    @PostMapping("/user_follow")
    public ResultVo userFollow(@RequestBody UserRelationDto dto) {
        apUserService.userFollow(dto);
        return ResultVo.ok();
    }
}

