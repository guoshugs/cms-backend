package com.leadnews.wemedia.feign;

import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.pojo.WmMaterial;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.wemedia.feign
 */
@FeignClient(name = "leadnews-wemedia",contextId = "wmMaterialFeign")
public interface WmMaterialFeign {

    /**
     * 远程调用添加素材记录
     * @param wmMaterial
     * @return
     */
    @PostMapping("/api/wmMaterial/add")
    ResultVo addWmMaterial(@RequestBody WmMaterial wmMaterial);

}
