package com.leadnews.user.feign;

import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.pojo.ApUserFollow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version 1.0
 * @description 说明 day10 用户关系行为展示
 * @package com.leadnews.user.feign
 */
@FeignClient(name = "leadnews-user", contextId = "apUserFollowFeign")
public interface ApUserFollowFeign {

    /**
     * 查询用户是否关注了作者
     * @param userId
     * @param followId
     * @return
     */
    @GetMapping("/api/apUserFollow/one")
    ResultVo<ApUserFollow> findUserFollowByUserIdFollowId(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "followId", required = true) Integer followId
    );
}
