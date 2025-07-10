package com.leadnews.article.feign;

import com.leadnews.article.pojo.ApAuthor;
import com.leadnews.common.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "leadnews-article", contextId = "apAuthorFeign")// 将来启动后容器内bean对象的名称
public interface ApAuthorFeign {

    /**
     * 通过用户id与自媒体人id查询作者信息
     * @param apUserId
     * @param wmUserId
     * @return
     */
    @GetMapping("/api/apAuthor/getByApUserIdWmUserId/{apUserId}/{wmUserId}")
    ResultVo<ApAuthor> getByApUserIdWmUserId(@PathVariable(value = "apUserId") Long apUserId,
                                             @PathVariable(value = "wmUserId") Long wmUserId);


    /**
     * 添加作者信息
     * @param apAuthor
     * @return
     */
    @PostMapping("/api/apAuthor/addApAuthor")
    ResultVo addApAuthor(@RequestBody ApAuthor apAuthor);
    // 这时不写泛型了，只需要成功失败就可以了，不需要得到它的数据
}
