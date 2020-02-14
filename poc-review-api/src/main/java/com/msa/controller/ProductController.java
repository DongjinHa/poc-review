package com.msa.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.dto.ProductDTO;
import com.msa.repository.ProductRepository;
import com.msa.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
    @GetMapping("/getProductListByPrdSeq/{PrdSeq}")
    public ProductDTO getProductListByPrdSeq(@PathVariable String prdSeq) {	
        return productService.getProductListByPrdSeq(prdSeq);
    }
    

}
