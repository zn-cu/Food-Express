package com.sky.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sky.result.Result;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;


@Configuration
@EnableCaching
//这个配置类是解决使用Spring cache注解时，将java对象转为json，从而在redis中可以直观看到数据
/*
* 当你使用的是Spring的Cache缓存并且配置的缓存方式为Redis时，
* Cache实际上采用的是直接获取RedisCacheManager，而并不是RedisTemplate。
* 因此，当我们自定义RedisTemplate的序列化器时，并不会被Cache所采用。
* 只有当我们使用RedisTemplate API编程式地处理缓存时，自定义序列化才会生效。
* */
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 创建一个String序列化器
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 创建ObjectMapper实例(用于处理JSON与Java对象之间的转换)
        ObjectMapper objectMapper = new ObjectMapper();
        // 注册JavaTimeModule模块(确保能够正确地序列化和反序列化LocalDateTime等模块)
        objectMapper.registerModule(new JavaTimeModule());
        // 禁用了将日期写成时间戳的功能，将其格式化为更易读的字符串形式。
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //使用Jackson2JsonRedisSerializer并且指定目标类型(指定为result)，这样可以保留类型信息
        Jackson2JsonRedisSerializer<Result> serializer = new Jackson2JsonRedisSerializer<>(Result.class);
        serializer.setObjectMapper(objectMapper); // 这里的objectMapper应包含你的自定义配置

        // 构建RedisCacheConfiguration并指定序列化策略
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        // 返回一个新的RedisCacheManager实例
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }
}