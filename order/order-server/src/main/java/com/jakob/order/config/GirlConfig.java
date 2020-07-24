package com.jakob.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "girl")
@Component
@RefreshScope
public class GirlConfig {
    private String name;
    private Integer age;
}
