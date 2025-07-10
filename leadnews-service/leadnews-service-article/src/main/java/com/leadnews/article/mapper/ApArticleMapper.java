package com.leadnews.article.mapper;

import com.leadnews.article.dto.ArticleHomeDto;
import com.leadnews.article.dto.ArticleVisitStreamMess;
import com.leadnews.article.pojo.ApArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leadnews.article.vo.ArticleInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @description <p>已发布的文章信息 Mapper 接口</p>
 *
 * @version 1.0
 * @package com.leadnews.article.mapper
 */
@Repository
public interface ApArticleMapper extends BaseMapper<ApArticle> {
    /**
     * App 端文章列表深度分页
     * @param dto
     * @return
     * 最好取别名
     */
    List<ApArticle> findPage(@Param("dto") ArticleHomeDto dto,
                             @Param("loadType") int loadType);


    ArticleInfoVo loadArticleInfo(@Param("articleId") Long articleId);

    /**
     * 查询指定时间到现在的数据
     * @param dateBefore
     * @return
     */
    @Select(("select a.* from ap_article a inner join ap_article_config ac on a.id=ac.id where ac.is_down=0 and ac.is_delete=0 and a.publish_time>=#{dateBefore}"))
    List<ApArticle> selectAfterDate(LocalDate dateBefore);



    /**
     * 更新用户的行为数量
     * @param articleVisitStreamMess
     */
    @Update("update ap_article set likes=likes+#{like}, collection=collection+#{collect}, " +
            "comment=comment+#{comment}, views=views+#{view} where id=#{articleId}")
    void updateNumber(ArticleVisitStreamMess articleVisitStreamMess);
}
