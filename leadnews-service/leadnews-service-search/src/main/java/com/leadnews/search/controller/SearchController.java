package com.leadnews.search.controller;

import com.alibaba.fastjson.JSON;
import com.leadnews.common.constants.SC;
import com.leadnews.common.util.RequestContextUtil;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.search.document.ArticleInfoDocument;
import com.leadnews.search.dto.HistoryLoadDto;
import com.leadnews.search.dto.SearchDto;
import com.leadnews.search.pojo.ApAssociateWords;
import com.leadnews.search.service.ApAssociateWordsService;
import com.leadnews.search.service.ApUserSearchService;
import com.leadnews.search.service.ArticleInfoDocumentSearchService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class SearchController {
    @Resource
    private ArticleInfoDocumentSearchService articleInfoDocumentSearchService;

    @Resource
    private ApUserSearchService apUserSearchService;

    /**
     * 文章搜索
     * 在文章搜索时，每一次搜索还要有异步线程将搜索记录保存在redis中
     * @param
     * @return
     */
    @PostMapping("/article/search/search")
    public ResultVo<List<ArticleInfoDocument>> search(@RequestBody SearchDto dto) {
        List<ArticleInfoDocument> list = articleInfoDocumentSearchService.search(dto);

        System.out.println(Thread.currentThread().getName());// 用于调试

        if (!StringUtils.isEmpty(dto.getSearchWords())) {
/*            new Thread(()->{
                apUserSearchService.saveSearchRecord(dto.getSearchWords());
            }).start();*/

            Long id = RequestContextUtil.getUserId();
            if (id == SC.ANONYMOUS_USER_ID) {
                apUserSearchService.saveSearchRecord(dto.getSearchWords(), dto.getEquipmentId());
            } else {
                apUserSearchService.saveSearchRecord(dto.getSearchWords(), id);
            }
        }

        return ResultVo.ok(list);
    }

    /**
     * 查询搜索记录
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/history/load")
    public ResultVo<List<String>> historyLoad(@RequestBody HistoryLoadDto dto) {
        List<String> list = new ArrayList<>(dto.getPageSize());
        Long id = RequestContextUtil.getUserId();
        if (id == SC.ANONYMOUS_USER_ID) {
            id = dto.getEquipmentId().longValue();
        }
        String key = "USER_SEARCH_" + id;
        String redisValue = stringRedisTemplate.opsForValue().get(key);
        List<String> records = new ArrayList<>();
        if(!StringUtils.isEmpty(redisValue)) {
            records = JSON.parseArray(redisValue, String.class);
        }
        list = records.stream().limit(dto.getPageSize()).collect(Collectors.toList());

        return ResultVo.ok(list);
    }

    /**
     * hot_keywords/load/
     */
    @Resource
    private ApAssociateWordsService apAssociateWordsService;
    @PostMapping("hot_keywords/load/")
    public ResultVo<List<ApAssociateWords>> hotKeywordsLoad(@RequestBody HistoryLoadDto dto) {
/*        List<ApAssociateWords> keywords = Optional.ofNullable(apAssociateWordsService.list())
                .map(words -> words.stream().limit(dto.getPageSize()).collect(Collectors.toList()))
                .orElse(Collections.emptyList());*/
        // 上面代码为何总报空指针异常？？？

        return ResultVo.ok(apAssociateWordsService.list());
    }
}
