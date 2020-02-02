package com.msa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msa.dto.ReviewDTO;

public interface ReviewRepository extends MongoRepository<ReviewDTO, String> {

	List<ReviewDTO> findByTitle(String name);
	
}
