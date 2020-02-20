package com.msa.service;

import java.util.List;

import com.msa.document.Review;
import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ReviewerDTO;

public interface ReviewService {

    public Review addReview(Review review);
	public List<ReviewDTO> getReviewList();
	public List<ReviewDTO> getReviewList1();
	public List<Review> getReviewList2();
	public List<ReviewDTO> getReviewList3(String name);
    //public Optional<ReviewDTO> getReview(String id);
	public List<ReviewDTO> getReviewList4(ReviewDTO reviewdto);
	public ReviewDTO getReview(String id);
    public void delReview(String id);
	public ReviewerDTO getReviewer(String id);

	//public List<CommentDTO> getComments(String id);
	public List<CommentDTO> getComments2(String id);
	public List<CommentDTO> getMoreComments(String id, int PageNo);
	public int getCommentsTotalCount(String id);

}
