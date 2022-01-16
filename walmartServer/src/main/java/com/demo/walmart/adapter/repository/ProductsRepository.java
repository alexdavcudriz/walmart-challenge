package com.demo.walmart.adapter.repository;

import com.demo.walmart.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends MongoRepository<Product, String> {

    String FIND_BY_ID_QUERY = "{ 'id': ?0 }";
    String FIND_BY_BRAND_OR_DESCRIPTION_QUERY = "{ $or: [ { 'brand': /?0/ }, { 'description': /?0/ }]}";

    @Query(FIND_BY_ID_QUERY)
    List<Product> findById(Integer value);

    @Query(FIND_BY_BRAND_OR_DESCRIPTION_QUERY)
    List<Product> findByBrandOrDescription(String value);
}
