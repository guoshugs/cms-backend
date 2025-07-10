package com.leadnews.search.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.search.dto.SearchDto;
import com.leadnews.search.pojo.ApAssociateWords;

import java.util.List;

/**
 * @description <p>联想词 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.search.service
 */
public interface ApAssociateWordsService extends IService<ApAssociateWords> {
    /**
     * 搜索词联想
     * @param dto
     * @return
     */
    List<String> search(SearchDto dto);

    /**
     * 搜索词联想
     * 使用字典树
     * @param dto
     * @return
     */
    List<String> searchV2(SearchDto dto);
}
