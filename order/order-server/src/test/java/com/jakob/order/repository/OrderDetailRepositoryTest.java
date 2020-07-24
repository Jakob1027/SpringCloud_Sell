package com.jakob.order.repository;

import com.jakob.order.OrderApplicationTests;
import com.jakob.order.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

class OrderDetailRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testSave() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("157875227953464068");
        orderDetail.setProductName("慕斯蛋糕");
        orderDetail.setProductPrice(BigDecimal.valueOf(10.9));
        orderDetail.setProductIcon("//fuss10.elemecdn.com/9/93/91994e8456818dfe7b0bd95f10a50jpeg.jpeg");
        orderDetail.setProductQuantity(5);

        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(result);
    }
}