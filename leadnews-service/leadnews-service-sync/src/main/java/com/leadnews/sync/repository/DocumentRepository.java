package com.leadnews.sync.repository;

import com.leadnews.search.document.ArticleInfoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentRepository extends ElasticsearchRepository<ArticleInfoDocument, Long> {
}
