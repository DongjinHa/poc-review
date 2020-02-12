package com.msa.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
public class ReviewerDTO {
	@Id
	private ObjectId _id;
	private String nickNm;
	private String lvl;
	private String sex;
	private String birthDay;
	private String skinToneCd;
	private String skinTypeCd;
	private String[] skinTrublList;
	private String skinEtcInfo;
	private String profileImg;
	private String regDate;
	private String updDate;
}
