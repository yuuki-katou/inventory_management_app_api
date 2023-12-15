package com.yuuki.katou.inventory_management_app01.service;


import com.yuuki.katou.inventory_management_app01.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    //idを基に特定の商品情報を取得する
    ProductDto findById(int id);

    List<ProductDto> findByCriteria(String keyword, String brandName, String categoryName, String storeName, BigDecimal priceLower, BigDecimal priceUpper, String conditionName, String sortOrder);

    int createProduct(ProductDto productDto);

    int updateProduct(ProductDto productDto);

    int deletedProduct(int productId);
}
