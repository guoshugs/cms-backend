package com.leadnews.article.feign;

import com.leadnews.article.dto.ArticleInfoDto;
import com.leadnews.common.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "leadnews-article", contextId = "apArticleFeign")
public interface ApArticleFeign {

    @PostMapping("/api/apArticle/saveArticleInfo")
    ResultVo saveArticleInfo(@RequestBody ArticleInfoDto articleInfoDto);

    /**
     * 通过文章id获取作者名称
     * @param articleId
     * @return
     */
    @GetMapping("/api/apArticle/getAuthorNameByArticleId/{articleId}")
    ResultVo<String> getAuthorNameByArticleId(@PathVariable(value = "articleId") Long articleId);

}
