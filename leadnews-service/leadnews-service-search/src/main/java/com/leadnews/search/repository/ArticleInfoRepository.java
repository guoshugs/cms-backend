package com.leadnews.search.repository;

import com.leadnews.search.document.ArticleInfoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/** repository也要给加上。是spring data jpa的操作方式，用它来操作数据库
 * @version 1.0
 * @description 说明
 * @package com.leadnews.search.repository
 */
@Repository
public interface ArticleInfoRepository extends ElasticsearchRepository<ArticleInfoDocument,String> {
}
