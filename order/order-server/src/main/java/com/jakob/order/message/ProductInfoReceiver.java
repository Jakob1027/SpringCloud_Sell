package com.jakob.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jakob.order.utils.JsonUtil;
import com.jakob.product.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
@Transactional
public class ProductInfoReceiver {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    public ProductInfoReceiver(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // message => ProductInfoOutput
        List<ProductInfoOutput> productInfoOutputs = JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutput>>() {
        });
        log.info("从队列 【{}】 接收到消息 {}", "productInfo", productInfoOutputs);

        // 存储到redis
        for (ProductInfoOutput productInfoOutput : productInfoOutputs) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, productInfoOutput.getProductId()),
                    productInfoOutput.getProductStock().toString());
        }
    }
}
