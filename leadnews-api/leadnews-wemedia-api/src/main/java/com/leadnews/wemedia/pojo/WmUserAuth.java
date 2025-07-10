package com.leadnews.wemedia.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description <p>自媒体子账号权限信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_user_auth")
@ApiModel(value="WmUserAuth", description="自媒体子账号权限信息")
public class WmUserAuth implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "账号ID", dataType="Long")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(notes = "资源类型 0:菜单 1:频道 2:按钮", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "资源名称", dataType="String")
    @TableField("name")
    private String name;


}
