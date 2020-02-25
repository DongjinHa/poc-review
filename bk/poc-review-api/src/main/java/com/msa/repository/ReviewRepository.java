package com.msa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msa.document.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {

	List<Review> findByPrdSeq(String prdSeq);
	
}
