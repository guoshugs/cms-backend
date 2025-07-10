package com.leadnews.search.service.impl;

import com.leadnews.common.constants.BC;
import com.leadnews.search.document.ArticleInfoDocument;
import com.leadnews.search.dto.SearchDto;
import com.leadnews.search.service.ArticleInfoDocumentSearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleInfoDocumentSearchServiceImpl implements ArticleInfoDocumentSearchService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 文章搜索
     *
     * @param dto@return
     */
    @Override
    public List<ArticleInfoDocument> search(SearchDto dto) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder(); //搜索执行器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();//条件构建器，复合查询条件，需要被装在大执行器中执行
        if (!StringUtils.isEmpty(dto.getSearchWords())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", dto.getSearchWords()));
        }
        boolQueryBuilder.filter(QueryBuilders.termQuery("isDown", 0))
                .filter(QueryBuilders.termQuery("isDelete", 0));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("publishTime")
                                    .lt(dto.getMinBehotTime().getTime()));// publishTime的映射是long类型的。minBehotTime是Date类型的，需要把参数转换成long
        if(!BC.ArticleConstants.DEFAULT_TAG.equals(dto.getTag())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("channelId",Integer.parseInt(dto.getTag())));
        }
        /* 在调试的时候可以在debug中找到boolQueryBuilder，右键copyvalue，就可以复制在kibana中，预先查看写的条件对不对*/

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder); // 把条件装进执行器，返回的是原来的执行器
        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,20));//这里的分页start是从0开始，不是之前分页从1开始的
        //nativeSearchQueryBuilder.withPageable(PageRequest.of(dto.getPageNum() - 1, dto.getPageSize()));

        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("publishTime").order(SortOrder.DESC));

        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title"));
        nativeSearchQueryBuilder.withHighlightBuilder(new HighlightBuilder()
                                    .preTags("<font color='red'>")
                                    .postTags("</font>")
                                    .requireFieldMatch(false));

        NativeSearchQuery build2Query = nativeSearchQueryBuilder.build(); // 把上面装好条件的执行器再构建一下，就能得到组装好的查询
        SearchHits<ArticleInfoDocument> results = elasticsearchRestTemplate.search(build2Query, ArticleInfoDocument.class);

        List<SearchHit<ArticleInfoDocument>> searchHits = results.getSearchHits();
        List<ArticleInfoDocument> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(searchHits)) {
            for (SearchHit<ArticleInfoDocument> searchHit : searchHits) {
                ArticleInfoDocument articleInfoDocument = searchHit.getContent();
                resultList.add(articleInfoDocument);

                List<String> titleList = searchHit.getHighlightField("title");
                if (!CollectionUtils.isEmpty(titleList)) {
                    // 设置高亮
                    articleInfoDocument.setTitle(String.join(",", titleList));
                }
            }
        }
        return resultList;
    }
}
