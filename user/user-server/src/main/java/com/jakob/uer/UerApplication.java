package com.jakob.uer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UerApplication.class, args);
    }

}
