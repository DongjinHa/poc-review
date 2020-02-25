package com.msa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.msa.document.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
	
}
