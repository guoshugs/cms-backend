package com.leadnews.wemedia.vo;

import com.leadnews.wemedia.pojo.WmNews;
import com.leadnews.wemedia.pojo.WmUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.wemedia.vo
 */
@Data
public class WmNewsVo extends WmNews {
    //作者名称
    private String authorName;

    public static WmNewsVo build(WmNews wmNews, WmUser wmUser){
        WmNewsVo vo = new WmNewsVo();
        BeanUtils.copyProperties(wmNews, vo);
        vo.setAuthorName(wmUser.getName());
        return vo;
    }
}
