package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.ProductDTO;

public interface ProductRepository extends MongoRepository<ProductDTO, String> {

}
