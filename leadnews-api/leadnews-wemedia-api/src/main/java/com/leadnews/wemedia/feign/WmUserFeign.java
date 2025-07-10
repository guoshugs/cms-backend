package com.leadnews.wemedia.feign;

import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.pojo.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "leadnews-wemedia", contextId = "wmUserFeign")
//contextId这里容器id最好加上（接口类名的首字母小写），
// 不然就以服务提供者的名称"leadnews-wemedia"作为bean对象了
public interface WmUserFeign {

    @GetMapping("/api/wmUser/getByUserId/{userId}")
    ResultVo<WmUser> getByUserId(@PathVariable(value = "userId") Long userId);
    /* 这里之所以用ResultVo包装后进行返回，而不是直接用WmUser返回，是因为之前设置了全局异常处理
    * 一旦除了异常，异常处理器返回是ResultVo。类型如果与ResultVo不同，就会报错。
    * 使用异常处理器可以区分业务异常和系统异常。
    * 因此实际业务中，远程调用的结果必须用包装类封装上。
    * 就可以判断如果结果不符合预期抛出的异常是哪种类型
    * 这里ResultVo的泛型也得写上，不写的话，将来得到的是LinkedHashMap
    * 底层原理：
    * 远程调用的序列化与反序列化*/


    @PostMapping("/api/wmUser/addWmUser")
    ResultVo<WmUser> addWmUser(@RequestBody WmUser wmUser);
    /* user微服务远程调用wemedia去添加一个用户，那么user就得在请求中传递过去（准Wmuser对象，其实是Json数据）。
    * wemedia微服务用requestBody注解，接收调用方发送过来的json数据，转换成WmUser对象
    * 这个对象给了谁呢？给了Wemedia的对应controller */

}
