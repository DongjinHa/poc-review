package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.ProductDTO;
import com.example.demo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
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
	
	public List<ProductDTO> getProductListByPrdNm(String prdNm) {
		Query query = new Query()
				.addCriteria(Criteria.where("prdNm").regex(prdNm));
		return mongoTemplate.find(query, ProductDTO.class);
	}
	
}
