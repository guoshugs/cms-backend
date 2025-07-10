package com.leadnews.comment.service;

import com.leadnews.comment.dto.CommentDto;
import com.leadnews.comment.dto.CommentLikeDto;
import com.leadnews.comment.dto.CommentSaveDto;
import com.leadnews.comment.vo.CommentVo;

import java.util.List;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.service
 */
public interface CommentService {
    /**
     * 发表评论
     * @param dto
     */
    void add(CommentSaveDto dto);

    /**
     * 进入文章详情时 评论列表
     * @param dto
     * @return
     */
    List<CommentVo> loadByPage(CommentDto dto);

    /**
     * 评论 点赞或取消点赞
     * @param commentLikeDto
     */
    void like(CommentLikeDto commentLikeDto);
}
