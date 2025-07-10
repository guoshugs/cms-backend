package com.leadnews.article.vo;

import com.leadnews.article.pojo.ApArticle;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @version 1.0
 * @description 原来的article没有分数值，无法比较。需要添加一个字段，新添加一个子类更合适，不用修改原表。
 * @package com.leadnews.article.vo
 */
@Data
public class HotArticleVo extends ApArticle {
    // 文章对应的分数值
    private Integer score;

    public static HotArticleVo build(ApArticle apArticle, Integer score){
        HotArticleVo vo = new HotArticleVo();
        BeanUtils.copyProperties(apArticle,vo);
        vo.setScore(score);
        return vo;
    }
}
