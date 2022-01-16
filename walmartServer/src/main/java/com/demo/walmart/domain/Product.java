package com.demo.walmart.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("products")
public class Product {

    private static final Double PRICE_DISCOUNT = 0.5;

    @Id
    String objectId;
    Integer id;
    String brand;
    String description;
    String image;
    Integer price;

    public Product doDiscount() {
        this.setPrice((int)Math.round(this.price*PRICE_DISCOUNT));
        return this;
    }
}
