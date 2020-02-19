package com.msa.service;

import java.util.List;
import javax.annotation.Resource;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators.ToObjectId;
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
		
		query.addCriteria(Criteria.where("goodCnts").regex(reviewDTO.getKey()));
		
		if(StringUtils.isEmpty(reviewDTO.getReviewCl())==true) {
			query.addCriteria(Criteria.where("reviewCl").is("A"));
		}else {
			query.addCriteria(Criteria.where("reviewCl").is(reviewDTO.getReviewCl()));
		}
		
		if(reviewDTO.getSort()==2) { // 조회순
			query.with(Sort.by(Sort.Order.desc("hit")));
		} else { // 최신순
			query.with(Sort.by(Sort.Order.desc("regDate")));
		} 
			
		query.skip((reviewDTO.getPageNo()-1)*20)  // 페이징
		 	 .limit(20);						  // 페이징
		
		return mongoTemplate.find(query, ReviewDTO.class);
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
				.addCriteria(Criteria.where("review_id").is(id))	//조건 추가
				.with(Sort.by(Sort.Order.asc("regDate")))
				.limit(3);
		List<CommentDTO> comments =  mongoTemplate.find(query, CommentDTO.class);    
		
		//코멘트를 단 유저의 정보 출력을 위하여 로직 추가
		for(CommentDTO comment : comments) {
			ReviewerDTO commenter = mongoTemplate.findById(new ObjectId(comment.getReviewer_id()), ReviewerDTO.class,"reviewers");
			comment.setReviewer(commenter);
		}
		System.out.print(comments);
		
		return comments;
	}
	
	//댓글 페이징 기능 TEST
	public List<CommentDTO> getComments2(String id, int pageNo) {
		Query query = new Query()
				.addCriteria(Criteria.where("review_id").is(id)) // 해당하는 리뷰의 글을
				.with(Sort.by(Sort.Order.asc("regDate"))) // 등록 오름차순으로
				.skip((pageNo-1)*3) // 페이지-1개 * 3건씩 건너 뛰고 
				.limit(3); // 3개만 조회
		List<CommentDTO> comments =  mongoTemplate.find(query, CommentDTO.class);    
		
		for(CommentDTO comment : comments) {
			ReviewerDTO commenter = mongoTemplate.findById(new ObjectId(comment.getReviewer_id()), ReviewerDTO.class,"reviewers");
			comment.setReviewer(commenter);
		}
		
		return comments;
	}

	
	//DBRef test용
	public ReviewDetailDTO getReview1(String id) {	//review+reviewer 정보
		LookupOperation lookup = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")
				.foreignField("_id").as("reviewer");
		Aggregation aggregation = Aggregation.newAggregation(
				lookup,
				Aggregation.match(
						Criteria.where("_id").is(id)
				)
		);
		AggregationResults<ReviewDetailDTO> groupResults = mongoTemplate.aggregate(aggregation,"reviews", ReviewDetailDTO.class);
    	return groupResults.getUniqueMappedResult();
	}
	
	/*
	public List<CommentDTO> getComments1(ReviewDetailDTO Review){	//review에 대한 comment 정보
		LookupOperation lookup = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")
				.foreignField(("_id")).as("commenter");
		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.match(
						Criteria.where("review_id").is(Review.get_id())
				)
				,lookup
		);
		AggregationResults<CommentDTO> groupResults = mongoTemplate.aggregate(aggregation,"comments", CommentDTO.class);
		System.out.println(groupResults.getMappedResults());
    	return groupResults.getMappedResults();
	}
	*/
    
}
