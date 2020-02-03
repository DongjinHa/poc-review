package com.msa.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviews")
public class ReviewDTO {
	@Id
	private ObjectId _id;
	private ObjectId reviewer_id;
	private String reviewCl;
	private String prdSeq;
	private String bestFl;
	private String evalScore;
	private String hit;
	private String recomCnt;
//	private String cmtCnt;
	private String recbScore;
	private String goodCnts;
	private String etcCnts;
//	private String tplRegCnt;
//	private String prevImg;
	private String[] tplList;
	private String regDate;
	private String updDate;
}
