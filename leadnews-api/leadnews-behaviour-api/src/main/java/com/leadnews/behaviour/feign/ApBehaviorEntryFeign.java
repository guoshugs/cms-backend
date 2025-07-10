package com.leadnews.behaviour.feign;

import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.common.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** day10 用户关系行为展示
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.feign
 */
@FeignClient(name="leadnews-behaviour", contextId ="apBehaviorEntryFeign" )
public interface ApBehaviorEntryFeign {
    @GetMapping("/api/apBehaviorEntry/one")
    ResultVo<ApBehaviorEntry> findBehaviorEntryByEntryIdAndType(
        @RequestParam(name="userId",required = true) Long userId,
        @RequestParam(name="type",required = true) Integer type);
}
