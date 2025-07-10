package com.leadnews.search.config;

import com.leadnews.search.pojo.ApAssociateWords;
import com.leadnews.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.trie4j.patricia.PatriciaTrie;

import java.util.List;

/**
 * @version 1.0
 * @description 说明
 * @package com.leadnews.search.config
 */
@Configuration
public class ApAssociateWordsInitializingBean implements InitializingBean {
    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    @Bean
    public PatriciaTrie patriciaTrie(){
        PatriciaTrie pat = new PatriciaTrie();
        return pat;
    }

    @Autowired
    private PatriciaTrie pat;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<ApAssociateWords> associateWordsList = apAssociateWordsService.list();//在服务一启动就去数据库查询并装在进进程缓存的trie容器中了
        for (ApAssociateWords apAssociateWords : associateWordsList) {
            pat.insert(apAssociateWords.getAssociateWords());
        }
    }
}
