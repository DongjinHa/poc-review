package com.msa.dto;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
public class ProductDTO {
	private ObjectId _id;
	private String prdSeq; 
	private String prdNm;
	private int salPrc;
	private String prdImg2Nd;
}
