package com.msa.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msa.document.Review;
import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ReviewerDTO;
import com.msa.service.ReviewService;
 
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
    
    @PostMapping("/addReview")
    public String addReview(@RequestBody Review _review) {
    	Review review = reviewService.addReview(_review);
        return "added id:" + review.get_id();
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
    public List<Review> getReviewList2() {
        return reviewService.getReviewList2();
    }   
    
    @GetMapping("/getReviewList3/{name}")
    public List<ReviewDTO> getReviewList3(@PathVariable String name) {
        return reviewService.getReviewList3(name);
    }  
    
    @GetMapping("/getReviewList4")
    public List<ReviewDTO> reviews(@RequestParam String mode
    							 , @RequestParam String reviewCl
    							 , @RequestParam int pageNo
    							 , @RequestParam int sort
    							 , @RequestParam String key
    							 , @RequestParam String uage
    							 , @RequestParam String skintypecd1
    							 , @RequestParam String skintypecd2
    							 , @RequestParam String skintypecd3
    							 , @RequestParam String skintypecd4
    							 , @RequestParam String skintypecd5
    							 , @RequestParam String skintypecd6
    							 , @RequestParam String skintypecd7
    							 , @RequestParam String skintypecdyn
    							 , @RequestParam String skinetcinfo1
    							 , @RequestParam String skinetcinfo2
    							 , @RequestParam String skinetcinfo3
    							 , @RequestParam String skinetcinfoyn
    							 , @RequestParam String skintonecd1
    							 , @RequestParam String skintonecd2
    							 , @RequestParam String skintonecd3
    							 , @RequestParam String skintonecdyn
    							 ) {
    	ReviewDTO reviewdto = new ReviewDTO();
    	reviewdto.setReviewCl(reviewCl);
    	reviewdto.setPageNo(pageNo);
    	reviewdto.setSort(sort);
    	reviewdto.setKey(key);
    	reviewdto.setUage(uage);
    	reviewdto.setSkintypecd1(skintypecd1);
    	reviewdto.setSkintypecd2(skintypecd2);
    	reviewdto.setSkintypecd3(skintypecd3);
    	reviewdto.setSkintypecd4(skintypecd4);
    	reviewdto.setSkintypecd5(skintypecd5);
    	reviewdto.setSkintypecd6(skintypecd6);
    	reviewdto.setSkintypecd7(skintypecd7);
    	reviewdto.setSkintypecdyn(skintypecdyn);
    	reviewdto.setSkinetcinfo1(skinetcinfo1);
    	reviewdto.setSkinetcinfo2(skinetcinfo2);
    	reviewdto.setSkinetcinfo3(skinetcinfo3);
    	reviewdto.setSkinetcinfoyn(skinetcinfoyn);
    	reviewdto.setSkintonecd1(skintonecd1);
    	reviewdto.setSkintonecd2(skintonecd2);
    	reviewdto.setSkintonecd3(skintonecd3);
    	reviewdto.setSkintonecdyn(skintonecdyn);
    	
        return reviewService.getReviewList4(reviewdto);
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
    	return reviewService.getComments2(id);
    	
    }

    @GetMapping("/Comments/{id}/{pageNo}")
    public List<CommentDTO> getMoreComments(@PathVariable String id, @PathVariable int pageNo) {
    	return reviewService.getMoreComments(id, pageNo);
    	
    }
    
    @GetMapping("/CommentsCount/{id}")
    public int getCommentsTotalCount(@PathVariable String id) {
    	return reviewService.getCommentsTotalCount(id);
    }
    
}
