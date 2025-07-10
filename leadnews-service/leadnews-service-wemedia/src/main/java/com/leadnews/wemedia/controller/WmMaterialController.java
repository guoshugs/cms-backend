package com.leadnews.wemedia.controller;


import com.leadnews.common.vo.PageResultVo;
import com.leadnews.core.controller.AbstractCoreController;
import com.leadnews.wemedia.dto.WmMaterialPageRequestDto;
import com.leadnews.wemedia.pojo.WmMaterial;
import com.leadnews.wemedia.service.WmMaterialService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description <p>自媒体图文素材信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmMaterialController",tags = "自媒体图文素材信息")
@RestController
@RequestMapping("/material")
public class WmMaterialController extends AbstractCoreController<WmMaterial> {

    private WmMaterialService wmMaterialService;

    @Autowired
    public WmMaterialController(WmMaterialService wmMaterialService) {
        super(wmMaterialService);
        this.wmMaterialService=wmMaterialService;
    }

    /**
     * 素材分页查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public PageResultVo findPage(@RequestBody WmMaterialPageRequestDto dto){
        PageResultVo vo = wmMaterialService.findPage(dto);
        return vo;
    }

}

