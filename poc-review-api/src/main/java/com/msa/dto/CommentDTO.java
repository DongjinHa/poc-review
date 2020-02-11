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
	private String review_id;	//ObjectId로 설정할 경우 DB상의 _id값과 차이 발생하여 변경
	private ObjectId reviewer_id;
	private String cnts;
	private String regDate;
	private String updDate;
}
