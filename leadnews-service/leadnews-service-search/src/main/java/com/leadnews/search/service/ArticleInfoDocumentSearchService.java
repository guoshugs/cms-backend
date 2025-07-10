package com.leadnews.search.service;

import com.leadnews.search.document.ArticleInfoDocument;
import com.leadnews.search.dto.SearchDto;

import java.util.List;

public interface ArticleInfoDocumentSearchService {

    /**
     * 文章搜索
     * @param searchDto
     * @return
     */
    List<ArticleInfoDocument> search(SearchDto dto);
}
