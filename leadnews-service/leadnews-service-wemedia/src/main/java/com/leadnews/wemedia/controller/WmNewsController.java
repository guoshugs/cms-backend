package com.leadnews.wemedia.controller;


import com.leadnews.common.vo.PageResultVo;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.dto.AuthReqDto;
import com.leadnews.wemedia.dto.DownOrUpDto;
import com.leadnews.wemedia.dto.WmNewsDtoSave;
import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.pojo.WmNews;
import com.leadnews.wemedia.service.WmNewsService;
import com.leadnews.wemedia.vo.WmNewsVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.leadnews.core.controller.AbstractCoreController;

import javax.validation.Valid;

/**
 * @description <p>自媒体图文内容信息</p>
 *
 * @version 1.0
 * @package com.leadnews.wemedia.controller
 */
@Api(value="WmNewsController",tags = "自媒体图文内容信息")
@RestController
@RequestMapping("/news")
public class WmNewsController extends AbstractCoreController<WmNews> {

    private WmNewsService wmNewsService;

    @Autowired
    public WmNewsController(WmNewsService wmNewsService) {
        super(wmNewsService);
        this.wmNewsService=wmNewsService;
    }

    /**day05
     * 发表文章提交
     * @param isDraft
     * @param dto
     * @return
     */
    @PostMapping("/submit")
    /* 另一个参数从载荷中来，可以不用加@PathVariable*/
    public ResultVo submit(@RequestBody WmNewsDtoSave dto,
                           boolean isDraft) {
        wmNewsService.submit(dto, isDraft);
        return ResultVo.ok("操作成功");
    }

    /**
     * 文章列表分页查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public PageResultVo findPage(@RequestBody WmNewsPageReqDto dto) {
        PageResultVo vo = wmNewsService.findPage(dto);
        return vo;
    }


    /**
     * 通过id查询文章信息
     * @param wmNewsId
     * @return
     */
    @GetMapping("/one/{wmNewsId}")
    public ResultVo getOne(@PathVariable(value = "wmNewsId") Long wmNewsId){
        WmNews news = wmNewsService.findOne(wmNewsId);
        return ResultVo.ok(news);
    }

    /**
     * 通过id删除
     * @param wmNewsId
     * @return
     */
    @GetMapping("/del_news/{wmNewsId}")
    public ResultVo removeMyWmNews(@PathVariable(value = "wmNewsId") Long wmNewsId){
        wmNewsService.removeMyWmNews(wmNewsId);
        return ResultVo.ok("操作成功!");
    }

    /**
     * 文章的上架与下架操作
     * @param dto
     * @return
     */
    @PostMapping("/down_or_up") //上下架的参数在载荷中，有2个参数，1个id，1个enable，两个参数就可以考虑实例化
    public ResultVo downOrUp(@RequestBody @Valid DownOrUpDto dto){
        wmNewsService.downOrUp(dto);
        return ResultVo.ok("操作成功!");
    }


    /*---------------------day07_自媒体文章审核-------------------------*/
    /**
     * 文章列表 分页查询 给后台管理人员，人工审核
     * @param dto
     * @return
     */
    @PostMapping("/list_vo")
    public PageResultVo<WmNewsVo> listWithAuthor(@RequestBody WmNewsPageReqDto dto) {
        PageResultVo pageResultVo = wmNewsService.listWithAuthor(dto);
        return pageResultVo;
    }




    @PostMapping("/auth_pass")
    public ResultVo authPass(@RequestBody @Validated AuthReqDto authReqDto){
        wmNewsService.authPass(authReqDto);
        return ResultVo.ok();
    }


    @PostMapping("/auth_fail")
    public ResultVo authFail(@RequestBody AuthReqDto authReqDto){
        wmNewsService.authFail(authReqDto);
        return ResultVo.ok();
    }

    @GetMapping("/one_vo/{wmNewsId}")
    public ResultVo<WmNewsVo> oneVo(@PathVariable Long wmNewsId) {
        WmNewsVo wmNewsVo = wmNewsService.oneVo(wmNewsId);
        return ResultVo.ok(wmNewsVo);
    }

}

