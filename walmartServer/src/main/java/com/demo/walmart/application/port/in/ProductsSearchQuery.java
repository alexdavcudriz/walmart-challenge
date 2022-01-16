package com.demo.walmart.application.port.in;

import com.demo.walmart.domain.Product;

import java.util.List;

public interface ProductsSearchQuery {

    List<Product> execute(String value);
}
