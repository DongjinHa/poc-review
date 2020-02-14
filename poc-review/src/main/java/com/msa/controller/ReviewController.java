package com.msa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.msa.dto.ReviewDTO;
import com.msa.dto.ProductDTO;

@Controller
@RequestMapping("/review")
public class ReviewController {
	/* PocReviewApplication.java에 rootUri 설정과 함께 Bean으로 등록하면서 주석 및 수정
	배포시 수정을 간편하게 하기 위해 상수로 선언
	final String BASE_URL = "http://localhost:9091/review";
	 */
	
	@Autowired
	RestTemplate restTemplate;	//PocReviewApplication.java에서 Bean 등록
	
    @GetMapping("/getReviewList")
    public String getReviewList(Model model) {
    	model.addAttribute("Review", null);
        return "/review/review";
    }

	@GetMapping("/productList") 
	public String productList(Model model){
		
		ResponseEntity<List<ProductDTO>> productList = restTemplate.exchange("/getProductList", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {});
        List<ProductDTO> result= productList.getBody();
		
        model.addAttribute("Product", result);
		return "/product/productListMD";
	}
    
    @GetMapping("/getReviewList2")
    public String getReviewList2(Model model) {
        /* PocReviewApplication.java에 rootUri 설정과 함께 Bean으로 등록하면서 주석 및 수정
    	RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange(BASE_URL+"/getReviewList2", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        */
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("/getReviewList2", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
     
        
        model.addAttribute("Review", result);
        return "apitest";
    }    
    
    @GetMapping("/getReviewList3")
    public String getReviewList3(Model model) {
    	/* PocReviewApplication.java에 rootUri 설정과 함께 Bean으로 등록하면서 주석 및 수정
    	RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange(BASE_URL+"/getReviewList3/yulimkang", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        */
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("/getReviewList3/yulimkang", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        
        model.addAttribute("Review", result);
        return "apitest";
    }    
    
    //review detail 구현 테스트용
    @GetMapping("/reviewDetail/{id}")
    public String productDetailTest(Model model,@PathVariable("id") String _id) {
    	/* PocReviewApplication.java에 rootUri 설정과 함께 Bean으로 등록하면서 주석 및 수정
    	RestTemplate restTemplate = new RestTemplate();
    	review = restTemplate.getForObject(BASE_sURL+"/getReview/"+_id, ReviewDTO.class);
    	*/
    	ReviewDTO review = restTemplate.getForObject("/getReview/"+_id, ReviewDTO.class);
    	model.addAttribute("Review", review);
    	
    	
    	//ProductDTO product = restTemplate.getForObject("/getProductListByPrdSeq/"+review.getPrdSeq(), ProductDTO.class);
    	
    	ProductDTO product = restTemplate.getForObject("http://localhost:9092/getProductListByPrdSeq/"+review.getPrdSeq(), ProductDTO.class);
    	
    	model.addAttribute("Product", product);
        
    	
    	return "apitest1";
    }
}
