package com.jakob.order.service;

import com.jakob.order.dto.OrderDTO;

public interface OrderService {
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    OrderDTO finish(String orderId);
}
