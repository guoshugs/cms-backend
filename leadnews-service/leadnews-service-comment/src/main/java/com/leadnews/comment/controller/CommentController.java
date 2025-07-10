package com.leadnews.comment.controller;

import com.leadnews.comment.dto.CommentDto;
import com.leadnews.comment.dto.CommentLikeDto;
import com.leadnews.comment.dto.CommentSaveDto;
import com.leadnews.comment.service.CommentService;
import com.leadnews.comment.vo.CommentVo;
import com.leadnews.common.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.controller
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 发表评论
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResultVo addComment(@RequestBody CommentSaveDto dto){
        commentService.add(dto);
        return ResultVo.ok("操作成功!");
    }

    /**
     * 进入文章详情时 评论列表
     * @param dto
     * @return
     */
    @PostMapping("/load")
    public ResultVo<List<CommentVo>> loadByPage(@RequestBody CommentDto dto){
        List<CommentVo> voList = commentService.loadByPage(dto);
        return ResultVo.ok(voList);
    }

    /**
     * 评论 点赞或取消点赞
     * @param commentLikeDto
     * @return
     */
    @PostMapping("/like")
    public ResultVo like(@RequestBody CommentLikeDto commentLikeDto){
        commentService.like(commentLikeDto);
        return ResultVo.ok();
    }
}
