package com.leadnews.search.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

/** 创建pojo，通过注解自动创建映射
 * 看一个字段是否要创建索引，要看它是否是查询条件。
 * @version 1.0
 * @description 说明
 * @package com.leadnews.search.document
 */
@Data
@Document(indexName = "article")
public class ArticleInfoDocument implements Serializable {

    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word") // text能分词，最后能搜索的就是title
    private String title;

    private Long authorId;

    @Field(type = FieldType.Keyword) // 作者名不能分词
    private String authorName;

    private Integer channelId;

    @Field(type = FieldType.Keyword) // 频道名不能分词
    private String channelName;

    private Integer layout;

    @Field(type = FieldType.Keyword) // 图片不能分词
    private String images;

    private Integer likes;

    private Integer collection;

    private Integer comment;

    private Integer views;

    private LocalDateTime createdTime;

    private LocalDateTime publishTime;

    // 在查询响应的实体document类中，createdTime和publishTime都是用的LocalDateTime来接收,
    // 但ES中的映射中这两个字段是使用的long类型。需要进行类型转换。mongoDB中存储的日期也是long类型的
    // 如果响应的实体document非得使用LocalDateTime的话，需要在配置中加一个ES转换器，让ES查询出来的结果能对比上
    private Integer isDown;

    private Integer isDelete;
    
}
