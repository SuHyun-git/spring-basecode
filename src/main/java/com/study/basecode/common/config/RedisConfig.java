package com.study.basecode.common.config;

import com.study.basecode.domain.memo.entity.Memo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
//
//    @Value("${REDIS_HOST_NAME}")
//    private String localhost;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
//                .master("mymaster")
//                .sentinel(localhost, 26379)
//                .sentinel(localhost, 26380)
//                .sentinel(localhost, 26381);
//
//        return new LettuceConnectionFactory(redisSentinelConfiguration);
//    }

    @Bean
    public RedisTemplate<String, Memo> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Memo> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
