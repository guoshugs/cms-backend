package com.leadnews.user.service;

import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.user.dto.LoginDto;
import com.leadnews.user.dto.UserRelationDto;
import com.leadnews.user.pojo.ApUser;
import com.baomidou.mybatisplus.extension.service.IService;

/** day09 APP端用户关注行为
 * @description <p>APP用户信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.user.service
 */
public interface ApUserService extends IService<ApUser> {


    LoginUserVo loginCheck(LoginDto dto);

    /**
     * 关注作者与取消关注
     * @param dto
     * @return
     */
    void userFollow(UserRelationDto dto);
}
