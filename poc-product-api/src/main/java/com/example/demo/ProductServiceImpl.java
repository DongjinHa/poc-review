package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.ProductDTO;
import com.example.demo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	private final MongoTemplate mongoTemplate;
	
	public ProductServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public ProductDTO getProductListByPrdSeq(String prdSeq) {
		Query query = new Query()
				.addCriteria(Criteria.where("prdSeq").is(prdSeq));
		ProductDTO product = mongoTemplate.findOne(query, ProductDTO.class,"products");
		return product;
	}
	
}
