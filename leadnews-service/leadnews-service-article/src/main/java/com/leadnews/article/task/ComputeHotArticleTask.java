package com.leadnews.article.task;

import com.leadnews.article.service.ApArticleService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.task
 */
@Component
@Slf4j
public class ComputeHotArticleTask {

    @Resource
    private ApArticleService apArticleService;

    @XxlJob("computeHotArticle")
    public ReturnT<String> computeHotArticle(String params){
        log.info("热点文章统计开始...");
        XxlJobLogger.log("热点文章统计开始...");
        apArticleService.computeAndSave2Redis();
        log.info("热点文章统计完成...");
        XxlJobLogger.log("热点文章统计完成...");
        return ReturnT.SUCCESS;
    }
}
