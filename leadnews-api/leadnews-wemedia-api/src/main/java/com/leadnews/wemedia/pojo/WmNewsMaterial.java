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
 * @description <p>自媒体图文引用素材信息 </p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.pojo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wm_news_material")
@ApiModel(value="WmNewsMaterial", description="自媒体图文引用素材信息")
public class WmNewsMaterial implements Serializable {


    @ApiModelProperty(notes = "主键", dataType="Long")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "素材ID", dataType="Long")
    @TableField("material_id")
    private Long materialId;

    @ApiModelProperty(notes = "图文ID", dataType="Long")
    @TableField("news_id")
    private Long newsId;

    @ApiModelProperty(notes = "引用类型 0:内容引用 1:主图引用", dataType="Integer")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(notes = "引用排序", dataType="Integer")
    @TableField("ord")
    private Integer ord;


}
