package com.msa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.dto.ReviewDTO;
import com.msa.service.ReviewService;
 
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
    
    @PostMapping("/addReview")
    public String addReview(@RequestBody ReviewDTO _reviewDTO) {
    	ReviewDTO reviewDTO = reviewService.addReview(_reviewDTO);
        return "added id:" + reviewDTO.get_id();
    }

    @GetMapping("/getReviewList")
    public List<ReviewDTO> getReviewList() {
        return reviewService.getReviewList();
    }    
    
    @GetMapping("/getReviewList2")
    public List<ReviewDTO> getReviewList2() {
        return reviewService.getReviewList2();
    }    
    
    @GetMapping("/getReview/{id}")
    public Optional<ReviewDTO> getReview(@PathVariable String id) {
        return reviewService.getReview(id);
    }

    @DeleteMapping("/delReview/{id}")
    public String delReview(@PathVariable String id) {
    	reviewService.delReview(id);
        return "deleted id:" + id;
    }
    
}
