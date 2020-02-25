package com.example.demo;

import java.util.List;

import com.example.demo.ProductDTO;

public interface ProductService {
	
	public ProductDTO getProductListByPrdSeq(String prdSeq);
	public List<ProductDTO> getProductListByPrdNm(String prdNm);

}
