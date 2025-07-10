package com.leadnews.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.common.constants.BC;
import com.leadnews.common.dto.LoginUserDto;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.AppJwtUtil;
import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.wemedia.mapper.WmUserMapper;
import com.leadnews.wemedia.pojo.WmUser;
import com.leadnews.wemedia.service.WmUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.validation.Valid;

/**
 * @description <p>自媒体用户信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.service.impl
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    /**
     * 通过ap_user.id查询自媒体人信息
     *
     * @param userId
     * @return
     */
    @Override
    public WmUser getByUserId(Long userId) {
        return query().eq("ap_user_id", userId).one();
    }


    /**
     * 自媒体登录校验
     *
     * @param dto
     * @return
     */
    @Override
    public LoginUserVo loginCheck(@Valid LoginUserDto dto) { // 记住要参数校验
        //1. 通过用户名查询用户信息
        WmUser loginUser = query().eq("name", dto.getName()).one();
        //2. 如果不存在则报错
        if(null == loginUser){
            throw new LeadNewsException("用户名或密码错误!");
        }
        //3. 存在则
        //3.1 判断状态
        if (loginUser.getStatus() != BC.WmUserConstants.STATUS_OK) {
            throw new LeadNewsException("用户状态异常，请联系客服!");
        }
        //3.2 前端的密码加盐加密
        String encrypted = DigestUtils.md5DigestAsHex((dto.getPassword() + loginUser.getSalt()).getBytes());
        //3.3 对比数据库的密码
        if(!encrypted.equals(loginUser.getPassword())) {
            //3.4 如果不一致则报错
            throw new LeadNewsException("用户名或密码错误!");
        }
        //4. 生成token
        String token = AppJwtUtil.createToken(loginUser.getId());
        //5. 数据脱敏处理
        loginUser.setPassword(null);
        loginUser.setSalt(null);
        loginUser.setPhone(null);
        //6. 构建vo返回
        LoginUserVo vo = new LoginUserVo();
        vo.setUser(loginUser);
        vo.setToken(token);
        return vo;
    }
}
