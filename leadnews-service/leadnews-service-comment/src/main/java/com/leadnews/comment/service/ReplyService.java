package com.leadnews.comment.service;

import com.leadnews.comment.dto.CommentRelyDto;
import com.leadnews.comment.dto.CommentReplyLikeDto;
import com.leadnews.comment.dto.CommentReplySaveDto;
import com.leadnews.comment.vo.ReplyVo;

import java.util.List;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.service
 */
public interface ReplyService {
    /**
     * 发表回复
     * @param commentReplySaveDto
     */
    void saveReply(CommentReplySaveDto commentReplySaveDto);

    /**
     * 点赞回复
     * @param commentReplyLikeDto
     */
    void like(CommentReplyLikeDto commentReplyLikeDto);

    /**
     * 查询回复列表
     * @param commentRelyDto
     * @return
     */
    List<ReplyVo> loadPage(CommentRelyDto commentRelyDto);

}
