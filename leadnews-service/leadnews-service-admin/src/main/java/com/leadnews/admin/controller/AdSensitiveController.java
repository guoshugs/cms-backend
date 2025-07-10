package com.leadnews.admin.controller;


import com.leadnews.admin.pojo.AdSensitive;
import com.leadnews.admin.service.AdSensitiveService;
import com.leadnews.core.controller.AbstractCoreController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>敏感词信息</p>
 *
 * @version 1.0
 * @package com.leadnews.admin.controller
 */
@Api(value="AdSensitiveController",tags = "敏感词信息")
@RestController
@RequestMapping("/sensitive")
public class AdSensitiveController extends AbstractCoreController<AdSensitive> {

    private AdSensitiveService adSensitiveService; // 若是下面有用到该类的service，则可以添加service为属性，并在构造方法中初始化。这其实也是类与类之间关系的一种。

    /**
     * spring的注入
     * @Autowired @Resource
     * setter方法，构造方法
     *
     * 一个类加载时，先调用构造方法，再对内部属性进行初始化，这是必须的两步。
     */
    public AdSensitiveController(AdSensitiveService adSensitiveService) {
        super(adSensitiveService);
        this.adSensitiveService = adSensitiveService; // 在构造方法内部对属性进行初始化了，就不用再在属性的上面加注解@Autowired
    }

    /*
    在一个类内部加入另一个类作为属性，并在构造方法中对这个属性进行赋值，这种关系通常被称为组合关系或者聚合关系。
    在面向对象编程中，组合关系表示一种强关联关系，其中一个类包含了另一个类的对象作为自己的一部分。
    这种关系可以描述为“has-a”关系，即一个类“有一个”另一个类的对象。

    举例来说，如果有一个Car类，而在Car类中加入一个Engine类作为属性，
    并在Car类的构造方法中对Engine属性进行初始化赋值，那么Car和Engine之间就是组合关系。
    这表示一个汽车“有一个”引擎作为其一部分。

    组合关系和聚合关系在概念上是相似的，都表示包含关系，但在语义上略有不同。
    在组合关系中，包含的对象与容器对象的生命周期是相同的，
    容器对象的生命周期结束时，包含的对象也会被销毁。
    而在聚合关系中，包含的对象的生命周期可以独立于容器对象的生命周期，
    即包含的对象可以存在于其他地方或被多个容器对象共享。
    */
}

