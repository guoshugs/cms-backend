package com.leadnews.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.admin.pojo.AdUser;
import com.leadnews.common.dto.LoginUserDto;
import com.leadnews.common.vo.LoginUserVo;

/**
 * @description <p>管理员用户信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.service
 */
public interface AdUserService extends IService<AdUser> {

    /**
     * 登录校验
     * @param dto
     * @return
     */
    LoginUserVo loginCheck(LoginUserDto dto);
}
