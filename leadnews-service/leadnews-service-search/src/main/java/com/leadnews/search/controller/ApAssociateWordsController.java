package com.leadnews.search.controller;


import com.leadnews.common.vo.ResultVo;
import com.leadnews.core.controller.AbstractCoreController;
import com.leadnews.search.dto.SearchDto;
import com.leadnews.search.pojo.ApAssociateWords;
import com.leadnews.search.service.ApAssociateWordsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description <p>联想词</p>
 *
 * @version 1.0
 * @package com.leadnews.search.controller
 */
@Api(value="ApAssociateWordsController",tags = "联想词")
@RestController
@RequestMapping("/associate")
public class ApAssociateWordsController extends AbstractCoreController<ApAssociateWords> {

    private ApAssociateWordsService apAssociateWordsService;

    @Autowired
    public ApAssociateWordsController(ApAssociateWordsService apAssociateWordsService) {
        super(apAssociateWordsService);
        this.apAssociateWordsService=apAssociateWordsService;
    }

    /**
     * 搜索词联想
     * @param dto
     * @return
     */
    @PostMapping("/search")
    public ResultVo<List<String>> search(@RequestBody SearchDto dto){
        List<String> list = apAssociateWordsService.searchV2(dto);
        return ResultVo.ok(list);
    }
}

