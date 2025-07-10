package com.leadnews.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leadnews.article.feign.ApAuthorFeign;
import com.leadnews.article.pojo.ApAuthor;
import com.leadnews.common.constants.BC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.vo.PageResultVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.user.dto.ApUserRealnameAuthDto;
import com.leadnews.user.dto.ApUserRealnamePageRequestDto;
import com.leadnews.user.mapper.ApUserMapper;
import com.leadnews.user.pojo.ApUser;
import com.leadnews.user.pojo.ApUserRealname;
import com.leadnews.user.mapper.ApUserRealnameMapper;
import com.leadnews.user.service.ApUserRealnameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.wemedia.feign.WmUserFeign;
import com.leadnews.wemedia.pojo.WmUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description <p>APP实名认证信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.user.service.impl
 */
@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    @Resource
    private ApUserMapper apUserMapper;

    @Resource
    private WmUserFeign wmUserFeign;

    @Resource
    private ApAuthorFeign apAuthorFeign;

    /**
     * 实名认证列表 分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageResultVo findPage(ApUserRealnamePageRequestDto dto) {
        //1. 分页参数设置
        IPage<ApUserRealname> pageInfo = new Page<>(dto.getPage(), dto.getSize());
        //2. 构建查询条件
        LambdaQueryWrapper<ApUserRealname> lqw = new LambdaQueryWrapper<>();
        lqw.eq(null != dto.getStatus(), ApUserRealname::getStatus, dto.getStatus());
        //3. 查询
        page(pageInfo, lqw);
        //4. 封装结果返回
        return PageResultVo.pageResult(dto.getPage(), dto.getSize(), pageInfo.getTotal(), pageInfo.getRecords());
    }

    @Override
    public void authFail(ApUserRealnameAuthDto dto) {
        ApUserRealname updatePojo = new ApUserRealname();
        updatePojo.setStatus(BC.ApUserRealnameConstants.AUTH_REJECT);
        updatePojo.setReason(dto.getMsg());
        updatePojo.setUpdatedTime(LocalDateTime.now());
        //update().eq("user_id", dto.getId()).update(updatePojo);
        updatePojo.setId(dto.getId());
        updateById(updatePojo);
    }

    /*================day03,user实名认证成功后调用其他微服务================*/

    @Override
    @Transactional // user微服务下操作了2张表，ap_user_realname, ap_user。所以加上Transactional保持事务一致性
    // 在3、4中才涉及是否有分布式事务的讨论。Transactional跟它们不相关。
    public void authPass(ApUserRealnameAuthDto dto) {
        // 1 更新ap_user_realname
        // 2 返回结果给ap_user
        // 3 完成创作者管理系统wmmedia的实名认证（不是注册，有可能是老创作人，也忧可能新用户）
        // 4 完成素材库管理系统article的注册
        updateUserRealName(dto);
        /* 调试时前端的超时时间是1s，1s没有响应就会报超时。微服务的超时时间也是1s，除非去配置了Feign的默认的超时时间
        * 但是不建议调试微服务时时间太长。哪个微服务有问题，它启动的时候去调试就好了。所以调试的主要是用户微服务种的代码
        * 所以在上面下断点。
        * 网关如果断的时间太长，就会任务服务下线了。下次就要重启或者等到nacos的服务发现。
        * 在userApp上下了断点，就相当于把它卡住了。它的心跳也被阻塞了，所以它不会向nacos发送心跳。
        * nacos会认user微服务挂了。*/
        Long userId = updateApUser(dto);
        /*上面2张表更新都在user库，下面就需要跨库了*/
        WmUser wmUser = createWmMedia(userId);
        createApAuthor(wmUser);
    }

    /**
     * 操作ap_user_realname,更新状态,审核通过
     * @param dto
     */
    private void updateUserRealName(ApUserRealnameAuthDto dto) {
        // 更新对象
        ApUserRealname updatePojo = new ApUserRealname();
        // 设置需要更新的字段值
        updatePojo.setStatus(BC.ApUserRealnameConstants.AUTH_PASS);
        updatePojo.setUpdatedTime(LocalDateTime.now());
        // 设置更新的条件
        updatePojo.setId(dto.getId());// 注意list页面传过来的本来就是id！并不是userId。搞错很多次了！
        // 执行更新
        updateById(updatePojo);
    }


    /**
     * 操作ap_user，更新flag，是否认证状态
     * @param dto
     */
    private Long updateApUser(ApUserRealnameAuthDto dto) {
        // 更新对象
        ApUser updatePojo = new ApUser();
        // 设置需要更新的字段值
        updatePojo.setIsIdentityAuthentication(BC.ApUserConstants.AUTHENTICATED);
        updatePojo.setFlag(BC.ApUserConstants.FLAG_WEMEDIA);
        // 设置更新的条件, 获取用户id
        // 通过认证记录的id查询认证记录，记录中有user_id
        ApUserRealname apUserRealname = getById(dto.getId());
        updatePojo.setId(apUserRealname.getUserId());
        // 执行更新
        apUserMapper.updateById(updatePojo);
        return apUserRealname.getUserId();
    }

    /**
     * 创建自媒体人
     * @param userId
     */
    private WmUser createWmMedia(Long userId) {
        //1. 通过用户id查询自媒体人信息（wm_user）自媒体微服里的，需要远程调用
        ResultVo<WmUser> getResult = wmUserFeign.getByUserId(userId);
        // 解析远程调用结果
        if (!getResult.isSuccess()) {
            // 不成功，说明自媒体微服务出错了，要抛异常，让上面事务回滚
            throw new LeadNewsException("远程调用自媒体微服：通过用户id查询自媒体人信息失败了: " + getResult.getErrorMessage());
        }
        WmUser wmUser = getResult.getData(); // 包装过来的结果不一定能查到，可能回来的是null
        if(null == wmUser) {
            //2. 没有自媒体人信息

            // 构建自媒体人信息
            wmUser = buildWmUser(userId);

            //2.1 添加自媒体人, 远程调用
            ResultVo<WmUser> addResult = wmUserFeign.addWmUser(wmUser);
            // 为何里面要加泛型，因为Result要经过网络传输，传递的是json数据，如果不以对象的形式传递，就会默认以LinkedHashMap的形式传递，这样就拿不到对象了
            // 这时wmUser对象中有id吗？
            if (!addResult.isSuccess()) {
                //失败报错
                throw new LeadNewsException("远程调用自媒体微服: 添加自媒体人信息失败: " + addResult.getErrorMessage());
            }
            // 调用之前是没有id的，只有生成之后才有id
            wmUser = addResult.getData();//新增自媒体用户信息, 才能得新增的id
            System.out.println(wmUser);
        }
        //3. 有自媒体人信息
        //4. 返回自媒体人的id
        return wmUser;
    }

    private WmUser buildWmUser(Long userId) {
        WmUser wmUser = new WmUser();
        // 查询ap_user信息, 自媒体人的部分信息默认使用ap_user的信息
        ApUser apUser = apUserMapper.selectById(userId);
        // 属性相同则复制
        BeanUtils.copyProperties(apUser, wmUser);
        // 特殊id处理, ap_user表中的id是用户id对应的是wm_user.user_id App端用户id, wm_user.id是自媒体人的id,
        wmUser.setId(null);
        // 属性不同，则手工设置
        wmUser.setApUserId(apUser.getId());//这是后来添加的
        wmUser.setNickname(wmUser.getName());
        wmUser.setStatus(BC.WmUserConstants.STATUS_OK);
        wmUser.setType(BC.WmUserConstants.TYPE_PERSONAL);
        wmUser.setScore(0);// 初始化为0
        wmUser.setCreatedTime(LocalDateTime.now());
        return wmUser;
    }

    /**
     * 远程调用文章微服，添加作者
     * @param wmUser
     */
    private void createApAuthor(WmUser wmUser) {

        //1. 先通过用户id与自媒体人id查询作者信息
        //【注意】参数顺序：先用户id，再自媒体人id
        ResultVo<ApAuthor> getResult = apAuthorFeign.getByApUserIdWmUserId(wmUser.getApUserId(), wmUser.getId());
        if (!getResult.isSuccess()) {
            throw new LeadNewsException("远程调用文章微服:通过用户id与自媒体人id查询作者信息失败:" + getResult.getErrorMessage());
        }
        ApAuthor author = getResult.getData();
        //2. 如果没有作者，则添加
        if(null == author){
            author = new ApAuthor();
            author.setName(wmUser.getName());
            author.setUserId(wmUser.getApUserId());
            author.setWmUserId(wmUser.getId());
            author.setType(BC.ApAuthorConstants.A_MEDIA_USER);
            author.setCreatedTime(LocalDateTime.now());
            // 远程调用添加
            ResultVo addResult = apAuthorFeign.addApAuthor(author);
            if(!addResult.isSuccess()){
                throw new LeadNewsException("远程调用文章微服:添加作者信息失败:" + addResult.getErrorMessage());
            }
        }
        //3. 如果有则不处理
    }

}
