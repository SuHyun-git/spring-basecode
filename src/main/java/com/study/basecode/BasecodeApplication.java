package com.study.basecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BasecodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasecodeApplication.class, args);
    }

    @GetMapping("/")
    public String test() {
        return "프로젝트 실행!!";
    }
}
