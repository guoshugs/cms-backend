package com.leadnews.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @version 1.0
 * @description 频道实体类
 * @package com.leadnews.admin.pojo
 */
@Data
@TableName("ad_channel")
@ApiModel(value = "AdChannel", description = "频道")
public class AdChannel {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(notes = "频道id，主键自增", dataType = "Integer")
    private Integer id;

    @TableField("name")
    @ApiModelProperty(notes = "名称", dataType = "String")
    @Size(min = 2, max = 10, message = "名称的长度必须在2-10之间")
    // 参数校验规则: javax.validation.constraints
    private String name;

    @TableField("description")
    @ApiModelProperty(notes = "描述")
    @NotBlank(message = "描述不能为空")
    /* 如果要使用后台校验，加上@validated的注解用validation就可以搞定
    * 能够这样实现，是因为Nacos的全局异常处理生效了。
    * 如果启动类所在的包，不包含全局异常处理，是不会有提示的
    * 因为在全局异常处理里面处理了方法参数不合法的异常，捕获了
    * MethodArgumentNotValidException.class的异常
    * 经过参数校验的时候，如果不符合他的规则，它就会抛出上面的异常
    * 全局处理就会获取异常的信息。并且把异常信息拼接起来*/
    private String description;

    @ApiModelProperty(notes = "是否默认频道", dataType="Boolean")
    @TableField("is_default")
    private Boolean isDefault;

    /* 数据库中会把Boolean值变成int，但是我也不知道在模型中除了此处，还有哪里有Boolean类型的status，导致如果这里写了Integer，前后端联调的时候会报错
    * Cannot deserialize instance of `java.lang.Integer` out of VALUE_FALSE token。
    * 另外的解决办法是加一个*/
    @ApiModelProperty(notes = "频道的状态，是否可用", dataType = "Boolean")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty(notes = "默认排序", dataType="Integer")
    @TableField("ord")
    private Integer ord;

    @ApiModelProperty(notes = "创建时间", dataType="LocalDateTime")
    @TableField("created_time")
    private LocalDateTime createdTime;
}