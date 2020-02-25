package com.msa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msa.document.Reviewer;

public interface ReviewerRepository extends MongoRepository<Reviewer, String> {
	
}
