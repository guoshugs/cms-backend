package com.leadnews.wemedia.service;


import com.leadnews.admin.pojo.AdChannel;

import java.util.List;

public interface ApiService {

    /**
     * 查询频道列表
     * @return
     */
    List<AdChannel> listChannels();
}
