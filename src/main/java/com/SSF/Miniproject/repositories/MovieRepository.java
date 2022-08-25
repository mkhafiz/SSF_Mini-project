package com.SSF.Miniproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String id, String payload) {

        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(id, payload); // , Duration.ofSeconds(cacheTime)
        System.out.printf("%s is saved\n", id);
    }

    public String get(String id) {

        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String value = valueOps.get(id);
        System.out.printf("%s retrieved successfully\n", id);
        return value;
    }
}