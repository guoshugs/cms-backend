package com.leadnews.behaviour.feign;

import com.leadnews.behaviour.pojo.ApUnlikesBehavior;
import com.leadnews.common.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.feign
 */
@FeignClient(name="leadnews-behaviour", contextId ="apUnlikesBehaviorFeign" )
public interface ApUnlikesBehaviorFeign {
    @GetMapping("/api/apUnlikesBehavior/one")
    ResultVo<ApUnlikesBehavior> findApUnlikesByEntryIdAndArticleId(
            @RequestParam(name="articleId",required = true) Long articleId,
            @RequestParam(name="entryId",required = true) Integer entryId);
}
