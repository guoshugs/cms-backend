package com.leadnews.admin.controller;

import com.leadnews.admin.dto.ChannelPageRequestDto;
import com.leadnews.admin.pojo.AdChannel;
import com.leadnews.admin.service.AdChannelService;
import com.leadnews.common.vo.PageResultVo;
import com.leadnews.common.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channel")
@Api(value="AdChannelController",tags = "频道信息")
@AllArgsConstructor // 下面把该类的构造方法给写全了，就不用加注解了
public class AdChannelController {

    private AdChannelService adChannelService; // 只给定全参构造器@AllArgsConstructor会自动用构造方法实现注入

/*    @Autowired
    public AdChannelController(AdChannelService adChannelService) {
        super(adChannelService);
        this.adChannelService=adChannelService;
    }*/

    /**
     * 频道列表
     * 前端请求类型content-type是json，一定传来了一个对象，需要有人接收，就用dto！
     * @param dto
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("频道列表")
    public PageResultVo list(@RequestBody ChannelPageRequestDto dto) {
        PageResultVo vo = adChannelService.findPage(dto);
        return vo;
    }

    /**
     * 添加频道
     * @param adChannel
     * @return
     */
    @PostMapping("save")
    @ApiOperation("新增频道")
    public ResultVo add(@RequestBody @Validated AdChannel adChannel){
        adChannelService.add(adChannel);
        return ResultVo.ok("操作成功!");
    }


    /**
     * 在做编辑修改时，为了防止并发concurrent操作数据的发生，需要在修改请求发出时，第一时间去查一下该数据
     * 这是数据隔离级别的问题。先查询一下，能保证在修改的当时，数据是准确的
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("通过id查询频道信息")
    public ResultVo getById(@PathVariable(value = "id") Integer id){
        return ResultVo.ok(adChannelService.getById(id));
    }

    /**
     * 更新频道
     * @param adChannel
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("更新频道")
    public ResultVo update(@RequestBody AdChannel adChannel){
        adChannelService.updateById(adChannel);
        return ResultVo.ok("操作成功!");
    }

    /**
     * 通过id删除频道
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    @ApiOperation("通过id删除")
    public ResultVo deleteById(@PathVariable(value = "id") Integer id){
        adChannelService.removeById(id);
        return ResultVo.ok("删除成功!");
    }

}
