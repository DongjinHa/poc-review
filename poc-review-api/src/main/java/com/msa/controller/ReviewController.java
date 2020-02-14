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

import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ReviewDetailDTO;
import com.msa.dto.ReviewerDTO;
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

    @GetMapping("/getReviewList1")
    public List<ReviewDTO> getReviewList1() {
        return reviewService.getReviewList1();
    }   
    
    @GetMapping("/getReviewList2")
    public List<ReviewDTO> getReviewList2() {
        return reviewService.getReviewList2();
    }   
    
    @GetMapping("/getReviewList3/{name}")
    public List<ReviewDTO> getReviewList3(@PathVariable String name) {
        return reviewService.getReviewList3(name);
    }  
    
    @GetMapping("/getReview/{id}")
    public ReviewDTO getReview(@PathVariable String id) {	
        return reviewService.getReview(id);
    }

    @DeleteMapping("/delReview/{id}")
    public String delReview(@PathVariable String id) {
    	reviewService.delReview(id);
        return "deleted id:" + id;
    }
    
    @GetMapping("/Reviewer/{id}")
    public ReviewerDTO getReviewer(@PathVariable String id) {
    	return reviewService.getReviewer(id);
    	
    }
    
    @GetMapping("/Comments/{id}")
    public List<CommentDTO> getComments(@PathVariable String id) {
    	return reviewService.getComments(id);
    	
    }
    
    @GetMapping("/getReview1/{id}")
    public ReviewDetailDTO getReview1(@PathVariable String id) {	
    	
        return reviewService.getReview1(id);
    }

    @GetMapping("/Reviewer1/{id}")
    public ReviewDetailDTO getReviewer1(@PathVariable String id) {
    	return reviewService.getReviewer1(id);
    	
    }
    
    @GetMapping("/Comments1/{id}")
    public List<ReviewDetailDTO> getComments1(@PathVariable String id) {
    	return reviewService.getComments1(id);
    	
    }
}
