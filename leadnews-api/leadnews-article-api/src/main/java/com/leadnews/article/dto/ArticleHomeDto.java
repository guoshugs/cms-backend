package com.leadnews.article.dto;

import lombok.Data;

import java.util.Date;

/** 深度分页问题需要在请求中记录最大最小属性。该dto放在api包中是因为在ES查询微服务中也会用到深度分页。
 * @version 1.0
 * @description 说明
 * @package com.leadnews.article.dto
 */
@Data
public class ArticleHomeDto {
    //页码
    private Long loaddir;
    // 最大时间
    private Date maxBehotTime;
    // 最小时间
    private Date minBehotTime;
    // 分页大小
    private Long size;
    // 频道ID
    private String tag;
    // size大小检测
    public void checkSize(){
        this.size = (null!=this.size&&this.size>=1&&this.size<=50)?this.size:10;
    }
}
