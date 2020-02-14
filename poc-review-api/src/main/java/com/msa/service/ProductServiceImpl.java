package com.msa.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.msa.dto.ProductDTO;
import com.msa.dto.ReviewDTO;
import com.msa.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	private final MongoTemplate mongoTemplate;
	
	public ProductServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	// 이름으로 검색 시
	public List<ProductDTO> getProductListByName(String prdNm) {
		Query query = new Query()
				.addCriteria(Criteria.where("prdSeq").is(prdNm));
		return mongoTemplate.find(query, ProductDTO.class);
	}
	
	// 상품코드에 대한 값 출력
	public ProductDTO getProductListByPrdSeq(String prdSeq) {
		Query query = new Query()
				.addCriteria(Criteria.where("prdSeq").is(prdSeq));
		ProductDTO product = mongoTemplate.findOne(query, ProductDTO.class,"products");
		return product;
	}

	public List<ProductDTO> getProductList() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}
}
