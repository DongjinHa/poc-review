package com.msa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msa.dto.CommentDTO;

public interface CommentRepository extends MongoRepository<CommentDTO, String> {
	
}
