package com.leadnews.sync.service.impl;

import com.leadnews.search.document.ArticleInfoDocument;
import com.leadnews.sync.mapper.ArticleMapper;
import com.leadnews.sync.repository.DocumentRepository;
import com.leadnews.sync.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    @Async("taskExecutor")
    public void importAll(Long page, Long size, CountDownLatch countDownLatch, LocalDateTime now) {
        String threadName = Thread.currentThread().getName();
        log.info("{} start: page={},size={}", threadName,page,size);
        //1分页查询到数据
        List<ArticleInfoDocument> articleList = articleMapper.selectByPage((page - 1) * size, size,now);
        log.info("{} start: page={},size={}, actualSize={} found", threadName,page,size,articleList.size());
        //2.分批导入到ES中
        try {
            documentRepository.saveAll(articleList);
            log.info("{} end: page={},size={}, actualSize={} found", threadName,page,size,articleList.size());
        } catch (Exception e) {
            log.error("{} error: page={},size={}, actualSize={} found", threadName,page,size,articleList.size(),e);
        } finally {
            //减掉数量
            countDownLatch.countDown();
        }

    }

    @Override
    public Long selectCount(LocalDateTime now) {
        return articleMapper.selectCount(now);
    }
}
