package com.leadnews.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leadnews.article.pojo.ApArticleConfig;
import com.leadnews.article.mapper.ApArticleConfigMapper;
import com.leadnews.article.service.ApArticleConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description <p>APP已发布文章配置 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.article.service.impl
 */
@Service
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {
    /**
     * 同步文章的上下架
     * @param message
     */
    @Override
    public void upOrDown(String message) {
        JSONObject jsonObject = JSON.parseObject(message);// 解析出来是个map！！！

        /*  存消息时放进去的是articleId!,取消息自然也是
            msgMap.put("articleId", articleId);
            msgMap.put("enable", myWmNews.getEnable().longValue()); // 这里的enable就是状态了。指令是下给wemedia写服务的，读服务article只能做同步

            String jsonString = JSON.toJSONString(msgMap);
            kafkaTemplate.send(BC.MqConstants.WM_NEWS_DOWN_OR_UP_TOPIC, jsonString);*/

        ApArticleConfig updatePojo = new ApArticleConfig();
        updatePojo.setId(jsonObject.getLong("articleId")); //设置更新条件

        Integer enable = jsonObject.getInteger("enable"); // enable代表上架的指令
        System.out.println("~~~~~~~~~调试~~~~~~~~~~~~~~~~~~enable = " + enable);
        updatePojo.setIsDown(enable == 1?0:1); // enable代表上架指令，isDown代表下架的状态

        updateById(updatePojo);
    }
}
