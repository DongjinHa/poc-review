package com.msa.service;

import java.util.List;
import com.msa.dto.ReviewDTO;

public interface ReviewService {

    public ReviewDTO addReview(ReviewDTO reviewDTO);
	public List<ReviewDTO> getReviewList();
	public List<ReviewDTO> getReviewList2();
	public List<ReviewDTO> getReviewList3(String name);
    //public Optional<ReviewDTO> getReview(String id);
	public List<ReviewDTO> getReviewList4(ReviewDTO reviewdto);
	public ReviewDTO getReview(String id);
    public void delReview(String id);
    
}
