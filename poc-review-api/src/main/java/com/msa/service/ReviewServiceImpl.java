package com.msa.service;

import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ReviewDetailDTO;
import com.msa.dto.ReviewerDTO;
import com.msa.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	private final MongoTemplate mongoTemplate;
	
	public ReviewServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        return reviewRepository.save(reviewDTO);
    }
    
	public List<ReviewDTO> getReviewList() {
		Query query = new Query()
				.addCriteria(Criteria.where("title").is("DongjinHa2"))
				.with(Sort.by(Sort.Order.desc("revrSeq")))
				.limit(2);
		return mongoTemplate.find(query, ReviewDTO.class);
	}
	
	public List<ReviewDTO> getReviewList1() {		//파워리뷰 출력을 위한 서비스
		Query query = new Query()
				.addCriteria(Criteria.where("bestFl").is("Y"))
				.with(Sort.by(Sort.Order.desc("hit")))
				.limit(15);
		return mongoTemplate.find(query, ReviewDTO.class);    
	} 
	
    public List<ReviewDTO> getReviewList2() {
        return reviewRepository.findAll();
    } 
    
	public List<ReviewDTO> getReviewList3(String title) {
		Query query = new Query()
				.addCriteria(Criteria.where("title").is("DongjinHa2"))
				.with(Sort.by(Sort.Order.desc("revrSeq")))
				.limit(2);
		return mongoTemplate.find(query, ReviewDTO.class);
	}
	
	public List<ReviewDTO> getReviewList4(ReviewDTO reviewDTO) {
		
		Query query = new Query();
		
		/* full text search 테스트*/
		/*
		if(StringUtils.isEmpty(reviewDTO.getKey())==false) {
			
			TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(reviewDTO.getKey()); 
			query = TextQuery.queryText(criteria);
			
		}else {
			query.addCriteria(Criteria.where("goodCnts").regex(reviewDTO.getKey()));
		}
		*/
		
		// 검색어
		query.addCriteria(Criteria.where("goodCnts").regex(reviewDTO.getKey()));   
		
		// A:포토리뷰, B:간단리뷰 
		if(StringUtils.isEmpty(reviewDTO.getReviewCl())==true) {  
			query.addCriteria(Criteria.where("reviewCl").is("A"));
		}else {
			query.addCriteria(Criteria.where("reviewCl").is(reviewDTO.getReviewCl()));
		}
		
		// 1:최신순, 2:조회순
		if(reviewDTO.getSort()==2) {
			query.with(Sort.by(Sort.Order.desc("hit")));
		} else { 
			query.with(Sort.by(Sort.Order.desc("regDate")));
		} 
		
		// 연령  
		// query.addCriteria(Criteria.where("uage").is(reviewDTO.getUage()));	
		
		// 페이징
		query.skip((reviewDTO.getPageNo()-1)*20)  
		 	 .limit(20);						 
		
		return mongoTemplate.find(query, ReviewDTO.class);
	}
	
	// 룩업 테스트
	public List<ReviewDTO> lookupReviewer() {
		
		LookupOperation lookUp = LookupOperation.newLookup()
				.from("reviewers")   // 묶이는 컬렉션 이름 reviewers
				.localField("reviewer_id")   // reviewers에서의 묶일 도큐먼트  
				.foreignField("_id")  // 묶는 review의 도큐먼트
				.as("ref");  // 별명은 ref
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("reviewCl").is("B")), // 조건
				lookUp) ;
			
		 List<ReviewDTO> results = mongoTemplate.aggregate(agg, "reviews", ReviewDTO.class).getMappedResults();
		 
		 System.out.println(results);
		 
		 return results;  
	}
	
    public ReviewDTO getReview(String id) {
    	ReviewDTO review = mongoTemplate.findById(new ObjectId(id), ReviewDTO.class,"reviews");
    	return review;
    }
    
    public void delReview(String id) {
    	reviewRepository.deleteById(id);
    }
    
    public ReviewerDTO getReviewer(String id) {
    	ReviewerDTO reviewer = mongoTemplate.findById(new ObjectId(id), ReviewerDTO.class,"reviewers");
    	return reviewer;
    }
    
	public List<CommentDTO> getComments(String id) {
		Query query = new Query()
				.with(Sort.by(Sort.Order.asc("regDate")))
				.limit(3);
		return mongoTemplate.find(query, CommentDTO.class);    
	}

	
	//DBRef test용
	public ReviewDetailDTO getReview1(String id) {
		//ReviewDetailDTO reviewDetail = mongoTemplate.findById(new ObjectId(id), ReviewDetailDTO.class,"reviews");
		Query query = new Query()
				.addCriteria(Criteria.where("review.$id").is(id)).limit(1);
		ReviewDetailDTO reviewDetail = (ReviewDetailDTO) mongoTemplate.find(query, ReviewDetailDTO.class);
		System.out.println(reviewDetail);
    	return reviewDetail;
	}
	public ReviewDetailDTO getReviewer1(String id) {
		ReviewDetailDTO reviewDetail = mongoTemplate.findById(new ObjectId(id), ReviewDetailDTO.class,"reviewers");
    	return reviewDetail;
	}
	public List<ReviewDetailDTO> getComments1(String id){
		Query query = new Query()
				.with(Sort.by(Sort.Order.asc("regDate")))
				.limit(3);
		return mongoTemplate.find(query, ReviewDetailDTO.class);    
				
			//	find(query, ReviewDetailDTO.class);    
	}
    
}
