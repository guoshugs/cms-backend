package com.leadnews.user.controller;

import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.pojo.ApUserFollow;
import com.leadnews.user.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** day10 用户关系行为展示
 * @version 1.0
 * @description 说明
 * @package com.leadnews.user.controller
 */
@RestController
public class ApiController {

    @Autowired
    private ApUserFollowService apUserFollowService;

    /** day10 用户关系行为展示
     * 查询用户是否关注了作者
     * @param userId
     * @param followId
     * @return
     */
    @GetMapping("/api/apUserFollow/one")
    public ResultVo<ApUserFollow> findUserFollow(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "followId", required = true) Integer followId)
    {
        ApUserFollow apUserFollow = apUserFollowService.findUserFollowByUserIdFollowId(userId,followId);
        return ResultVo.ok(apUserFollow);
    }
}
