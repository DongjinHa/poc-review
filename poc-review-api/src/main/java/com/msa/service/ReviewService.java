package com.msa.service;

import java.util.List;
import org.bson.types.ObjectId;
import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ReviewerDTO;
import com.msa.dto.ReviewDetailDTO;

public interface ReviewService {

    public ReviewDTO addReview(ReviewDTO reviewDTO);
	public List<ReviewDTO> getReviewList();
	public List<ReviewDTO> getReviewList1();
	public List<ReviewDTO> getReviewList2();
	public List<ReviewDTO> getReviewList3(String name);
    //public Optional<ReviewDTO> getReview(String id);
	public List<ReviewDTO> getReviewList4(ReviewDTO reviewdto);
	public ReviewDTO getReview(String id);
    public void delReview(String id);
	public ReviewerDTO getReviewer(String id);
	public List<CommentDTO> getComments(String id);
    
	//DBRef test용
	public ReviewDetailDTO getReview1(String id);
	public ReviewDetailDTO getReviewer1(String id);
	public List<ReviewDetailDTO> getComments1(String id);
	public List<ReviewDTO> lookupReviewer();
}
