package com.leadnews.article.service;

import com.leadnews.article.dto.ArticleBehaviourDtoQuery;
import com.leadnews.article.dto.ArticleHomeDto;
import com.leadnews.article.dto.ArticleInfoDto;
import com.leadnews.article.dto.ArticleVisitStreamMess;
import com.leadnews.article.pojo.ApArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leadnews.article.vo.ArticleInfoVo;
import com.leadnews.common.vo.PageResultVo;

import java.util.Map;

/**
 * @description <p>已发布的文章信息 业务接口</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service
 */
public interface ApArticleService extends IService<ApArticle> {
    /*-------------------day08------------------------------*/
    /**
     * 做文章同步时从wemedia经由admin传来，远程调用article中该保存方法，将综合体信息保存到三张表中
     * @param articleInfoDto
     * @return
     */
    void saveArticleInfo(ArticleInfoDto articleInfoDto);

    /*-------------------day09------------------------------*/
    /**
     * APP端深度分页查询
     * @param dto
     * @return
     */
    PageResultVo<ApArticle> findPage(ArticleHomeDto dto, int loadType);


    /**
     * 加载文章 详情
     * @param paramMap 请求参数只有1个，就是articleId，并没有放路径当中（@PathVariable)，
     *                 而是放在了载荷中(@RequestBody)，所以这里用map来接收的。
     * @return
     */
    ArticleInfoVo loadArticleInfo(Map<String, Long> paramMap);


    /*-------------------day10用户关系行为展示------------------------------*/
    /**
     * 加载用户对文章的行为
     * @param dto
     * @return
     */
    Map<String, Boolean> loadArticleBehaviour(ArticleBehaviourDtoQuery dto);

    /*-------------------day12热点文章------------------------------*/
    /**
     * 定时任务 热点文章计算，存入redis
     */
    void computeAndSave2Redis();

    /**
     * kafka消费者，流计算热点文章
     * @param articleVisitStreamMess
     */
    void computeAndSave2RedisSingle(ArticleVisitStreamMess articleVisitStreamMess);
}
