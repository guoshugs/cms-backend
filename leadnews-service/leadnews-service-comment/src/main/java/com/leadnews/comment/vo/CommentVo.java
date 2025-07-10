package com.leadnews.comment.vo;

import com.leadnews.comment.document.Comment;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.vo
 */
@Data
public class CommentVo extends Comment {

    private Integer operation;//1:代表当前用户对它点赞了

    public static CommentVo build(Comment comment){
        CommentVo vo = new CommentVo();
        BeanUtils.copyProperties(comment,vo);
        return vo;
    }
}
