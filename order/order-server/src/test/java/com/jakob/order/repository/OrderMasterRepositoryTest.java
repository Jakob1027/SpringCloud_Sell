package com.jakob.order.repository;

import com.jakob.order.OrderApplicationTests;
import com.jakob.order.dataobject.OrderMaster;
import com.jakob.order.enums.OrderStatusEnum;
import com.jakob.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("test");
        orderMaster.setBuyerPhone("136xxxxxxxx");
        orderMaster.setBuyerAddress("jxgz");
        orderMaster.setBuyerOpenid("123123");
        orderMaster.setOrderAmount(BigDecimal.valueOf(123.456));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }
}