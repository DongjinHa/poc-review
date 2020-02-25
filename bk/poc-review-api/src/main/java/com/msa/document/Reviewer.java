package com.msa.document;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviewers")
public class Reviewer {
	
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
	private Date regDate;
	private Date updDate;
	
}
