package com.leadnews.article.controller;

import com.leadnews.article.dto.ArticleInfoDto;
import com.leadnews.article.pojo.ApArticle;
import com.leadnews.article.pojo.ApAuthor;
import com.leadnews.article.service.ApArticleService;
import com.leadnews.article.service.ApAuthorService;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController // 竟然忘了！！！
public class ApiController {

    @Resource
    private ApAuthorService apAuthorService;

    @Resource
    private ApArticleService apArticleService;


    @GetMapping("/api/apAuthor/getByApUserIdWmUserId/{apUserId}/{wmUserId}")
    public ResultVo<ApAuthor> getByApUserIdWmUserId(@PathVariable(value = "apUserId") Long apUserId,
                                                    @PathVariable(value = "wmUserId") Long wmUserId) {
        ApAuthor apAuthor = apAuthorService.getByApUserIdWmUserId(apUserId, wmUserId);
        return ResultVo.ok(apAuthor);
    }


    @PostMapping("/api/apAuthor/addApAuthor")
    public ResultVo addApAuthor(@RequestBody ApAuthor apAuthor) { /* 这里没写泛型，因为已经不需要得到ResultVo里面的数据了*/
        apAuthorService.save(apAuthor);
        return ResultVo.ok("操作成功");
    }

    /**
     * 做文章同步时从wemedia经由admin传来，远程调用article中该保存方法，将综合体信息保存到三张表中
     * @param articleInfoDto
     * @return
     */
    @PostMapping("/api/apArticle/saveArticleInfo")
    public ResultVo saveArticleInfo(@RequestBody ArticleInfoDto articleInfoDto) {
        apArticleService.saveArticleInfo(articleInfoDto);
        return ResultVo.ok("操作成功!");
    }


    /**
     * 通过文章id获取作者名称
     * @param articleId
     * @return
     */
    @GetMapping("/api/apArticle/getAuthorNameByArticleId/{articleId}")
    public ResultVo<String> getAuthorNameByArticleId(@PathVariable(value = "articleId") Long articleId){
        ApArticle apArticle = apArticleService.getById(articleId);
        if (null == apArticle) {
            throw new LeadNewsException("根据文章id远程调用获取文章作者名称失败");
        }
        Object authorName = apArticle.getAuthorName();
        /*ResultVo类中重载了很多方法，String会把authorName当成errorMsg。所以要用参数为Object的方法，把authorName当作data*/
        return ResultVo.ok(authorName);
    }
}
