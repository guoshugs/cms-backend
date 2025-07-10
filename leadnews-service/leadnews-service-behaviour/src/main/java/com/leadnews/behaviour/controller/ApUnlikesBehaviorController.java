package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.dto.UnLikesBehaviorDto;
import com.leadnews.behaviour.pojo.ApUnlikesBehavior;
import com.leadnews.behaviour.service.ApUnlikesBehaviorService;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description <p>APP不喜欢行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApUnlikesBehaviorController",tags = "APP不喜欢行为")
@RestController
@RequestMapping("/unlike_behavior")
public class ApUnlikesBehaviorController extends AbstractCoreController<ApUnlikesBehavior> {

    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    @Autowired
    public ApUnlikesBehaviorController(ApUnlikesBehaviorService apUnlikesBehaviorService) {
        super(apUnlikesBehaviorService);
        this.apUnlikesBehaviorService=apUnlikesBehaviorService;
    }

    /**
     * 不喜欢行为
     * @param unLikesBehaviorDto
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResultVo unlikeBehavior(@RequestBody UnLikesBehaviorDto unLikesBehaviorDto) throws Exception {
        apUnlikesBehaviorService.unlikeBehavior(unLikesBehaviorDto);
        return ResultVo.ok();
    }
}

