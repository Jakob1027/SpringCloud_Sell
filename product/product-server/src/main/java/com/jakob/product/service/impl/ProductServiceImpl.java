package com.jakob.product.service.impl;

import com.jakob.product.DecreaseStockInput;
import com.jakob.product.ProductInfoOutput;
import com.jakob.product.dataobject.ProductInfo;
import com.jakob.product.enums.ProductEnum;
import com.jakob.product.enums.ProductStatueEnum;
import com.jakob.product.exception.ProductException;
import com.jakob.product.repository.ProductInfoRepository;
import com.jakob.product.service.ProductService;
import com.jakob.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductInfoRepository productInfoRepository;

    private final AmqpTemplate amqpTemplate;

    public ProductServiceImpl(ProductInfoRepository productInfoRepository, AmqpTemplate amqpTemplate) {
        this.productInfoRepository = productInfoRepository;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatueEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = doDecreaseStock(decreaseStockInputList);
        List<ProductInfoOutput> productInfoOutputs = productInfoList.stream().map(productInfo -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(productInfo, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        // 发送mq消息
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputs));
    }

    @Transactional
    public List<ProductInfo> doDecreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> list = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ProductEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = productInfoOptional.get();
            int result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (result < 0) {
                throw new ProductException(ProductEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            list.add(productInfo);
        }
        return list;
    }
}
