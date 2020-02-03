package com.msa.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comments")
public class CommentDTO {
	@Id
	private ObjectId _id;
	private ObjectId review_id;
	private ObjectId reviewer_id;
	private String cnts;
	private String regDate;
	private String updDate;
}
