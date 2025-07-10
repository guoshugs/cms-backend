package com.leadnews.user.controller;


import com.leadnews.common.vo.PageResultVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.dto.ApUserRealnameAuthDto;
import com.leadnews.user.dto.ApUserRealnamePageRequestDto;
import com.leadnews.user.pojo.ApUserRealname;
import com.leadnews.user.service.ApUserRealnameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP实名认证信息</p>
 *
 * @version 1.0
 * @package com.leadnews.user.controller
 */
@Api(value="ApUserRealnameController",tags = "APP实名认证信息")
@RestController
@RequestMapping("/auth")
public class ApUserRealnameController extends AbstractCoreController<ApUserRealname> {

    private ApUserRealnameService apUserRealnameService;

    @Autowired
    public ApUserRealnameController(ApUserRealnameService apUserRealnameService) {
        super(apUserRealnameService);
        this.apUserRealnameService=apUserRealnameService;
    }

    /**
     * 实名认证列表 分页查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public PageResultVo list(@RequestBody ApUserRealnamePageRequestDto dto){
        PageResultVo vo = apUserRealnameService.findPage(dto);
        return vo;
    }

    @PostMapping("/authFail")
    public ResultVo authFail(@RequestBody ApUserRealnameAuthDto dto) { //统一结果是否需要写泛型类。这里没有从结果里拿去信息
        apUserRealnameService.authFail(dto);
        return ResultVo.ok("操作成功");
    }

    @PostMapping("/authPass")
    public ResultVo authPass(@RequestBody ApUserRealnameAuthDto dto) {
        apUserRealnameService.authPass(dto);
        return ResultVo.ok("操作成功");
    }

}

