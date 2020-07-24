package com.jakob.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
//    })
    @HystrixCommand
    @GetMapping("/getProductList")
    public String getProductList(@RequestParam Integer num) {
        if (num % 2 == 0) {
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.postForObject("http://localhost:8080/product/listForOrder", Arrays.asList("157875196366160022"), String.class);
        return s;
    }

    private String fallback() {
        return "oops！访问失败";
    }

    private String defaultFallback() {
        return "默认提示：oops！访问失败";
    }
}
