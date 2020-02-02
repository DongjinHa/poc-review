package com.msa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msa.dto.ReviewerDTO;

public interface ReviewerRepository extends MongoRepository<ReviewerDTO, String> {
	
}
