package com.msa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.msa.dto.ReviewDTO;
import com.msa.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	private final MongoTemplate mongoTemplate;
	
	public ReviewServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        return reviewRepository.save(reviewDTO);
    }
    
	public List<ReviewDTO> getReviewList() {
		Query query = new Query()
				.addCriteria(Criteria.where("title").is("DongjinHa2"))
				.with(Sort.by(Sort.Order.desc("revrSeq")))
				.limit(2);
		return mongoTemplate.find(query, ReviewDTO.class);
	}
	
    public List<ReviewDTO> getReviewList2() {
        return reviewRepository.findAll();
    } 
    
	public List<ReviewDTO> getReviewList3(String title) {
		Query query = new Query()
				.addCriteria(Criteria.where("title").is("DongjinHa2"))
				.with(Sort.by(Sort.Order.desc("revrSeq")))
				.limit(2);
		return mongoTemplate.find(query, ReviewDTO.class);
	}
	
    public Optional<ReviewDTO> getReview(String id) {
        return reviewRepository.findById(id);
    }
    
    public void delReview(String id) {
    	reviewRepository.deleteById(id);
    }
    
}
