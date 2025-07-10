package com.leadnews.core.knif4j;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @description api展示时的配置信息
 * @package com.leadnews.core.knif4j
 */

@ConfigurationProperties("steven.knife4j")
@Data
public class MyKnife4jProperties {

    private String basePackage = "com.leadnews";

    private String description = "默认描述";

    private String title = "默认标题";

    private String contact = "联系人";

    private String version = "1.0";

    private String serviceUrl = "http://www.leadnews.com";


}
