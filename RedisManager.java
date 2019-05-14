package com.example.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;


@Component
@SuppressWarnings("unused")
public class RedisManager {
    //spring-data-redis 中的核心操作类是 RedisTemplate
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //封装set方法，value的类型为String
    public boolean set(String key, String value) {
        //返回值初始化为false，set失败时返回false
        boolean result = false;

        try {
            /*核心操作类RedisTemplate提供多种数据类型的get和set，常用的有：
            * opsForValue() >>> String类型
            * opsForHash() >>> HashMap类型
            * opsForList() >>> List集合类型
            * */
            //这里调用操作类的opsForValue()下的set方法
            redisTemplate.opsForValue().set(key, value);

            //set成功，将返回值置为true
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /*封装get方法，get方法并不复杂
    * 此处封装可将原来的
    *    redisTemplate.opsForValue().get(String key);
    * 更换为
    *    RedisManager.get(String key);
    * 以达到简化代码的目的
    * 除此之外，还可与上面的set方法呼应
    * */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
