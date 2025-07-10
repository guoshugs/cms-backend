package com.leadnews.sync.service;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

public interface ArticleService {
    void importAll(Long page, Long size, CountDownLatch countDownLatch, LocalDateTime now);
    Long selectCount(LocalDateTime now);

}

