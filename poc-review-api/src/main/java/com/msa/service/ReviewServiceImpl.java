package com.msa.service;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
=======
import java.util.Calendar;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
>>>>>>> master

import org.apache.logging.log4j.util.Strings;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.CountOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.msa.document.Comment;
import com.msa.document.Review;
import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
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
	
	public List<ReviewDTO> getReviewList(ReviewDTO reviewDTO) {
		
		List<Criteria> criteriaList = new ArrayList<>();
		List<Criteria> criteriaTargetList = new ArrayList<>();
		Criteria criteriaTargetForKey = new Criteria();
		
		// A:포토리뷰, B:간단리뷰 
		criteriaList.add(Criteria.where("reviewCl").is(reviewDTO.getReviewCl()));
		
		// 연령  
        int currentYear  = Calendar.getInstance().get(Calendar.YEAR);
        String uage = reviewDTO.getUage();
        
        switch(uage) {
        case "all" :
        	break;
        case "10" :
        	criteriaList.add(Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-18)+"0101"));
        	criteriaList.add(Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-9)+"1231"));
			break;
        case "20" :
        	criteriaList.add(Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-28)+"0101"));
        	criteriaList.add(Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-19)+"1231"));
			break;
        case "30" :
        	criteriaList.add(Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-38)+"0101"));
        	criteriaList.add(Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-29)+"1231"));
			break;
        case "40" :
        	criteriaList.add(Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-48)+"0101"));
        	criteriaList.add(Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-79)+"1231"));
			break;
        }
        
		// 피부타입
		if(reviewDTO.getSkintypecdyn().equals("Y")) {
			criteriaList.add(Criteria.where("reviewer.skinTypeCd").in(reviewDTO.getSkintypecd1(),reviewDTO.getSkintypecd2(),reviewDTO.getSkintypecd3()
					,reviewDTO.getSkintypecd4(),reviewDTO.getSkintypecd5(),reviewDTO.getSkintypecd6(),reviewDTO.getSkintypecd7()));
		}
		
		// 피부밝기
		if(reviewDTO.getSkinetcinfoyn().equals("Y")) {
			criteriaList.add(Criteria.where("reviewer.skinEtcInfo").in(reviewDTO.getSkinetcinfo1(),reviewDTO.getSkinetcinfo2(),reviewDTO.getSkinetcinfo3()));
		}
		
		// 피부톤
		if(reviewDTO.getSkintonecdyn().equals("Y")) {
			criteriaList.add(Criteria.where("reviewer.skinToneCd").in(reviewDTO.getSkintonecd1(),reviewDTO.getSkintonecd2(),reviewDTO.getSkintonecd3()));
		}
		
		// 상품코드 검색(키워드로 상품정보를 조회해온 경우)
		if(reviewDTO.getPrdSeqList() != null && reviewDTO.getPrdSeqList().size() > 0) {
			criteriaTargetList.add(Criteria.where("prdSeq").in(reviewDTO.getPrdSeqList()));
		}

		// 키워드 검색
		if(Strings.isEmpty(reviewDTO.getKey())==false) {
			criteriaTargetList.add(Criteria.where("goodCnts").regex(reviewDTO.getKey()));
		}
	    
		// 키워드 검색대상이 있으면 수행
        if(criteriaTargetList.size() > 0) {
        	criteriaList.add(criteriaTargetForKey.orOperator(criteriaTargetList.toArray(new Criteria[criteriaTargetList.size()])));	
        }
        
		Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
		
		MatchOperation match = Aggregation.match(criteria);
		LookupOperation lookUp = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")   	//1. 묶을 컬렉션 이름은 reviewers, 대상 도큐먼트는 같은 이름인 reviewer_id
				.foreignField("_id").as("reviewer");  			//2. 조회할 컬렉션에서 해당 reviews 컬렉션이 묶일 도큐먼트 이름은 _id, 별명은 reviewer

		Aggregation aggregation;
		// 총건수 조회여부에 따라수행
		if(reviewDTO.getTotCntYn() != null && "Y".equals(reviewDTO.getTotCntYn())){
			
			CountOperation count = Aggregation.count().as("totCnt");

			aggregation = Aggregation.newAggregation(lookUp, match, count);
			
		}else {
			
			ProjectionOperation project = Aggregation.project().andExclude("reviewer_id");
			SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "regDate");
		//	SkipOperation skip = Aggregation.skip((reviewDTO.getPageNo()-1)*20);
			SkipOperation skip = Aggregation.skip(1*20);
			LimitOperation limit = Aggregation.limit(20);
			
			aggregation = Aggregation.newAggregation(lookUp, match, project, sort, skip, limit);	
		}
		
	    AggregationResults<ReviewDTO> result = mongoTemplate.aggregate(aggregation, Review.class, ReviewDTO.class);
	    
		return result.getMappedResults(); 
	}
	
	public List<ReviewDTO> getReviewList1() {		//파워리뷰 출력을 위한 서비스
		/*Query query = new Query()
				.addCriteria(Criteria.where("bestFl").is("Y"))
				.addCriteria(Criteria.where("reviewCl").is("A"))
				.with(Sort.by(Sort.Order.desc("hit")))
				.limit(15);
		return mongoTemplate.find(query, ReviewDTO.class);    */
		
		//lookup 사용ver.
		Criteria criteria = new Criteria();
		criteria.andOperator(
				Criteria.where("bestFl").is("Y"),
				Criteria.where("reviewCl").is("A")	//포토리뷰만 출력하도록 임시조치
		);
		
		MatchOperation match = Aggregation.match(criteria);
		SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "regDate");
		LimitOperation limit = Aggregation.limit(15);
		
		LookupOperation lookUp = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")
				.foreignField("_id").as("reviewer"); 
		
		
		Aggregation aggregation = Aggregation.newAggregation(match, lookUp, sort, limit);
		AggregationResults<ReviewDTO> result = mongoTemplate.aggregate(aggregation, Review.class, ReviewDTO.class);
	    
		return result.getMappedResults();  
	} 
	
    public List<Review> getReviewList2() {
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
		
		//Query query = new Query();
		
		/* full text search 테스트*/
		/*
		if(StringUtils.isEmpty(reviewDTO.getKey())==false) {
			
			TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(reviewDTO.getKey()); 
			query = TextQuery.queryText(criteria);
			
		}else {
			query.addCriteria(Criteria.where("goodCnts").regex(reviewDTO.getKey()));
		}
		*/
		
		/* 리뷰리스트 조회  (old) 
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
		*/
		
		// lookup 방식
		Criteria criteriaReviewCl = new Criteria();
		Criteria criteriaAge1 = new Criteria();
		Criteria criteriaAge2 = new Criteria();
		Criteria criteriaSkintype = new Criteria();
		Criteria criteriaSkinetc = new Criteria();
		Criteria criteriaSkintone = new Criteria();
		Criteria criteriaKey = new Criteria();
		
		// A:포토리뷰, B:간단리뷰 
		criteriaReviewCl = Criteria.where("reviewCl").is(reviewDTO.getReviewCl());
		
		// 연령  
        int currentYear  = Calendar.getInstance().get(Calendar.YEAR);
        String uage = reviewDTO.getUage();
        
        switch(uage) {
        case "all" :
        	break;
        case "10" :
        	criteriaAge1 = Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-18)+"0101");
			criteriaAge2 = Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-9)+"1231");
			break;
        case "20" :
        	criteriaAge1 = Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-28)+"0101");
			criteriaAge2 = Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-19)+"1231");
			break;
        case "30" :
        	criteriaAge1 = Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-38)+"0101");
			criteriaAge2 = Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-29)+"1231");
			break;
        case "40" :
        	criteriaAge1 = Criteria.where("reviewer.birthDay").gte(Integer.toString(currentYear-48)+"0101");
			criteriaAge2 = Criteria.where("reviewer.birthDay").lte(Integer.toString(currentYear-79)+"1231");
			break;
        }
        
		// 피부타입
		if(reviewDTO.getSkintypecdyn().equals("Y")) {
			criteriaSkintype = Criteria.where("reviewer.skinTypeCd").in(reviewDTO.getSkintypecd1(),reviewDTO.getSkintypecd2(),reviewDTO.getSkintypecd3()
					,reviewDTO.getSkintypecd4(),reviewDTO.getSkintypecd5(),reviewDTO.getSkintypecd6(),reviewDTO.getSkintypecd7());
		}
		
		// 피부밝기
		if(reviewDTO.getSkinetcinfoyn().equals("Y")) {
			criteriaSkinetc = Criteria.where("reviewer.skinEtcInfo").in(reviewDTO.getSkinetcinfo1(),reviewDTO.getSkinetcinfo2(),reviewDTO.getSkinetcinfo3());
		}
		
		// 피부톤
		if(reviewDTO.getSkintonecdyn().equals("Y")) {
			criteriaSkintone = Criteria.where("reviewer.skinToneCd").in(reviewDTO.getSkintonecd1(),reviewDTO.getSkintonecd2(),reviewDTO.getSkintonecd3());
		}
		
		// 검색어
		if(Strings.isEmpty(reviewDTO.getKey())==false) {
			criteriaKey = Criteria.where("goodCnts").regex(reviewDTO.getKey()); 
		}
		
		Criteria criteria = new Criteria().andOperator(criteriaReviewCl, criteriaAge1, criteriaAge2, criteriaSkintype, criteriaSkinetc, criteriaSkintone, criteriaKey);
		
		MatchOperation match = Aggregation.match(criteria);
		LookupOperation lookUp = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")   	//1. 묶을 컬렉션 이름은 reviewers, 대상 도큐먼트는 같은 이름인 reviewer_id
				.foreignField("_id").as("reviewer");  			//2. 조회할 컬렉션에서 해당 reviews 컬렉션이 묶일 도큐먼트 이름은 _id, 별명은 reviewer

		ProjectionOperation project = Aggregation.project().andExclude("reviewer_id");
	
		// 1:최신순, 2:조회순
		SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "regDate");
		if(reviewDTO.getSort()==2) {
			sort = Aggregation.sort(Sort.Direction.DESC, "hit");
		}
		
		SkipOperation skip = Aggregation.skip((reviewDTO.getPageNo())*20);
		
		LimitOperation limit = Aggregation.limit(20);

		Aggregation aggregation = Aggregation.newAggregation(lookUp, match, project, sort, skip, limit);
	
	    AggregationResults<ReviewDTO> result = mongoTemplate.aggregate(aggregation, Review.class, ReviewDTO.class);
	    
		return result.getMappedResults();  
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
    
    //댓글 가져오기 (old)
//	public List<CommentDTO> getComments(String id) {
//		Query query = new Query()
//				.addCriteria(Criteria.where("review_id").is(id))	//조건 추가
//				.with(Sort.by(Sort.Order.asc("regDate")))
//				.limit(3);
//		List<CommentDTO> comments =  mongoTemplate.find(query, CommentDTO.class);    
//		
//		//코멘트를 단 유저의 정보 출력을 위하여 로직 추가
//		for(CommentDTO comment : comments) {
//			ReviewerDTO commenter = mongoTemplate.findById(new ObjectId(comment.getReviewer_id()), ReviewerDTO.class,"reviewers");
//			comment.setReviewer(commenter);
//		}
//		System.out.print(comments);
//		
//		return comments;
//	}
	
	//댓글 가져오기 (new, ObjectId)
	public List<CommentDTO> getComments2(String id) {

		LookupOperation lookUp = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")  
				.foreignField("_id").as("reviewer");	

		//ProjectionOperation project = Aggregation.project().andExclude("review_id");
		SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "regDate");
		LimitOperation limit = Aggregation.limit(3);
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(
																		Criteria.where("review_id").is(new ObjectId(id))
																		), lookUp, sort, limit);
	    AggregationResults<CommentDTO> result = mongoTemplate.aggregate(aggregation, Comment.class, CommentDTO.class);
	    
	    List<CommentDTO> comments = result.getMappedResults(); 
		return comments;
	}
	
	//댓글 페이징 (new)
	public List<CommentDTO> getMoreComments(String id, int pageNo) {
		
		LookupOperation lookUp = LookupOperation.newLookup()
				.from("reviewers").localField("reviewer_id")  
				.foreignField("_id").as("reviewer");  	

		SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "regDate");
		SkipOperation skip = Aggregation.skip((pageNo - 1) * 3);
		LimitOperation limit = Aggregation.limit(3);
		Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(
																	Criteria.where("review_id").is(new ObjectId(id))
																	),lookUp, sort, skip, limit);
	    AggregationResults<CommentDTO> result = mongoTemplate.aggregate(aggregation, Comment.class, CommentDTO.class);
	    
	    List<CommentDTO> comments = result.getMappedResults();
	    
		return comments;
	}
	
	/* 댓글 페이징 (old)
	public List<CommentDTO> getMoreComments2(String id, int pageNo) {
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
	} */
	
	// 댓글 더보기 버튼 제어를 위한 댓글 전체 수 구하기 
	public int getCommentsTotalCount(String id) {
		Query query = new Query()
				.addCriteria(Criteria.where("review_id").is(new ObjectId(id)));
		int count = mongoTemplate.find(query, CommentDTO.class).size();
		return count;
	}
	
}
