package com.leadnews.sync.mapper;

import com.leadnews.search.document.ArticleInfoDocument;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleMapper {
    @Select(value="select count(1) from ap_article apa inner join ap_article_config apc on apa.id=apc.id where publish_time<=#{publishTime}") // 因为在配置表中，没设置articleId，写的就是id
    Long selectCount(@Param(value="publishTime") LocalDateTime publishTime);

    @Select(value="select apa.*,apc.is_down,apc.is_delete from ap_article apa inner join ap_article_config apc on apa.id=apc.id where apa.publish_time<=#{publishTime} limit #{start},#{size}")
    @ResultMap(value = "articleDocumentResultMap")
    List<ArticleInfoDocument> selectByPage(@Param(value = "start") Long start,
                                 @Param(value="size") Long size,@Param(value="publishTime") LocalDateTime publishTime);

    @Select(value="select apa.*,apc.is_down,apc.is_delete from ap_article apa inner join ap_article_config apc on apa.id=apc.id " +
            "where publish_time > #{redisTime} and publish_time <= #{nowTime}")
    @Results(id = "articleDocumentResultMap",value = {
        @Result(property = "id",column = "id"),
        @Result(property = "title",column = "title"),
        @Result(property = "authorId",column = "author_id"),
        @Result(property = "authorName",column = "author_name"),
        @Result(property = "channelId",column = "channel_id"),
        @Result(property = "channelName",column = "channel_name"),
        @Result(property = "layout",column = "layout"),
        @Result(property = "images",column = "images"),
        @Result(property = "likes",column = "likes"),
        @Result(property = "collection",column = "collection"),
        @Result(property = "comment",column = "comment"),
        @Result(property = "views",column = "views"),
        @Result(property = "createdTime",column = "created_time"),
        @Result(property = "publishTime",column = "publish_time"),
        @Result(property = "isDown",column = "is_down"),
        @Result(property = "isDelete",column = "is_delete")
    })
    List<ArticleInfoDocument> selectForCondition(@Param(value="redisTime") LocalDateTime publishTime,@Param(value="nowTime") LocalDateTime nowTime);
}
