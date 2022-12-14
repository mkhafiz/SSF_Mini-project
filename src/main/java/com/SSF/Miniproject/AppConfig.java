package com.SSF.Miniproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// This is meant to configure Redis so we can utilize it
@Configuration
public class AppConfig {

    // private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    // The @Value allows us to get the actual value from the application.properties
    // file and initialize here
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.database}")
    private Integer redisDatabase;
    @Value("${spring.redis.username}")
    private String redisUsername;
    @Value("${REDIS_PASSWORD}")
    private String redisPassword;

    // RedisTemplate is available to us as we added the dependency in when creating
    // the spring package
    // Bean allows us to get the return type RedisTemplate<String, String> to inject
    // to the Qualifier
    @Bean("redislab")
    public RedisTemplate<String, String> initRedisTemplate() {

        // Configure the Redis database
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setDatabase(redisDatabase);
        redisConfig.setUsername(redisUsername);
        redisConfig.setPassword(redisPassword);

        // Create an instance of the Jedis driver
        JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();

        // Create a factory for jedis connection
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFac.afterPropertiesSet();

        // Create RedisTemplate
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisFac);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer()); // add
        redisTemplate.setHashValueSerializer(redisTemplate.getValueSerializer());  // add

        return redisTemplate;
    }
}
