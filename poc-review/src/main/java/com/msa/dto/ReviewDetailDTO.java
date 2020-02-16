package com.msa.dto;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
public class ReviewDetailDTO {
	@DBRef
	private ReviewDTO review;
	
	@DBRef
	private ReviewerDTO reviewer;
	
	@DBRef
	private CommentDTO comment;
	
	@DBRef
	private ProductDTO product;
}
