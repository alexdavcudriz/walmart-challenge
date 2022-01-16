package com.demo.walmart.adapter.controller.model;

import com.demo.walmart.domain.Product;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductsResponse {

    private static final String HTTP_PROTOCOL = "http://";

    String objectId;
    Integer id;
    String brand;
    String description;
    String image;
    Integer price;

    public static ProductsResponse of(Product product) {
        return ProductsResponse.builder()
                .objectId(product.getObjectId())
                .id(product.getId())
                .brand(product.getBrand())
                .description(product.getDescription())
                .image(appendProtcol(product.getImage()))
                .price(product.getPrice())
                .build();
    }

    public static List<ProductsResponse> of(List<Product> products) {
        return products.stream()
                .map(product -> ProductsResponse.of(product))
                .collect(Collectors.toList());
    }

    private static String appendProtcol(String url) {
        return new StringBuilder().append(HTTP_PROTOCOL).append(url).toString();
    }
}
