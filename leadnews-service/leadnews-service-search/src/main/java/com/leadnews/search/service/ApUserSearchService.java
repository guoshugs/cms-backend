package com.leadnews.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.search.pojo.ApUserSearch;

/**
 * @description <p>APP用户搜索信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.search.service
 */
public interface ApUserSearchService extends IService<ApUserSearch> {

    /**
     * 异步存入redis，保存搜索记录
     * @param searchWords
     */
    void saveSearchRecord(String searchWords, Number id);
}
