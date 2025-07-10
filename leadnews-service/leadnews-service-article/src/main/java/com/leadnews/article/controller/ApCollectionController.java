package com.leadnews.article.controller;


import com.leadnews.article.dto.CollectionBehaviorDto;
import com.leadnews.article.pojo.ApCollection;
import com.leadnews.article.service.ApCollectionService;
import com.leadnews.common.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import com.leadnews.core.controller.AbstractCoreController;

/**
 * @description <p>APP收藏信息</p>
 *
 * @version 1.0
 * @package com.leadnews.article.controller
 */
@Api(value="ApCollectionController",tags = "APP收藏信息")
@RestController
@RequestMapping("/collection_behavior")
public class ApCollectionController extends AbstractCoreController<ApCollection> {

    private ApCollectionService apCollectionService;

    @Autowired
    public ApCollectionController(ApCollectionService apCollectionService) {
        super(apCollectionService);
        this.apCollectionService=apCollectionService;
    }

    /** day10 用户关系行为展示
     * APP收藏行为
     * @param collectionBehaviorDto
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResultVo collectionBehavior(@RequestBody CollectionBehaviorDto collectionBehaviorDto) {
        apCollectionService.collectionBehavior(collectionBehaviorDto);
        return ResultVo.ok();
    }

}

