package com.msa.document;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comments")
public class Comment {
	
	private ObjectId _id;
	private ObjectId review_id;
	private ObjectId reviewer_id;
	private String cnts;
	private Date regDate;
	private Date updDate;
	
}
