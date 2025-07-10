package com.leadnews.wemedia.controller;


import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.pojo.WmMaterial;
import com.leadnews.wemedia.pojo.WmNews;
import com.leadnews.wemedia.pojo.WmUser;
import com.leadnews.wemedia.service.ApiService;
import com.leadnews.wemedia.service.WmMaterialService;
import com.leadnews.wemedia.service.WmNewsService;
import com.leadnews.wemedia.service.WmUserService;
import com.leadnews.wemedia.vo.WmNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自媒体微服务提供给内部的所有远程调用的接口都在这。
 * 这里就不写@requestMapping了。所有微服务内部过来的请求路径都不一样
 */
@RestController
public class ApiController {
    /* 这里的API是向本工程外的其他系统user提供服务的，是内联，本身虽然操作的是WmUser，
    * 但是接口不能放在WmUserController中，因为WmUserController也有自己的业务场景。
    * 所以这里的请求是user发出的，提供给内部的user的，因此提供的服务
    * getByUserId，addWmUser是不能耦合在自己原本的工程Controller的*/

    @Autowired
    private WmUserService wmUserService;

    @Resource
    private WmMaterialService wmMaterialService;

    @Resource
    private ApiService apiService;

    @Resource
    private WmNewsService wmNewsService;

    /**
     * 通过ap_user.id查询自媒体人信息
     * @param userId
     * @return
     */
    @GetMapping("/api/wmUser/getByUserId/{userId}")
    public ResultVo<WmUser> getByUserId(@PathVariable(value = "userId") Long userId){
        WmUser wmUser = wmUserService.getByUserId(userId);
        return ResultVo.ok(wmUser);
    }

    /**
     * 添加自媒体人账号
     * @param wmUser
     * @return
     */
    @PostMapping("/api/wmUser/addWmUser")
    public ResultVo<WmUser> addWmUser(@RequestBody WmUser wmUser){
        // wmUser中的id有值吗? 没有
        wmUserService.save(wmUser);
        // wmUser中的id有值吗? 有 主键自动填充
        return ResultVo.ok(wmUser);
    }

    @GetMapping("getUserId")
/*    public Long getUserId(){
       return RequestContextUtil.getUserId();
    }
    上面是使用工具类的方法获得放在Header中的id。
    如果不使用工具类，而是用注解@RequestHeader，也能获得header中的id
    两种方法用哪一个都行
    但是用工具类的好处是，方法是static的，就存放在那里了。*/
    public Long getUserId(@RequestHeader(value = "userId") Long userId){
        System.out.println("id=" + userId); // 这里就能打印出id了。
        // 加上注解就直接能从请求头中获得用户id了；或者用工具类的requestcontextUtil方法也能获得用户id
        // 比@RequestHeader灵活
        Long loginUserId = RequestContextUtil.getUserId();
        System.out.println("loginUserId=" + loginUserId);
        return loginUserId;
    }

    /**
     * 远程调用添加素材记录
     * @param wmMaterial
     * @return
     */
    @PostMapping("/api/wmMaterial/add")
    public ResultVo addWmMaterial(@RequestBody WmMaterial wmMaterial){
        wmMaterialService.save(wmMaterial);
        return ResultVo.ok("操作成功!");
    }
    /*============================DAY05==============================================*/
    /** 这个api和其他api不一样，其他是提供别人远程调用，它是远程调用别人
     * 因它调用了admin，就要引入admin的api工程，不然用不了。
     * 查询频道列表
     * @return
     */
    @GetMapping("/channel/channels") // 注意前端转给自媒体微服务的请求路径
    public ResultVo<List<AdChannel>> listChannels(){
        List<AdChannel> list = apiService.listChannels(); // wemedia自己的业务中没有操作这个业务的service，需要自己创建一个。
        return ResultVo.ok(list);
    }


    /*--------------------day07_自媒体文章审核-------------------------------*/

    /**
     * 通过id查询文章信息
     * @return
     */
    @GetMapping("/api/wmNews/getWmNewsById/{wmNewsId}")
    public ResultVo<WmNews> getWmNewsById(@PathVariable(name = "wmNewsId") Long wmNewsId){
        WmNews wmNews = wmNewsService.getById(wmNewsId);
        return ResultVo.ok(wmNews);
    }

    /**
     * 通过id更新文章信息
     * @return
     */
    @PutMapping("/api/wmNews/update")
    public ResultVo updateWmNews(@RequestBody WmNews wmNews){
        if(wmNews == null) {
            throw new LeadNewsException("!!!!!!!!!!!!!!!!自动检测之后的远程调用wemedia更新wmnews，可是传输对象为空");
        };
        System.out.println(wmNews.toString());
        wmNewsService.updateById(wmNews);
        return ResultVo.ok("更新成功");
    }


    /*---------------------day08_分布式任务与文章信息同步-----------------------*/

    /**
     * 查询需要同步到文章微服的新闻文章
     * @param dto
     * @return
     */
    @PostMapping("/api/wmNews/list4ArticleSync")
    public ResultVo<List<WmNewsVo>> list4ArticleSync(@RequestBody WmNewsPageReqDto dto){
        List<WmNewsVo> voList = wmNewsService.list4ArticleSync(dto);
        return ResultVo.ok(voList);
    }

}
