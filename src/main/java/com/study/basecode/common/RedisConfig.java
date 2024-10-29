package com.study.basecode.common;

import com.study.basecode.domain.store.dto.CustomPage;
import com.study.basecode.domain.store.dto.StoreResponseDto;
import com.study.basecode.domain.store.entity.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, CustomPage> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,CustomPage> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}