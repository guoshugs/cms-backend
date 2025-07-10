package com.leadnews.article.controller;


import com.leadnews.article.dto.ArticleBehaviourDtoQuery;
import com.leadnews.article.dto.ArticleHomeDto;
import com.leadnews.article.pojo.ApArticle;
import com.leadnews.article.service.ApArticleService;
import com.leadnews.article.vo.ArticleInfoVo;
import com.leadnews.common.constants.BC;
import com.leadnews.common.vo.PageResultVo;
import com.leadnews.common.vo.ResultVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.leadnews.core.controller.AbstractCoreController;

import java.util.Map;

/** day09 APP端文章展示及深度分页
 * @description <p>已发布的文章信息</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApArticleController",tags = "已发布的文章信息")
@RestController
@RequestMapping("/article")
public class ApArticleController extends AbstractCoreController<ApArticle> {

    private ApArticleService apArticleService;

    @Autowired
    public ApArticleController(ApArticleService apArticleService) {
        super(apArticleService);
        this.apArticleService=apArticleService;
    }
    /* --day09 深度分页：不论查看前一页还是后一页，都使用同一个findPage方法，将上翻下翻做成参数传递--*/

    /**
     * APP端加载第一页数据
     * @param dto
     * @return
     */
    @PostMapping("/load")
    public PageResultVo<ApArticle> load(@RequestBody ArticleHomeDto dto){
        PageResultVo<ApArticle> resultVo = apArticleService.findPage(dto, BC.ArticleConstants.LOAD_NEW);
        return resultVo;
    }

    /**
     * 加载下一页， 往上推
     * @param dto
     * @return
     */
    @PostMapping("/loadmore")
    public PageResultVo<ApArticle> loadMore(@RequestBody ArticleHomeDto dto){
        PageResultVo<ApArticle> resultVo = apArticleService.findPage(dto, BC.ArticleConstants.LOAD_MORE);
        return resultVo;
    }

    /**
     * 加载最新数据  往下拉
     * @param dto
     * @return
     */
    @PostMapping("/loadnew")
    public PageResultVo<ApArticle> loadNew(@RequestBody ArticleHomeDto dto){
        PageResultVo<ApArticle> resultVo = apArticleService.findPage(dto, BC.ArticleConstants.LOAD_NEW);
        return resultVo;
    }

    /**
     * 加载文章 详情
     * @param paramMap 请求参数只有1个，就是articleId，并没有放路径当中（@PathVariable)，
     *                 而是放在了载荷中(@RequestBody)，所以这里用map来接收的。
     * @return
     */
    @PostMapping("/load_article_info")
    public ResultVo<ArticleInfoVo> loadArticleInfo(@RequestBody Map<String, Long> paramMap) {
        ArticleInfoVo articleInfoVo = apArticleService.loadArticleInfo(paramMap);
        return ResultVo.ok(articleInfoVo);
    }



    /**
     * 加载用户对文章的行为
     * @param dto
     * @return
     */
    @PostMapping("/load_article_behavior")
    public ResultVo<Map<String,Object>> loadArticleBehaviour(@RequestBody ArticleBehaviourDtoQuery dto){
        Map<String,Boolean> resultMap = apArticleService.loadArticleBehaviour(dto);
        return ResultVo.ok(resultMap);
    }

}

