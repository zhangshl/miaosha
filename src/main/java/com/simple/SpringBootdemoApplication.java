package com.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/12 5:24 下午
 * @Description：
 */
@SpringBootApplication
@ComponentScan("com.simple")
public class SpringBootdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootdemoApplication.class, args);
    }
}
