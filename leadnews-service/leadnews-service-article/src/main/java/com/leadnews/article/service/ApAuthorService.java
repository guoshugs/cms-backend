package com.leadnews.article.service;

import com.leadnews.article.pojo.ApAuthor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description <p>APP文章作者信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service
 */
public interface ApAuthorService extends IService<ApAuthor> {


    ApAuthor getByApUserIdWmUserId(Long apUserId, Long wmUserId);

}
