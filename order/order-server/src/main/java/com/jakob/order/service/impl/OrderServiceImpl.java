package com.jakob.order.service.impl;

import com.jakob.order.dataobject.OrderDetail;
import com.jakob.order.dataobject.OrderMaster;
import com.jakob.order.dto.OrderDTO;
import com.jakob.order.enums.OrderStatusEnum;
import com.jakob.order.enums.PayStatusEnum;
import com.jakob.order.enums.ResultEnum;
import com.jakob.order.exception.OrderException;
import com.jakob.order.repository.OrderDetailRepository;
import com.jakob.order.repository.OrderMasterRepository;
import com.jakob.order.service.OrderService;
import com.jakob.order.utils.KeyUtil;
import com.jakob.product.DecreaseStockInput;
import com.jakob.product.ProductInfoOutput;
import com.jakob.product.client.ProductClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMasterRepository orderMasterRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ProductClient productClient;

    public OrderServiceImpl(OrderMasterRepository orderMasterRepository, OrderDetailRepository orderDetailRepository, ProductClient productClient) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productClient = productClient;
    }

    /**
     * 2. 查询商品信息
     * 3. 计算总价
     * 4. 扣库存
     * 5. 订单入库
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        /**
         * 2. 查询商品信息
         * 3. 计算总价
         * 4. 扣库存
         * 5. 订单入库
         */
        List<String> productIdList = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.getProductInfoList(productIdList);


        BigDecimal amount = new BigDecimal(0);
        List<DecreaseStockInput> decreaseStockInputList = new ArrayList<>();
        orderDTO.setOrderId(KeyUtil.getUniqueKey());

        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            for (ProductInfoOutput productInfo : productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    amount = productInfo.getProductPrice()
                            .multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()))
                            .add(amount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderDTO.getOrderId());
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    orderDetail.setCreateTime(null);
                    orderDetail.setUpdateTime(null);
                    decreaseStockInputList.add(new DecreaseStockInput(orderDetail.getProductId(), orderDetail.getProductQuantity()));
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        productClient.decreaseStock(decreaseStockInputList);
        orderDTO.setOrderAmount(amount);

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if (!orderMasterOptional.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderMaster orderMaster = orderMasterOptional.get();
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.isEmpty()) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }


}
