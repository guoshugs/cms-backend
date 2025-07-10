package com.leadnews.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

    @XxlJob(value = "testTask")
    public ReturnT<String> handlerTask(String param){
        XxlJobLogger.log("测试任务开始了~~~");
        for (int i = 0; i < 5; i++) {
            System.out.println(i + 1);
        }
        XxlJobLogger.log("测试任务结束========");
        return ReturnT.SUCCESS;
    }
}
