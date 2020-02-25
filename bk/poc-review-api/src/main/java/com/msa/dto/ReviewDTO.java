package com.msa.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviews")
public class ReviewDTO {
	private String _id;	//ObjectId로 설정할 경우 DB상의 _id값과 차이 발생하여 변경
	private String reviewer_id;
	private String reviewCl;
	private String prdSeq;
	private String bestFl;
	private String evalScore;
	private int hit;
	private String recomCnt;
//	private String cmtCnt;
	private String recbScore;
	private String goodCnts;
	private String etcCnts;
//	private String tplRegCnt;
//	private String prevImg;
	private String[] tplList;
	private Date regDate;
	private Date updDate;
	
	//필터 변수
	private List<String> prdSeqList;
	private String totCntYn;
	private int totCnt;
	private int pageNo;
	private String key;
	private int sort;
	private String uage = "all";
	private String skintypecd1;
	private String skintypecd2;
	private String skintypecd3;
	private String skintypecd4;
	private String skintypecd5;
	private String skintypecd6;
	private String skintypecd7;
	private String skintypecdyn;
	private String skinetcinfo1;
	private String skinetcinfo2;
	private String skinetcinfo3;
	private String skinetcinfoyn;
	private String skintonecd1;
	private String skintonecd2;
	private String skintonecd3;
	private String skintonecdyn;
	private String mode;
	
	private List<Object> ref;
	
	/*
	 Lookup시 리턴타입으로 List로 하지 않을 경우 type error발생
	 	Failed to convert from type [java.util.ArrayList<?>] to type [com.msa.dto.ReviewerDTO] 
	 */
	private List<ReviewerDTO> reviewer;	
}
