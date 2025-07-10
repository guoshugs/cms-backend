package com.leadnews.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.search.dto.SearchDto;
import com.leadnews.search.mapper.ApAssociateWordsMapper;
import com.leadnews.search.pojo.ApAssociateWords;
import com.leadnews.search.service.ApAssociateWordsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.trie4j.patricia.PatriciaTrie;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description <p>联想词 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.search.service.impl
 */
@Service
public class ApAssociateWordsServiceImpl extends ServiceImpl<ApAssociateWordsMapper, ApAssociateWords> implements ApAssociateWordsService {

    /**
     * 搜索词联想
     * @param dto
     * @return
     */
    @Override
    public List<String> search(SearchDto dto) {
        IPage<ApAssociateWords> pageInfo = new Page<>(1, 10);

        LambdaQueryWrapper<ApAssociateWords> lqw = new LambdaQueryWrapper<>();
        lqw.like(!StringUtils.isEmpty(dto.getSearchWords()), ApAssociateWords::getAssociateWords, dto.getSearchWords());
        lqw.orderByDesc(ApAssociateWords::getCreatedTime);

        page(pageInfo, lqw);

        List<ApAssociateWords> records = pageInfo.getRecords();
        List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(records)) {
            list = records.stream().map(ApAssociateWords::getAssociateWords).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 搜索词联想
     * 使用字典树
     * @param dto
     * @return
     */

    @Resource
    private PatriciaTrie pat;

    // 使用自己的jvm的缓存，进程缓存，就在自己的服务器上，而不用去查数据库。都是占用进程的内存
    // 静态属性、单例对象，都算是进程中的缓存
    // 不需要查询数据库，也不需要查询redis，响应非常快。
    @Override
    public List<String> searchV2(SearchDto dto) { // 所以虽然客户端输入一个字符都去查询一次，但因为有进程间缓存的trie容器，查询更有效率

        List<String> list = new ArrayList<>();
        if(null != dto && !StringUtils.isEmpty(dto.getSearchWords())){
            pat.insert(dto.getSearchWords());
            // 前缀匹配
            list = (List<String>) pat.predictiveSearch(dto.getSearchWords());//patTrie的预测查询方法返回值是可迭代的接口类型
            if(list.size() > 10) {
                return list.subList(0, 10);
            }
        }
        return list;
    }
}
