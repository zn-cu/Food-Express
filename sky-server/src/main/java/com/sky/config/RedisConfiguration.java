package com.sky.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String,Result> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置redis value的序列化器
        //1.创建ObjectMapper实例(用于处理JSON与Java对象之间的转换)(即序列化和反序列化)
        ObjectMapper objectMapper = new ObjectMapper();
        //2.注册JavaTimeModule模块(确保能够正确地序列化和反序列化LocalDateTime等模块)
        objectMapper.registerModule(new JavaTimeModule());
        //3.禁用了将日期写成时间戳的功能，将其格式化为更易读的字符串形式。
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //4.使用Jackson2JsonRedisSerializer反序列化并且指定目标类型(指定为result)，这样可以保留类型信息
        Jackson2JsonRedisSerializer<Result> serializer = new Jackson2JsonRedisSerializer<>(Result.class);
        serializer.setObjectMapper(objectMapper); // 这里的objectMapper应包含你的自定义配置
        //5.会使用这个序列化器来将Java对象转换为JSON格式进行存储。
        redisTemplate.setValueSerializer(serializer);
        return redisTemplate;
    }
    @Bean
    public RedisTemplate<String,Integer> IntegerRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置redis value的序列化器
        //1.创建ObjectMapper实例(用于处理JSON与Java对象之间的转换)(即序列化和反序列化)
        ObjectMapper objectMapper = new ObjectMapper();
        //2.注册JavaTimeModule模块(确保能够正确地序列化和反序列化LocalDateTime等模块)
        objectMapper.registerModule(new JavaTimeModule());
        //3.禁用了将日期写成时间戳的功能，将其格式化为更易读的字符串形式。
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //4.使用Jackson2JsonRedisSerializer反序列化并且指定目标类型(指定为result)，这样可以保留类型信息
        Jackson2JsonRedisSerializer<Integer> serializer = new Jackson2JsonRedisSerializer<>(Integer.class);
        serializer.setObjectMapper(objectMapper); // 这里的objectMapper应包含你的自定义配置
        //5.会使用这个序列化器来将Java对象转换为JSON格式进行存储。
        redisTemplate.setValueSerializer(serializer);
        return redisTemplate;
    }
}
