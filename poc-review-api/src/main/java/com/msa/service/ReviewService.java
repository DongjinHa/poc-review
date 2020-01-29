package com.msa.service;

import java.util.List;
import java.util.Optional;

import com.msa.dto.ReviewDTO;

public interface ReviewService {

    public ReviewDTO addReview(ReviewDTO reviewDTO);
	public List<ReviewDTO> getReviewList();
	public List<ReviewDTO> getReviewList2();
	public List<ReviewDTO> getReviewList3(String name);
    public Optional<ReviewDTO> getReview(String id);
    public void delReview(String id);
    
}
