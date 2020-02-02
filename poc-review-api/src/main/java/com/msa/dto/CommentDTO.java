package com.msa.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comments")
public class CommentDTO {
	@Id
	private String _id;
	private String review_id;
	private String revrSeq;
	private String cnts;
	private String regDate;
	private String updDate;
}
