package com.leadnews.dfs.feign;

import com.leadnews.common.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "leadnews-dfs", contextId = "dfsFeign")
public interface DfsFeign {

    @PostMapping("/dfs/download")
    ResultVo<List<byte[]>> download(@RequestBody Collection<String> urls);
}
