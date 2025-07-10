package com.leadnews.behaviour.dto;

import lombok.Data;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.behaviour.dto
 */
@Data
public class FollowBehaviourDto {
    //文章id
    private Long articleId;
    //被关注者 用户ID
    private Long followId;
    //关注者  用户id
    private Long userId;
    //设备ID
    private Integer equipmentId;
}
