package com.msa.document;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviews")
public class Review {
	
	private ObjectId _id;
	private ObjectId reviewer_id;
	private String reviewCl;
	private String prdSeq;
	private String bestFl;
	private Integer evalScore;
	private Integer hit;
	private Integer recomCnt;
//	private String cmtCnt;
	private Integer recbScore;
	private String goodCnts;
	private String etcCnts;
//	private String tplRegCnt;
//	private String prevImg;
	private String[] tplList;
	private Date regDate;
	private Date updDate;

}
