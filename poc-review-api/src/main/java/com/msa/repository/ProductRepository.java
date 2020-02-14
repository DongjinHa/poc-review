package com.msa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.msa.dto.ProductDTO;

public interface ProductRepository extends MongoRepository<ProductDTO, String> {

}
