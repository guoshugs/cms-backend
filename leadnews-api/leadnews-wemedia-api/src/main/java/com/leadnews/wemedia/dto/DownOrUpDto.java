package com.leadnews.wemedia.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.wemedia.dto
 */
@Data
public class DownOrUpDto {
    /**
     * 文章id
     */
    private Long id;
    /**
     * 0:下架
     * 1:上架
     */
    //@Pattern(regexp = "^[0,1]$", message = "参数不正确!")
    @Min(value = 0, message = "参数不正确")
    @Max(value = 1, message = "参数不正确")
    private Integer enable;

    public static void main(String[] args) {
        java.util.regex.Pattern compile = java.util.regex.Pattern.compile("^[0,1]$");
        System.out.println(compile.matcher("2").find());
    }
}
