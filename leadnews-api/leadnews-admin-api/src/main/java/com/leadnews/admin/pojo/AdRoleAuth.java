package com.leadnews.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description <p>角色权限信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.admin.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_role_auth")
@ApiModel(value="AdRoleAuth", description="角色权限信息")
public class AdRoleAuth implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "角色ID", dataType="Integer")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(notes = "资源类型 0:菜单 1:功能", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "资源ID", dataType="Integer")
    @TableField("entry_id")
    private Integer entryId;

    @ApiModelProperty(notes = "登录时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;


}
