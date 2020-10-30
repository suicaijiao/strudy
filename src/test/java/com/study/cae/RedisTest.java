package com.study.cae;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    private String key = "room:msg:1001";

    @Test
    public void producer() {
        Map map = new HashMap();
        map.put("userId", "zhangshan");
        map.put("content", "美女真漂亮");
        redisTemplate.opsForStream().add(key, map);
    }

}
