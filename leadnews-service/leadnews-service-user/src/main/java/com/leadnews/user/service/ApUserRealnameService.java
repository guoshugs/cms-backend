package com.leadnews.user.service;

import com.leadnews.common.vo.PageResultVo;
import com.leadnews.user.dto.ApUserRealnameAuthDto;
import com.leadnews.user.dto.ApUserRealnamePageRequestDto;
import com.leadnews.user.pojo.ApUserRealname;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>APP实名认证信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.user.service
 */
public interface ApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 实名认证列表 分页查询
     * @param dto
     * @return
     */
    PageResultVo findPage(ApUserRealnamePageRequestDto dto);

    /**
     * 实名认证失败
     * @param dto
     */
    void authFail(ApUserRealnameAuthDto dto);

    /**
     * 实名认证成功
     * @param dto
     */
    void authPass(ApUserRealnameAuthDto dto);
}
