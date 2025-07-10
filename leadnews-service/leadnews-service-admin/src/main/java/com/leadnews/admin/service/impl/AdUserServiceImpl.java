package com.leadnews.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.admin.mapper.AdUserMapper;
import com.leadnews.admin.pojo.AdUser;
import com.leadnews.admin.service.AdUserService;
import com.leadnews.common.constants.SC;
import com.leadnews.common.dto.LoginUserDto;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.AppJwtUtil;
import com.leadnews.common.vo.LoginUserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

/**
 * @description <p>管理员用户信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.service.impl
 */
@Service
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements AdUserService {

    /**
     * 登录校验
     *
     * @param dto
     * @return
     */
    @Override
    public LoginUserVo loginCheck(LoginUserDto dto) {
        // 通过用户名查询用户信息
        // Optional.ifNullable作用: 判断对象是否为空，如果不为空则返回对象，如果为空则执行orElseThrow方法
        AdUser loginUser = Optional.ofNullable(query().eq("name", dto.getName()).one())
                .orElseThrow(()->new LeadNewsException("用户名或密码不正确!"));
        // 用户存在，判断状态是否正常
        if (loginUser.getStatus() != SC.USER_STATUS_OK) {
            throw new LeadNewsException("用户状态异常,请联系客服!");
        } /* 数据库中密码password=5d4e1a406d4a9edbf7b4f10c2a390405*/
        // 密码加盐加密
        String encrypted = DigestUtils.md5DigestAsHex((dto.getPassword() + loginUser.getSalt()).getBytes());
        // 与数据库密码比较
        /* 加盐加密后encrypted=81e158e10201b6d7aee6e35eaf744796*/
        if(!loginUser.getPassword().equals(encrypted)){
            throw new LeadNewsException("用户名或密码不正确!");
        }
        // 生成token
        String token = AppJwtUtil.createToken(loginUser.getId());

        // 构建返回对象
        LoginUserVo vo = new LoginUserVo();
        vo.setToken(token);
        // 有敏感信息，必须脱敏
        loginUser.setPassword(null);
        loginUser.setSalt(null);
        loginUser.setPhone(null);
        vo.setUser(loginUser);
        return vo;
    }
}
