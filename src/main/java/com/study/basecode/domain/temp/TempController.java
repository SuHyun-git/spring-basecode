package com.study.basecode.domain.temp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TempController {

    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping()
    public String temp() {
        return "이게 되네";
    }

    @GetMapping("/test")
    public String temp1() {
        String title = "redis 이론수업";
        redisTemplate.opsForValue().set("test", title);
        return title;
    }

    @GetMapping("/test/get")
    public String temp2() {
        String result = (String) redisTemplate.opsForValue().get("test");
        return result;
    }
}
