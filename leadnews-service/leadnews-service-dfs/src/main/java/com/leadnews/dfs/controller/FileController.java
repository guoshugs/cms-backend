package com.leadnews.dfs.controller;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.util.RequestContextUtil;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.feign.WmMaterialFeign;
import com.leadnews.wemedia.pojo.WmMaterial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.dfs.controller
 */
@RestController
@RequestMapping("dfs")
@Slf4j
public class FileController {

    @Resource
    private FastFileStorageClient client;

    @Resource
    private FdfsWebServer webServer; // 将来的请求地址，作用是用来拼接完整路径

    @Resource
    private WmMaterialFeign wmMaterialFeign;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    public ResultVo upload(MultipartFile multipartFile){ //multipartFile名字不能改，想改需要在前面加上@requestparams取别名
        //来源鉴权

        String origin = RequestContextUtil.getHeader(SC.USER_HEADER_FROM);
        if(StringUtils.isEmpty(origin)){ // 如果是空值，说明没有from键的值，说明请求没有经过自媒体网关，是不给处理的！如果其他微服务也要上传文件，也得加from键，并修改能通过的值
            // 目前代表 没经过自媒体网关
            throw new LeadNewsException("非法访问!");
        }
        //1. 接收文件
        try {
            // 上传上来的文件的输入流
            InputStream is = multipartFile.getInputStream();
            // 文件的大小
            long size = multipartFile.getSize();
            // 文件名的后缀名
            String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            //2. 传到fastDFS
            StorePath storePath = client.uploadFile(is, size, ext, null);
            String fileId = storePath.getFullPath();
            String fullPath = webServer.getWebServerUrl() + fileId;
            //3. 返回文件路径 {url:fullPath}
            if(SC.WEMEDIA_PIC.equals(origin)) { // 这里才是判断from键的值。
                // 请求的来源是从自媒体上传素材过来的(网关已经配置好了)
                // 远程调用自媒体微服，添加素材表记录
                addWmMaterial(fileId);
            }
            // singletonMap: 只能存入一个key
            Map<String, String> map = Collections.singletonMap("url", fullPath);
            return ResultVo.ok(map);
        } catch (IOException e) {
            log.error("上传文件失败!", e);
            throw new LeadNewsException("上传文件失败!");
        }
    }

    private void addWmMaterial(String picUrl) {
        WmMaterial wmMaterial = new WmMaterial();
        // 属性赋值
        wmMaterial.setUserId(RequestContextUtil.getUserId());
        wmMaterial.setUrl(picUrl);
        wmMaterial.setCreatedTime(LocalDateTime.now());
        wmMaterial.setType(0);// 类型为图片，可以在常数中添加BC.ArticleConstantsPic
        wmMaterial.setIsCollection(0); // 非收藏

        ResultVo addResult = wmMaterialFeign.addWmMaterial(wmMaterial);
        if(!addResult.isSuccess()){
            log.error("上传文件调用自媒体微服：添加素材失败：{}",addResult.getErrorMessage());
            throw new LeadNewsException("上传文件添加素材失败!");
        }
    }



    /**
     * 批量下载  【没有使用设计模式的实现】
     *
     * @param urls
     * @return
     */
    @PostMapping("/download")
    public ResultVo<List<byte[]>> download(@RequestBody Collection<String> urls){
        List<byte[]> list = new ArrayList<>();
        // 下载
        if(!CollectionUtils.isEmpty(urls)){
            DownloadByteArray dba = new DownloadByteArray();
        /*list = fullPath.stream().map(fileId -> {
            // 解析文件id, 获取分组及文件目录
            StorePath storePath = StorePath.parseFromUrl(fileId);
            return client.downloadFile(storePath.getGroup(), storePath.getPath(),dba);
        }).collect(Collectors.toList());*/
            for (String url : urls) {
                // 解析url地址，获取group, path
                StorePath storePath = StorePath.parseFromUrl(url);
                byte[] bytes = client.downloadFile(storePath.getGroup(), storePath.getPath(), dba);
                list.add(bytes);
            }
        }
        return ResultVo.ok(list);
    }

}
