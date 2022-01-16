package com.demo.walmart.utils;

import com.demo.walmart.adapter.controller.model.ProductsResponse;
import com.demo.walmart.domain.Product;

import java.util.Arrays;
import java.util.List;

public interface StubsFactory {

    String objectId = "61e0a60be7a8a1453b9378ba";
    Integer id = 123;
    String brand = "as";
    String description = "ghjk zxcv";
    String image = "www.lider.cl/catalogo/images/babyIcon.svg";
    Integer price = 1272633;

    String INVALID_SEARCH_MESSAGE = "Invalid search length";
    String PALINDROME_SEARCH_VALUE = "abba";
    String NUMERIC_PALINDROME_SEARCH_VALUE = "1661";

    String PRODUCTS_SEARCH_ENDPOINT = new StringBuilder().append("/api/v1.0/products/").append(description).toString();
    String PRODUCTS_SHORT_SEARCH_VALUE_ENDPOINT = new StringBuilder().append("/api/v1.0/products/").append(brand).toString();

    default Product buildProduct() {
        return Product.builder()
                .objectId(objectId)
                .id(id)
                .brand(brand)
                .description(description)
                .image(image)
                .price(price)
                .build();
    }

    default List<Product> buildProductsList() {
        return Arrays.asList(buildProduct());
    }

    default List<ProductsResponse> buildProductsResponseList() {
        return ProductsResponse.of(buildProductsList());
    }
}
