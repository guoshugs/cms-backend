package com.leadnews.admin.task;

import com.leadnews.admin.service.WemediaNewsAutoScanService;
import com.leadnews.wemedia.dto.WmNewsPageReqDto;
import com.leadnews.wemedia.vo.WmNewsVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.admin.task
 * admin端每隔5秒钟，去到command side查询符合同步条件的aggregate，
 * 若查到了，就让command side自己远程调用query side去保存aggregate
 */
@Component
@Slf4j
public class SyncArticleTask {

    @Resource
    private WemediaNewsAutoScanService wemediaNewsAutoScanService;

    /**
     * 文章同步 定时任务
     * @param params
     * @return
     *
     * 同步审核通过之后的文章列表进文章微服务系统，总共需要2步：
     *     1、admin远程调用wm系统获取审核通过后的列表；
     *     2、admin拿着该列表远程调用文章微服务去做保存
     */
    @XxlJob("syncArticleTask")
    public ReturnT<String> syncArticleJob(String params){

        //1. 分片策略1：
        // params=1
        //  channel=1
        String uuid = UUID.randomUUID().toString();
        log.info("开始进行文章同步{}", uuid);
        // 文章同步：一、远程调用wm获取审核通过后的列表。需要传进来同步需要的dto参数
        // 文章同步
        // 查询需要同步的文章, 条件: 状态为in(4,8), publisTime<=当前系统时间
        WmNewsPageReqDto dto = new WmNewsPageReqDto(); //相当于新建一张表！下面设置一个新的字段，并填上值！
        dto.setEndPubDate(new Date());

        List<WmNewsVo> syncList = null;
        try {
            syncList = wemediaNewsAutoScanService.list4ArticleSync(dto);
        } catch (Exception e) {
            log.error("查询需要同步的文章报错了",e);
            // 告诉xxl-job-admin，任务调度失败
            return ReturnT.FAIL;
        }

        log.debug("需要同步的文章数量：{}", syncList==null?0:syncList.size());

        // 文章同步：二、远程调用article去保存一系列表
        if (!CollectionUtils.isEmpty(syncList)) {
            // 打开列表，从可视化新闻对象创建文章综合对象
            for (WmNewsVo wmNewsVo : syncList) {
                try {
                    // 老师的syncArticleInfo方法在构建这一步是写在admin微服务中的（插入步骤是写在article微服务中的），是因为可以省略远程调用adChannel查找频道名称的方法
                    // 但是我是把整体都写在article微服务中了，因此我需要远程调用adChannel
                    wemediaNewsAutoScanService.syncArticleInfo(wmNewsVo);
                } catch (Exception e) {
                    log.error("文章同步失败", e);
                    // 发消息给mq 由mq 去发邮件、发短信通知。。。


                    // 文章同步：三、更新wmnews的文章状态
                    // 最后发送回执更新wmnews的过程，也可以一并在article微服务中做了，
                    // 直接在article系统中远程调用一次wmnews就可以完成最后的更新了。
                    // 就不用再传回任务执行者了再次远程调用了
                }
                log.debug("自媒体文章同步已完成，共{}篇.{}",syncList.size(),uuid);
            }
        }
        log.info("文章同步任务结束{}", uuid);
        return ReturnT.SUCCESS;
    }
}
