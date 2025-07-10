package com.leadnews.behaviour.controller;

import com.leadnews.behaviour.pojo.ApBehaviorEntry;
import com.leadnews.behaviour.pojo.ApLikesBehavior;
import com.leadnews.behaviour.pojo.ApUnlikesBehavior;
import com.leadnews.behaviour.service.ApBehaviorEntryService;
import com.leadnews.behaviour.service.ApLikesBehaviorService;
import com.leadnews.behaviour.service.ApUnlikesBehaviorService;
import com.leadnews.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** day10 用户关系行为展示
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.controller
 */
@RestController
public class ApiController {
    
    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    /** day10 用户关系行为展示
     * 远程调用 通过设备或用户id与类型，查询行为id
     * @param userId
     * @param type
     * @return
     */
    @GetMapping("/api/apBehaviorEntry/one")
    public ResultVo<ApBehaviorEntry> findBehaviorEntryByEntryIdAndType(
        @RequestParam(name="userId",required = true) Long userId,
        @RequestParam(name="type",required = true) Integer type){
        ApBehaviorEntry entry = apBehaviorEntryService.findByEntryIdAndType(userId, type);
        return ResultVo.ok(entry);
    }

    @Autowired
    private ApLikesBehaviorService apLikesBehaviorService;

    /**
     * 远程调用 通过文章Id与行为实体Id 查询是否点赞
     * @param articleId
     * @param entryId
     * @return
     */
    @GetMapping("/api/apLikesBehavior/one")
    ResultVo<ApLikesBehavior> findApLikesByUserIdOrEquipmentId(
            @RequestParam(name="articleId",required = true) Long articleId,
            @RequestParam(name="entryId",required = true) Integer entryId){
        ApLikesBehavior apLikesBehavior = apLikesBehaviorService
                .findByArticleIdAndEntryId(articleId,entryId);
        return ResultVo.ok(apLikesBehavior);
    }


    @Autowired
    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    /**
     * 远程调用 通过文章Id与行为实体Id 查询是否不喜欢
     * @param articleId
     * @param entryId
     * @return
     */
    @GetMapping("/api/apUnlikesBehavior/one")
    public ResultVo<ApUnlikesBehavior> findApUnlikesByEntryIdAndArticleId(
            @RequestParam(name="articleId",required = true) Long articleId,
            @RequestParam(name="entryId",required = true) Integer entryId){
        ApUnlikesBehavior apUnlikesBehavior = apUnlikesBehaviorService
                .findByArticleIdAndEntryId(articleId,entryId);
        return ResultVo.ok(apUnlikesBehavior);
    }


}
