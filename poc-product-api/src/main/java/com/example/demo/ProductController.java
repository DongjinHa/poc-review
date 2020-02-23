package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ProductDTO;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getProductListByPrdSeq/{prdSeq}")
    public ProductDTO getProductListByPrdSeq(@PathVariable String prdSeq) {	
        return productService.getProductListByPrdSeq(prdSeq);
    }
	
	@GetMapping("/getProductListByPrdNm/{prdNm}")
    public List<ProductDTO> getProductListByPrdNm(@PathVariable String prdNm) {	
        return productService.getProductListByPrdNm(prdNm);
    }
	
}
