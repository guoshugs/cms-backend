package com.leadnews.wemedia.service;

import com.leadnews.common.vo.PageResultVo;
import com.leadnews.wemedia.dto.WmMaterialPageRequestDto;
import com.leadnews.wemedia.pojo.WmMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>自媒体图文素材信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.service
 */
public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 素材分页查询
     * @param dto
     * @return
     */
    PageResultVo findPage(WmMaterialPageRequestDto dto);

}
