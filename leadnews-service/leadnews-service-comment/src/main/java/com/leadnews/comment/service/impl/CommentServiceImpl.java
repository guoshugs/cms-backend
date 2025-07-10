package com.leadnews.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.leadnews.behaviour.dto.UpdateArticleMess;
import com.leadnews.comment.document.Comment;
import com.leadnews.comment.document.CommentLike;
import com.leadnews.comment.dto.CommentDto;
import com.leadnews.comment.dto.CommentLikeDto;
import com.leadnews.comment.dto.CommentSaveDto;
import com.leadnews.comment.repository.CommentRepository;
import com.leadnews.comment.service.CommentService;
import com.leadnews.comment.vo.CommentVo;
import com.leadnews.common.constants.BC;
import com.leadnews.common.constants.SC;
import com.leadnews.common.enums.HttpCodeEnum;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
public class CommentServiceImpl implements CommentService {
    /* spring集成mongoDB，搭建的微服务可以用repository来操作，也可以用mongoTemplate来操作。*/
    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private KafkaTemplate kafkaTemplate;

    /**
     * 发表评论
     *
     * @param dto
     */
    @Override
    public void add(CommentSaveDto dto) {
        // 是否为匿名账号
        if(RequestContextUtil.getUserId() == SC.ANONYMOUS_USER_ID){
            throw new LeadNewsException(HttpCodeEnum.NEED_LOGIN);
        }
        Comment doc = new Comment(); // commentRepository就是操作comment的工具，所以需要使用comment对象
        doc.setHeadImage(RequestContextUtil.getHeader(SC.LOGIN_USER_IMAGE));
        doc.setNickName(RequestContextUtil.getHeader(SC.LOGIN_USER_NAME));
        doc.setUserId(RequestContextUtil.getUserId());
        doc.setReplies(0);
        doc.setLikes(0);
        doc.setContent(dto.getContent());
        doc.setArticleId(dto.getArticleId());
        doc.setUpdatedTime(LocalDateTime.now());
        doc.setCreatedTime(doc.getUpdatedTime());
        commentRepository.save(doc);

        /*---------------------day12热点文章流数据------------------------------*/
        // 收藏行为，要发消息给kafka流处理器，由流处理器进行聚合统计，再发消息给消费者进行更新，以及重算推荐分数
        UpdateArticleMess updateArticleMess = new UpdateArticleMess();
        updateArticleMess.setArticleId(dto.getArticleId());
        updateArticleMess.setType(UpdateArticleMess.UpdateArticleType.COMMENT);

        updateArticleMess.setNum(1);

        kafkaTemplate.send(BC.MqConstants.HOT_ARTICLE_SCORE_TOPIC, JSON.toJSONString(updateArticleMess));
    }

    /**
     * 评论 点赞或取消点赞
     *
     * @param dto
     */
    @Override
    public void like(CommentLikeDto dto) {
        //1. 判断是否为匿名
        if(RequestContextUtil.isAnonymous()){
            throw new LeadNewsException(HttpCodeEnum.NEED_LOGIN);
        }
        int loginUserId = RequestContextUtil.getUserId().intValue();
        //2. 查询点赞记录是否存在
        Query query = new Query(Criteria.where("userId").is(loginUserId)
                .and("commentId").is(dto.getCommentId()));
        CommentLike commentLike = mongoTemplate.findOne(query, CommentLike.class);
        int updateCount = 0;
        //3. 判断点赞类型
        if(dto.getOperation() == 0){
            //3.1 点赞
            // 如果存在则报错，说明点赞过了，不能重复点赞
            if(null != commentLike){
                throw new LeadNewsException("已经点过赞了!");
            }
            // 否则，添加点赞记录
            commentLike = new CommentLike();
            commentLike.setCommentId(dto.getCommentId());
            commentLike.setUserId(loginUserId);
            mongoTemplate.insert(commentLike);
            // 点赞数要+1
            updateCount = 1;
        }else{
            //3.2 取消点赞
            if(null == commentLike){
                // 如果不存在记录则报错
                throw new LeadNewsException("您还没点赞!");
            }
            // 存在则删除点赞记录
            mongoTemplate.remove(query, CommentLike.class);
            // 点赞数要-1
            updateCount = -1;
        }
        //4 更新评论中的赞数量
        Query updateQuery = new Query(Criteria.where("id").is(dto.getCommentId()));
        Update update = new Update();
        update.inc("likes", updateCount);
        mongoTemplate.findAndModify(updateQuery, update, Comment.class);
    }

    /**
     * 进入文章详情时 评论列表
     * 前端传来的请求参数出了问题。不是minLikes，是minDate。因为评论会有很多也需要滚动分页查询。
     * @param dto
     * @return
     */
    //@Override
    public List<CommentVo> loadByPage2(CommentDto dto) {
        // 前端有bug，传的时间200000。因此为了让程序跑下去，暂时设置为当前系统时间
        //1. 构建查询条件
        Query query = Query.query(Criteria.where("createdTime").lt(LocalDateTime.now()));
        query.addCriteria(Criteria.where("articleId").is(dto.getArticleId()));
        //按时间降序排序
        query.with(Sort.by(Sort.Order.desc("createdTime")));
        query.limit(10);
        //2. 查询
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        List<CommentVo> voList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(comments)) {
            voList = comments.stream().map(CommentVo::build).collect(Collectors.toList());
        }
        //3. 转成vo

        return voList;
    }


    /** 评论列表每次请求都要传递过来minDate，记录每次翻页的最小时间，意味着下一页要比它小
     * 但前端传过来的是minLikes，似乎是不对的，这里对minDate多加了判断
     * 文章评论列表分页查询
     *
     * @param commentDto
     * @return
     */
    @Override
    public List<CommentVo> loadByPage(CommentDto commentDto) {
        // 时间防空处理
        if (commentDto.getMinDate() == null) {
            commentDto.setMinDate(new Date());
        }
        List<CommentVo> voList = new ArrayList<>();
        //1. 构建查询条件
        Query query = new Query(Criteria.where("articleId").is(commentDto.getArticleId())
                .and("createdTime").lt(commentDto.getMinDate()));
        //2. 每页查询10条记录
        query.limit(10);
        //3 设置排序
        query.with(Sort.by(Sort.Order.desc("createdTime")));
        //4. 查询
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        if(!CollectionUtils.isEmpty(comments)) {
            //5. 转成vo
            voList = comments.stream().map(CommentVo::build).collect(Collectors.toList());
            //5. 判断是否为匿名，如果是匿名则直接返回结果
            if (!RequestContextUtil.isAnonymous()) {
                //6. 如果是登录用户，则还要查询当前用户是否对查询结果的评论点过赞了
                if (!CollectionUtils.isEmpty(comments)) {
                    //7. 取出所有评论的id
                    List<String> commentIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
                    int loginUserId = RequestContextUtil.getUserId().intValue();
                    //8. 构建查询条件查询评论点赞表, 条件为userId=登录用户id, commentId in (所有的评论id)
                    Query commentLikeQuery = new Query(Criteria.where("userId").is(loginUserId).and("commentId").in(commentIds));
                    // 查询所有点赞的评论记录
                    List<CommentLike> commentLikes = mongoTemplate.find(commentLikeQuery, CommentLike.class);
                    //9. 如果有结果则标记点赞
                    if (!CollectionUtils.isEmpty(commentLikes)) {
                        // 遍历点赞结果，如果有(即评论id相同时，则设置点赞标记为1)
                        for (CommentVo commentVo : voList) {
                            for (CommentLike commentLike : commentLikes) {
                                if (commentVo.getId().equals(commentLike.getCommentId())) {
                                    commentVo.setOperation(1);//点赞了
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
