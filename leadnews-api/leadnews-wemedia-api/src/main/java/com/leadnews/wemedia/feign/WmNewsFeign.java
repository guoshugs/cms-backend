package com.leadnews.wemedia.feign;

import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.pojo.WmNews;
import com.leadnews.wemedia.vo.WmNewsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "leadnews-wemedia", contextId = "wmNewsFeign")
public interface WmNewsFeign {

    /*--------------------day07_自媒体文章审核-------------------------------*/

    /**
     * 通过id查询文章信息
     * @return
     */
    @GetMapping("/api/wmNews/getWmNewsById/{wmNewsId}")
    ResultVo<WmNews> getWmNewsById(@PathVariable(name = "wmNewsId") Long wmNewsId);

    /**
     * 通过id更新文章信息
     * @return
     */
    @PutMapping("/api/wmNews/update")
    ResultVo updateWmNews(@RequestBody WmNews wmNews);




    /*--------------------day08_分布式任务与文章信息同步-------------------------------*/
    /* 同步审核通过之后的文章列表进文章微服务系统，总共需要2步：
    1、admin远程调用wm系统获取审核通过后的列表；2、admin拿着该列表远程调用文章微服务去做保存*/


    /**
     * 查询审核通过的文章，状态为8。进行数据同步到文章微服务
     * @param
     * @return
     */
    @PostMapping("/api/wmNews/list4ArticleSync")
    ResultVo<List<WmNewsVo>> list4ArticleSync(@RequestBody WmNewsPageReqDto dto);
}
