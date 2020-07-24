package com.jakob.order.controller;

import com.jakob.order.config.GirlConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GirlController {

    private final GirlConfig girlConfig;

    public GirlController(GirlConfig girlConfig) {
        this.girlConfig = girlConfig;
    }

    @GetMapping("/girl")
    public String girl() {
        return girlConfig.getName()+"==="+girlConfig.getAge();
    }
}
