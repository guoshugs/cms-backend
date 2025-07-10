package com.leadnews.comment.controller;

import com.leadnews.comment.dto.CommentRelyDto;
import com.leadnews.comment.dto.CommentReplyLikeDto;
import com.leadnews.comment.dto.CommentReplySaveDto;
import com.leadnews.comment.service.ReplyService;
import com.leadnews.comment.vo.ReplyVo;
import com.leadnews.common.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews.comment.controller
 */
@RestController
@RequestMapping("/comment_repay")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 发表回复
     * @param commentReplySaveDto
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public ResultVo saveReply(@RequestBody CommentReplySaveDto commentReplySaveDto) {
        replyService.saveReply(commentReplySaveDto);
        return ResultVo.ok();
    }

    /**
     * 点赞回复
     * @param commentReplyLikeDto
     * @return
     */
    @PostMapping("/like")
    public ResultVo like(@RequestBody CommentReplyLikeDto commentReplyLikeDto){
        replyService.like(commentReplyLikeDto);
        return ResultVo.ok();
    }

    /**
     * 查询回复列表
     * @param commentRelyDto
     * @return
     */
    @PostMapping("/load")
    public ResultVo<List<ReplyVo>> loadPage(@RequestBody CommentRelyDto commentRelyDto){
        List<ReplyVo> commentReplyVoList = replyService.loadPage(commentRelyDto);
        return ResultVo.ok(commentReplyVoList);
    }
}