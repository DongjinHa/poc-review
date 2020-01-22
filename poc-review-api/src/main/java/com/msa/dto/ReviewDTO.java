package com.msa.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "ReviewMt")
public class ReviewDTO {
	@Id
	private String id;
	private String name;
	private String hp;
}
