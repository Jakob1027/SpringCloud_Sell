package com.jakob.product.service;

import com.jakob.product.DecreaseStockInput;
import com.jakob.product.ProductApplicationTests;
import com.jakob.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

class ProductServiceTest extends ProductApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void findUpAll() {
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    void decreaseStock() {
        productService.decreaseStock(Arrays.asList(new DecreaseStockInput("157875196366160022", 5), new DecreaseStockInput("157875227953464068", 100)));
    }
}