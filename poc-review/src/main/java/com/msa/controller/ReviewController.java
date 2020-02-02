package com.msa.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.msa.dto.ReviewDTO;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
    @GetMapping("/getReviewList")
    public String getReviewList(Model model) {
    	model.addAttribute("user", null);
        return "/review/review";
    }

	@GetMapping("/productList") 
	public String productList(Model model){
		return "/product/productList";
	}
    
    @GetMapping("/getReviewList2")
    public String getReviewList2(Model model) {
    	
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("http://localhost:9091/review/getReviewList2", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        
        model.addAttribute("user", result);
        return "apitest";
    }    
    
    @GetMapping("/getReviewList3")
    public String getReviewList3(Model model) {
    	
    	RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("http://localhost:9091/review/getReviewList3/yulimkang", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        
        model.addAttribute("user", result);
        return "apitest";
    }    
    
}
