package com.leadnews.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.leadnews.admin.feign.AdChannelFeign;
import com.leadnews.admin.pojo.AdChannel;

import com.leadnews.common.constants.SC;
import com.leadnews.common.exception.LeadNewsException;
import com.leadnews.common.vo.ResultVo;
import com.leadnews.wemedia.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Resource
    private AdChannelFeign adChannelFeign;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /* 既然调用结果要存储到redis，那么用redis中用什么结构来存储呢？
    * 字符串（Strings）、哈希表（Hashes）、列表（Lists）、集合（Sets）、有序集合（Sorted Sets
    * 需要有序，而sortedset太重了，不能用大容器装个列表。
    * 用的StringTemplate，序列化方式就是String，同时存储的KV对也都是String*/
    /**
     * 查询频道列表
     * @return
     */
    @Override
    public List<AdChannel> listChannels() {
        //1. 从redis中来查询频道列表
        String key = SC.REDISKEY_CHANNELLIST;
        String json = stringRedisTemplate.opsForValue().get(key);
        //2. 不存在
        List<AdChannel> channelList = new ArrayList<>();
        if(StringUtils.isEmpty(json)) {
            //2.1 则远程调用admin微服查询频道列表
            ResultVo<List<AdChannel>> getResult = adChannelFeign.listChannel();
            //2.2 解析结果
            if(!getResult.isSuccess()){
                log.error("远程调用Admin微服:查询频道列表:失败了:" + getResult.getErrorMessage());
                throw new LeadNewsException("查询频道列表失败!");
            }
            //2.3 获取了频道列表
            channelList = getResult.getData();
            // 穿透(查询一个数据库不存在的数据，如果有数据则存入redis，如果没有数据则不存入redis，导致穿透)、
            if(null == channelList){// 如果返回的是空值，就要短一些。有列表，且频道本身不容易改变，可以设置长久有效。
                channelList = new ArrayList<>();
                // 如果ad            //2.4 存入redis, 什么时候更新。 Redis缓存问题? 雪崩(大量的key同一时间失效)、min没返回数据，为了防止缓存穿透，因此即使没数据也要保存到redis，但设置30s有效期
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(channelList), 30, TimeUnit.SECONDS);
            }else{
                //有数据则保存到redis并不设置过期。为什么？频道列表是一个高频访问数据，防止缓存击穿。
                //什么时候更新？当admin端操作频道时，使用MQ异步来修改redis。redis与mysql数据的一致性问题
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(channelList)); // 高频访问数据，哪哪都用，防止缓存击穿
            }
        }else {
            //3. 存在
            //3.1 转成java对旬
            channelList = JSON.parseArray(json, AdChannel.class);
        }
        return channelList;
    }
}
