package com.leadnews.article.service.impl;

import com.leadnews.article.pojo.ApAuthor;
import com.leadnews.article.mapper.ApAuthorMapper;
import com.leadnews.article.service.ApAuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description <p>APP文章作者信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service.impl
 */
@Service
public class ApAuthorServiceImpl extends ServiceImpl<ApAuthorMapper, ApAuthor> implements ApAuthorService {


    @Override
    public ApAuthor getByApUserIdWmUserId(Long apUserId, Long wmUserId) {
        ApAuthor apAuthor = query().eq("user_id", apUserId).eq("wm_user_id", wmUserId).one();
        return apAuthor;
    }
}
