package com.jakob.product.service;

import com.jakob.product.DecreaseStockInput;
import com.jakob.product.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {

    /**
     * 查询所有在架商品
     *
     * @return 在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 获取商品列表
     *
     * @param productIdList
     * @return
     */
    List<ProductInfo> findList(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
