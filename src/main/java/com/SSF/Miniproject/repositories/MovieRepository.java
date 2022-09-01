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
    private RedisTemplate<Integer, String> redisTemplate;

    public void save(int id, String payload) {

        ValueOperations<Integer, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(id, payload); 
        System.out.printf("%s is saved\n", id);
    }

    public String get(int id) {

        ValueOperations<Integer, String> valueOps = redisTemplate.opsForValue();
        String value = valueOps.get(id);
        System.out.printf("%s retrieved successfully\n", id);
        return value;
    }
}