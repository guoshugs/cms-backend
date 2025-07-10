package com.leadnews.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leadnews.search.mapper.ApUserSearchMapper;
import com.leadnews.search.pojo.ApUserSearch;
import com.leadnews.search.service.ApUserSearchService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @description <p>APP用户搜索信息 业务实现</p>
 *
 * @version 1.0
 * @package com.leadnews.search.service.impl
 */
@Service
public class ApUserSearchServiceImpl extends ServiceImpl<ApUserSearchMapper, ApUserSearch> implements ApUserSearchService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 异步存入redis，保存搜索记录。思路：
     * 每次新的查询请求进来，都要去redis中先判断存不存在“搜索的keyword”
     * 如果有值，要转成list。这时是翻新，翻新之后json字符串也变化了，所以翻新也得存入redis
     * 如果没有值，要new ArrayList<>()，添加记录到内存的list，再保存到redis中
     * 返回前端请求的响应list
     * 对于保存在redis中的历史记录，可以设置存活时间
     * @param searchWords
     */
    @Override
    @Async(value = "searchWordsPool")
    public void saveSearchRecord(String searchWords, Number id) { // 这里的id是登录用户id
        /* 这里的id是Number类型。之前报错是No thread-bound request found，要获取的请求对象超出了web线程
        * 因为执行保存历史记录的是线程池，根本就不是前端请求过来的线程，根本没有保存用户id
        * 解决的方式有2种，可以使用父（原请求）子（异步）线程，这样子线程能拿父线程里面存的用户id
        * 另一种方法，就是在执行异步的时候，将用户id传给异步线程就可以了。
        * 也可以传入Long类型的userId，没必要非得用Number类型*/
        System.out.println(Thread.currentThread().getName());// 用于调试

        String key = "USER_SEARCH_" + id;
        List<String> records = new ArrayList<>(10);

        String redisValue = stringRedisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(redisValue)){
            records = JSON.parseArray(redisValue, String.class);
        }
        add(records, searchWords);
        String newRedisValue = JSON.toJSONString(records);
        stringRedisTemplate.opsForValue().set(key,newRedisValue, Duration.ofDays(7));
    }

    private void add(List<String> list, String data) {
        int index = list.indexOf(data);
        if(index >= 0){
            list.remove(index);
        }
        if(list.size() >= 10) {
            list.remove(9);
        }
        list.add(0, data);
    }


    /*----------test-----------------*/
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            //list.add(i+""); // 不能保证最近的搜索在最上面
            list.add(0, i+""); // 能保证最近的搜索在最上面。因为index=0，指明了element被inserted的位置
        }
        list.forEach(System.out::println);
        System.out.println("===================");
        // 如果在这里再add一个查询，就会多出10条历史记录的限制。所以需要删除最久的一个，应该先删除，而不是后删除，这样不会扩容
        // 如果新增的这个已经存在于历史记录10个中了，那么哪怕新增，也不应该删除最久。所以翻新规则要优先于删除最久规则
        addOne(list,"zs");
        addOne(list,"5");
        addOne(list,"7");
        list.forEach(System.out::println);
    }
    private static void addOne(List<String> list, String word) {
        int index = list.indexOf(word);
        if (index >= 0) { // 说明在历史记录中存在这次的新查询，需要翻新
            list.remove(index);
        }
        if(list.size() >= 10) {
            list.remove(9);
        }
        list.add(0, word);
    }
}

