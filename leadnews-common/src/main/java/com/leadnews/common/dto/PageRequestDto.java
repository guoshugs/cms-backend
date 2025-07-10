package com.leadnews.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
@ApiModel(value = "PageRequestDto", description = "通用分页查询参数")
public class PageRequestDto implements Serializable {
    /* 凡是要在网络上传输的对象、要写入文件的对象、要保存到数据库中的对象都要进行序列化。
    Java对象是无法直接保存到文件中，或是存入数据库中的。
    如果要保存到文件中，或是存入数据库中，就要将对象序列化，即转换为字节数组才能保存到文件中或是数据库中。
    文件或者数据库中的字节数组拿出来之后要转换为对象才能被我们识别，即反序列化。*/

    @ApiModelProperty(notes = "每页大小", dataType = "Long")
    private Long size;
    @ApiModelProperty(notes = "页码", dataType = "Long")
    private Long page;

    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1l);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10l);
        }
    }
}
