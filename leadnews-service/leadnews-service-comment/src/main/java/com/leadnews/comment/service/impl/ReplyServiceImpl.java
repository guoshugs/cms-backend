package com.leadnews.comment.service.impl;

import com.leadnews.comment.document.Comment;
import com.leadnews.comment.document.Reply;
import com.leadnews.comment.document.ReplyLike;
import com.leadnews.comment.dto.CommentRelyDto;
import com.leadnews.comment.dto.CommentReplyLikeDto;
import com.leadnews.comment.dto.CommentReplySaveDto;
import com.leadnews.comment.service.ReplyService;
import com.leadnews.comment.vo.ReplyVo;
import com.leadnews.common.constants.SC;
import com.leadnews.common.enums.HttpCodeEnum;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.comment.service.impl
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 发表回复
     *
     * @param dto
     */
    @Override
    public void saveReply(CommentReplySaveDto dto) {
        //获取当前用户的信息 判断是否匿名用户 如果是匿名用户 返回错误
        if (RequestContextUtil.isAnonymous()) {
            throw new LeadNewsException(HttpCodeEnum.NEED_LOGIN);
        }

        Reply entity = new Reply();
        entity.setCommentId(dto.getCommentId());
        entity.setContent(dto.getContent());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setHeadImage(RequestContextUtil.getHeader(SC.LOGIN_USER_IMAGE));
        entity.setLikes(0);
        entity.setNickName(RequestContextUtil.getHeader(SC.LOGIN_USER_NAME));
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setUserId(RequestContextUtil.getUserId().intValue());
        mongoTemplate.insert(entity);

        //找到这个评论 将回复数量+1  update xxx set replays=replays+1 where id=?
        Query query = Query.query(Criteria.where("_id").is(dto.getCommentId()));
        Update update = new Update();
        update.inc("replys");
        mongoTemplate.findAndModify(query, update, Comment.class);
    }

    /**
     * 点赞回复
     *
     * @param dto
     */
    @Override
    public void like(CommentReplyLikeDto dto) {
        //1. 判断是否为匿名
        if (RequestContextUtil.isAnonymous()) {
            throw new LeadNewsException(HttpCodeEnum.NEED_LOGIN);
        }
        int loginUserId = RequestContextUtil.getUserId().intValue();
        //2. 查询点赞记录是否存在
        Query query = new Query(Criteria.where("userId").is(loginUserId)
            .and("replyId").is(dto.getCommentRepayId()));
        ReplyLike replyLike = mongoTemplate.findOne(query, ReplyLike.class);
        int updateCount = 0;
        //3. 判断点赞类型 前端传来的指令0是点赞
        if (dto.getOperation() == 0) {
            //3.1 点赞
            // 如果存在则报错，说明点赞过了，不能重复点赞
            if (null != replyLike) {
                throw new LeadNewsException("已经点过赞了!");
            }
            // 否则，添加点赞记录
            replyLike = new ReplyLike();
            replyLike.setReplyId(dto.getCommentRepayId());
            replyLike.setUserId(loginUserId);
            mongoTemplate.insert(replyLike);
            // 点赞数要+1
            updateCount = 1;
        } else {
            //3.2 取消点赞
            if (null == replyLike) {
                // 如果不存在记录则报错
                throw new LeadNewsException("您还没点赞!");
            }
            // 存在则删除点赞记录
            mongoTemplate.remove(query, ReplyLike.class);
            // 点赞数要-1
            updateCount = -1;
        }
        //4 更新评论中的赞数量
        Query updateQuery = new Query(Criteria.where("id").is(dto.getCommentRepayId()));
        Update update = new Update();
        update.inc("likes", updateCount);
        mongoTemplate.findAndModify(updateQuery, update, Reply.class);
    }

    /**
     * 查询回复列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<ReplyVo> loadPage(CommentRelyDto dto) {
        // 时间防空处理
        if (dto.getMinDate() == null) {
            dto.setMinDate(new Date());
        }
        List<ReplyVo> voList = new ArrayList<>();
        //1. 构建查询条件
        Query query = new Query(Criteria.where("commentId").is(dto.getCommentId())
            .and("createdTime").lt(dto.getMinDate()));
        //2. 每页查询10条记录
        query.limit(10);
        //3 设置排序
        query.with(Sort.by(Sort.Order.desc("createdTime")));
        //4. 查询
        List<Reply> replies = mongoTemplate.find(query, Reply.class);
        //5. 转成vo
        if(!CollectionUtils.isEmpty(replies)) {
            voList = replies.stream().map(ReplyVo::build).collect(Collectors.toList());
            //5. 判断是否为匿名，如果是匿名则直接返回结果
            if (!RequestContextUtil.isAnonymous()) {
                //6. 如果是登录用户，则还要查询当前用户是否对查询结果的评论点过赞了
                if (!CollectionUtils.isEmpty(replies)) {
                    //7. 取出所有评论的id
                    List<String> replyIds = replies.stream().map(Reply::getId).collect(Collectors.toList());
                    int loginUserId = RequestContextUtil.getUserId().intValue();
                    //8. 构建查询条件查询评论点赞表, 条件为userId=登录用户id, commentId in (所有的评论id)
                    Query replyLikeQuery = new Query(Criteria.where("userId").is(loginUserId).and("replyId").in(replyIds));
                    // 查询所有点赞的评论记录
                    List<ReplyLike> replyLikes = mongoTemplate.find(replyLikeQuery, ReplyLike.class);
                    //9. 如果有结果则标记点赞
                    if (!CollectionUtils.isEmpty(replyLikes)) {
                        // 遍历点赞结果，如果有(即评论id相同时，则设置点赞标记为1)
                        for (ReplyVo replyVo : voList) {
                            for (ReplyLike replyLike : replyLikes) {
                                if (replyVo.getId().equals(replyLike.getReplyId())) {
                                    replyVo.setOperation(1);//点赞了
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        //10. 返回voList
        return voList;
    }
}