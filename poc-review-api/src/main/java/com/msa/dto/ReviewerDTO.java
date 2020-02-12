package com.msa.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviewers")
public class ReviewerDTO {
	@Id
	private String _id;	//ObjectId로 설정할 경우 DB상의 _id값과 차이 발생하여 변경
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
