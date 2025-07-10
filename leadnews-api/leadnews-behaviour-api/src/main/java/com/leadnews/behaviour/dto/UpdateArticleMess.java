package com.leadnews.behaviour.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 更新评论、收藏、阅读行为接口的发送消息数据格式
 * @package com.leadnews.behaviour.dto
 */
@Data
public class UpdateArticleMess {

    /**
     * 修改文章的字段类型
     */
    private UpdateArticleType type;
    /**
     * 文章ID
     */
    private Long articleId;
    /**
     * 修改数据的增量，可为正负
     */
    private Integer num;

    public enum UpdateArticleType{
        COLLECTION(1),COMMENT(2),LIKES(3),VIEWS(4);
        
        private int value=0;
        UpdateArticleType(int i) {
            this.value = i;
        }
        
        public int getValue(){
            return this.value;
        }
    }
}
