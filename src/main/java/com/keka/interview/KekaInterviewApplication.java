package com.keka.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class KekaInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(KekaInterviewApplication.class, args);
    }
}
