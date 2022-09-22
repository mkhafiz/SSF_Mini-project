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

    public void save( int id, String payload) {

        ValueOperations<String, String> valueOp= redisTemplate.opsForValue();
        valueOp.set(Integer.toString(id), payload);
        System.out.printf("%s is saved\n", id);
    }

    public String get(int id) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String value = valueOp.get(Integer.toString(id));
        System.out.printf("%s retrieved successfully\n", id);
        return value;
    }
    
}