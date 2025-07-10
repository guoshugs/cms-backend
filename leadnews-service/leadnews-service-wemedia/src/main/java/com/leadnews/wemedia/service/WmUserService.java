package com.leadnews.wemedia.service;

import com.leadnews.common.dto.LoginUserDto;
import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.wemedia.pojo.WmUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>自媒体用户信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.service
 */
public interface WmUserService extends IService<WmUser> {

    /**
     * 自媒体登录校验
     * @param dto
     * @return
     */
    LoginUserVo loginCheck(LoginUserDto dto);

    WmUser getByUserId(Long userId);


}
