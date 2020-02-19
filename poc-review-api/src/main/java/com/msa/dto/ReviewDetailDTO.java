package com.msa.dto;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class ReviewDetailDTO {
	private String _id;	//ObjectId로 설정할 경우 DB상의 _id값과 차이 발생하여 변경
	private String reviewer_id;
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
	
	@DBRef
	private ReviewerDTO reviewer;
	
	@DBRef
	private List<CommentDTO> comment;
}
