package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.dto.LikesBehaviourDto;
import com.leadnews.behaviour.pojo.ApLikesBehavior;
import com.leadnews.behaviour.service.ApLikesBehaviorService;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description <p>APP点赞行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApLikesBehaviorController",tags = "APP点赞行为")
@RestController
@RequestMapping("/likes_behavior")
public class ApLikesBehaviorController extends AbstractCoreController<ApLikesBehavior> {

    private ApLikesBehaviorService apLikesBehaviorService;

    @Autowired
    public ApLikesBehaviorController(ApLikesBehaviorService apLikesBehaviorService) {
        super(apLikesBehaviorService);
        this.apLikesBehaviorService=apLikesBehaviorService;
    }
    /**
     * APP点赞行为
     * @param likesBehaviourDto
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResultVo likeBehavior(@RequestBody LikesBehaviourDto dto) {
        apLikesBehaviorService.likeBehavior(dto);
        return ResultVo.ok();
    }

}

