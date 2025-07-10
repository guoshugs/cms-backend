package com.leadnews.behaviour.controller;


import com.leadnews.behaviour.dto.ReadBehaviorDto;
import com.leadnews.behaviour.pojo.ApReadBehavior;
import com.leadnews.behaviour.service.ApReadBehaviorService;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>APP阅读行为</p>
 *
 * @version 1.0
 * @package com.leadnews.behaviour.controller
 */
@Api(value="ApReadBehaviorController",tags = "APP阅读行为")
@RestController
@RequestMapping("/read_behavior")
public class ApReadBehaviorController extends AbstractCoreController<ApReadBehavior> {

    private ApReadBehaviorService apReadBehaviorService;

    @Autowired
    public ApReadBehaviorController(ApReadBehaviorService apReadBehaviorService) {
        super(apReadBehaviorService);
        this.apReadBehaviorService=apReadBehaviorService;
    }

    /**
     * APP阅读行为
     * @param readBehaviorDto
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResultVo read(@RequestBody ReadBehaviorDto readBehaviorDto) throws Exception{
        apReadBehaviorService.saveReadBehavior(readBehaviorDto);
        return ResultVo.ok();
    }

}
