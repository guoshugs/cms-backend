package com.leadnews.comment.vo;

import com.leadnews.comment.document.Reply;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews.comment.vo
 */
@Data
public class ReplyVo extends Reply {
    //标记给前端显示 是否点赞
    private Integer operation;
    public static ReplyVo build(Reply reply){
        ReplyVo vo = new ReplyVo();
        BeanUtils.copyProperties(reply,vo);
        return vo;
    }
}