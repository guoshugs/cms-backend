package com.leadnews.search.controller;


import com.leadnews.core.controller.AbstractCoreController;
import com.leadnews.search.pojo.ApHotWords;
import com.leadnews.search.service.ApHotWordsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description <p>搜索热词</p>
 *
 * @version 1.0
 * @package com.leadnews.search.controller
 */
@Api(value="ApHotWordsController",tags = "搜索热词")
@RestController
@RequestMapping("/apHotWords")
public class ApHotWordsController extends AbstractCoreController<ApHotWords> {

    private ApHotWordsService apHotWordsService;

    @Autowired
    public ApHotWordsController(ApHotWordsService apHotWordsService) {
        super(apHotWordsService);
        this.apHotWordsService=apHotWordsService;
    }

}

