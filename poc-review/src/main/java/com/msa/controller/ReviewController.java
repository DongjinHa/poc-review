package com.msa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
}
