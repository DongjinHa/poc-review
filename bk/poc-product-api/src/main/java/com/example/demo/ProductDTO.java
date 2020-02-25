package com.example.demo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "products")
public class ProductDTO {
	private ObjectId _id;
	private String prdSeq; 
	private String prdNm;
	private String prdImg2Nd;
	private int salPrc;
	private String test1;
	private String test2;
}
