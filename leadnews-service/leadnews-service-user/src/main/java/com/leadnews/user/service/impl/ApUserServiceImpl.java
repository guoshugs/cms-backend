package com.leadnews.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leadnews.article.feign.ApArticleFeign;
import com.leadnews.behaviour.dto.FollowBehaviourDto;
import com.leadnews.common.constants.BC;
import com.leadnews.common.constants.SC;
import com.leadnews.common.enums.HttpCodeEnum;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.AppJwtUtil;
import com.leadnews.common.util.RequestContextUtil;
import com.leadnews.common.vo.LoginUserVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.dto.LoginDto;
import com.leadnews.user.dto.UserRelationDto;
import com.leadnews.user.mapper.ApUserFanMapper;
import com.leadnews.user.mapper.ApUserFollowMapper;
import com.leadnews.user.pojo.ApUser;
import com.leadnews.user.mapper.ApUserMapper;
import com.leadnews.user.pojo.ApUserFan;
import com.leadnews.user.pojo.ApUserFollow;
import com.leadnews.user.service.ApUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

/**day09_app端基本功能展示
 * @description <p>APP用户信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.user.service.impl
 */
@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Resource
    private ApUserFanMapper apUserFanMapper;

    @Resource
    private ApUserFollowMapper apUserFollowMapper;

    @Resource
    private ApArticleFeign apArticleFeign;

    @Resource
    private KafkaTemplate kafkaTemplate;


    /*----------------day09_app端基本功能展示-------------------------*/

    @Override
    public LoginUserVo loginCheck(LoginDto dto) {

        HashMap<String, Object> claimsMap = new HashMap<>();

        String phone = dto.getPhone();
        String password = dto.getPassword();

        // 不匿名，判断；匿名，直接生成token
        Long loginUserId = SC.ANONYMOUS_USER_ID;
        LoginUserVo vo = new LoginUserVo();

        if (!StringUtils.isEmpty(phone)) {
            //1. 通过手机号查询用户信息
            //2. 如果不存在则报错
            ApUser apUser = Optional.ofNullable(query().eq("phone", phone).one()).orElseThrow(() -> new LeadNewsException("手机号码或密码错误"));
            //3. 存在则
            //3.1 判断状态
            if (apUser.getStatus() == 1) {
                throw new LeadNewsException("当前账号被锁定");
            }
            //3.2 前端的密码加盐加密
            password = DigestUtils.md5DigestAsHex((password + apUser.getSalt()).getBytes());
            //3.3 对比数据库的密码
            if (!password.equals(apUser.getPassword())) {
                //3.4 如果不一致则报错
                throw new LeadNewsException("用户名或密码不匹配");
            }
            loginUserId = apUser.getId();
            //5. 数据脱敏处理
            apUser.setSalt(null);
            apUser.setPassword(null);
            apUser.setPhone(null);
            vo.setUser(apUser);
            /* -----day10评论微服务，重写createToken方法，把头像和昵称和原来的id一并存入map，用map来构建token------*/
            claimsMap.put(SC.LOGIN_USER_NAME, apUser.getName());
            claimsMap.put(SC.LOGIN_USER_IMAGE, apUser.getImage());
        }
        // 在能查到实际用户的情况下，把用户的昵称和头像存到map里，不管有没有id，都把匿名或id存进map里
        claimsMap.put("id", loginUserId);
        // 匿不匿名都要生成token，所以生成过程要在最外面的判断之外
        String token = AppJwtUtil.createToken(claimsMap);
        vo.setToken(token);
        return vo;
    }


    /** 关注行为涉及多表查询
     * 如果是匿名用户，需要先登录
     * 判断行为是关注还是取关
     * 关注时，如果之前关注过就报错，如果没有就操作2张表添加记录
     * 取关时，如果之前关注过就操作2张表删除记录，如果没有就报错
     * @param dto
     */
    @Override
    @Transactional
    public void userFollow(UserRelationDto dto) { //0  关注  1  取消。操作fan表和follow表
        // 先判断当前用户是否登录
        boolean anonymous = RequestContextUtil.isAnonymous();
        if(anonymous) {
            throw new LeadNewsException(HttpCodeEnum.NEED_LOGIN);
        }

        Integer operation = dto.getOperation();
        Long articleId = dto.getArticleId();
        Long authorId = dto.getAuthorId();

        LambdaQueryWrapper<ApUserFollow> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ApUserFollow::getUserId, RequestContextUtil.getUserId());
        lqw.eq(ApUserFollow::getFollowId, dto.getAuthorId());
        ApUserFollow apUserFollow = apUserFollowMapper.selectOne(lqw);

        if(0 == operation){ // 关注
            // 之前有没有关注过，看自己的follow表有没有followId
            if(null != apUserFollow){
                throw new LeadNewsException("已关注");
            }

            // 当前用户的follow+1，up主的fan+1
            apUserFollow = new ApUserFollow();
            apUserFollow.setUserId(RequestContextUtil.getUserId());
            apUserFollow.setFollowId(authorId);
            ResultVo<String> authorNameResult = apArticleFeign.getAuthorNameByArticleId(articleId);
            if(!authorNameResult.isSuccess()){
                throw new LeadNewsException("远程调用文章表获取作者姓名失败");
            }
            apUserFollow.setFollowName(authorNameResult.getData());
            apUserFollow.setLevel(0);
            apUserFollow.setIsNotice(1);
            apUserFollow.setCreatedTime(LocalDateTime.now());
            apUserFollowMapper.insert(apUserFollow);

            ApUserFan apUserFan = new ApUserFan();
            apUserFan.setUserId(authorId);
            apUserFan.setFansId(RequestContextUtil.getUserId());
            apUserFan.setFansName(getById(RequestContextUtil.getUserId()).getName());
            apUserFan.setLevel(0);
            apUserFan.setCreatedTime(LocalDateTime.now());
            apUserFan.setIsDisplay(1);
            apUserFan.setIsShieldLetter(0);
            apUserFan.setIsShieldComment(0);
            apUserFanMapper.insert(apUserFan);

            /*-------------------day10_app端用户行为处理-------------------------*/
            // 只需要在用户添加关注行为之后通知行为端即可，取关不需要通知
            FollowBehaviourDto followBehaviourDto = new FollowBehaviourDto();

            followBehaviourDto.setArticleId(articleId);
            followBehaviourDto.setFollowId(authorId);
            followBehaviourDto.setUserId(RequestContextUtil.getUserId());

            kafkaTemplate.send(BC.MqConstants.FOLLOW_BEHAVIOR_TOPIC, JSON.toJSONString(followBehaviourDto));


        } else if (1 == operation) { // 取关
            // 之前有没有关注过
            if(null == apUserFollow){
                throw new LeadNewsException("没关注过");
            }

            // 当前用户的follow-1，up主的fan-1
            LambdaQueryWrapper<ApUserFollow> followQuery = new LambdaQueryWrapper<>();
            followQuery.eq(ApUserFollow::getFollowId, authorId);
            followQuery.eq(ApUserFollow::getUserId, RequestContextUtil.getUserId());
            apUserFollowMapper.delete(followQuery);

            LambdaQueryWrapper<ApUserFan> fanQuery = new LambdaQueryWrapper<>();
            fanQuery.eq(ApUserFan::getUserId, dto.getAuthorId());
            fanQuery.eq(ApUserFan::getFansId, RequestContextUtil.getUserId());
            apUserFanMapper.delete(fanQuery);

        } else {
            throw new LeadNewsException("操作不确定");
        }
    }
}
