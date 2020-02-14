package com.msa.service;

import java.util.List;
import com.msa.dto.ProductDTO;


public interface ProductService {
	public List<ProductDTO> getProductListByName(String prdNm);
	public ProductDTO getProductListByPrdSeq(String prdSeq);
	public List<ProductDTO> getProductList();
}
