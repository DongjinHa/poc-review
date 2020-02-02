package com.msa.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviews")
public class ReviewDTO {
	@Id
	private String _id;
	private String revrSeq;
	private String title;
	private String reviewCl;
	private String prdSeq;
	private String bestFl;
	private String evalScore;
	private String hit;
	private String recomCnt;
	private String cmtCnt;
	private String recbScore;
	private String goodCnts;
	private String badCnts;
	private String etcCnts;
	private String shortCnts;
	private String tplRegCnt;
	private String prevImg;
	private String tplList;
	private String regDate;
	private String updDate;
}
