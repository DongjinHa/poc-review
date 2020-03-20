package com.msa.controller;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.msa.CommonUtils;
import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ProductDTO;
import com.msa.dto.ReviewerDTO;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	RestTemplate reviewTemplate;

	@Autowired
	RestTemplate productTemplate;
	
	@Autowired 
	WebClient.Builder builder;	//PocReviewApplication.java에서 Bean 등록 및 baseUrl 설정

    @GetMapping("/")
    public String initMain(Model model) {
    	
        return "./review/review";
    }	
	
    @PostMapping("/allreview")
    public String getReviewList(Model model, @ModelAttribute("reviewDTO") ReviewDTO reviewDTO) throws JsonProcessingException {

    	// 검색조건이 있으면 상품정보 조회
    	String key = reviewDTO.getKey();
    	
    	// 상품정보 키워드 조회
    	if (key != null && !"".equals(key)) {
    		ResponseEntity<List<ProductDTO>> _productResponse = productTemplate.exchange("/getProductListByPrdNm/"+key, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {});
    		List<ProductDTO> _productList = _productResponse.getBody();
    		
            List<String> _prdSeqList = Lists.transform(_productList.stream()
    									                .collect( Collectors.toList() ), new Function<ProductDTO, String>() {
            	@Override
            	public String apply(ProductDTO productDTO) {
            		return productDTO.getPrdSeq();
            	}
            }); 
    		
            if (_prdSeqList != null && _prdSeqList.size() > 0) {
            	reviewDTO.setPrdSeqList(_prdSeqList);
            }
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonStr = mapper.writeValueAsString(reviewDTO);
    	HttpEntity<?> reviewReqEntity = CommonUtils.apiClientHttpEntity(MediaType.APPLICATION_JSON_VALUE, jsonStr);
    	
    	ResponseEntity<List<ReviewDTO>> reviewResponse = reviewTemplate.exchange("/review/allreview", HttpMethod.POST, reviewReqEntity, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> reviewList = reviewResponse.getBody();

        String reviewTotCnt = "0";
        int pageno = reviewDTO.getPageNo();
        if (reviewList != null && reviewList.size() > 0 && pageno == 1) {
        	ResponseEntity<String> reviewCntResponse = reviewTemplate.exchange("/review/allreview-totcnt", HttpMethod.POST, reviewReqEntity, new ParameterizedTypeReference<String>() {});
        	reviewTotCnt = reviewCntResponse.getBody(); 
        }
        model.addAttribute("reviewTotCnt", reviewTotCnt);

        // 상품 API 호출을 위한 상품코드 재추출
        List<String> prdSeqList = Lists.transform(reviewList.stream()
									                .filter( CommonUtils.distinctByKey(review -> review.getPrdSeq()) )
									                .collect( Collectors.toList() ), new Function<ReviewDTO, String>() {
        	@Override
        	public String apply(ReviewDTO reviewDTO) {
        		return reviewDTO.getPrdSeq();
        	}
        }); 
        
        jsonStr = mapper.writeValueAsString(prdSeqList);
    	HttpEntity<?> prdReqEntity = CommonUtils.apiClientHttpEntity(MediaType.APPLICATION_JSON_VALUE, jsonStr);
    	
    	ResponseEntity<List<ProductDTO>> productResponse = productTemplate.exchange("/product", HttpMethod.POST, prdReqEntity, new ParameterizedTypeReference<List<ProductDTO>>() {});
    	List<ProductDTO> productList = productResponse.getBody();

        for(ProductDTO product : productList) {
        	for(ReviewDTO review : reviewList) {
        		if (review.getPrdSeq().equals(product.getPrdSeq())) {
        			review.setProduct(product);
        		}
        	}
        }
        model.addAttribute("reviewList", reviewList);
        
        String mode = reviewDTO.getMode();
        if ("more".equals(mode)) {
        	return "./review/moreReviewList";
        }else {
        	return "./review/allReviewList";
        }
        
    }

	@GetMapping("/productList") 
	public String productList(Model model){
		
		ResponseEntity<List<ProductDTO>> productList = reviewTemplate.exchange("/review/getProductList", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {});
        List<ProductDTO> result= productList.getBody();
		
        model.addAttribute("Product", result);
		return "./product/productListMD";
	}
  
	@GetMapping("/getReviewList1") //파워리뷰 - bestFL=Y & hit count desc로 15건 출력하도록 임시 api 사용
	public String getReviewList1(Model model) throws JsonProcessingException {
		/*	//WebClient ver.
		WebClient webClient = builder.build();
	    List<ReviewDTO> result = webClient.get().uri("/getReviewList1")
				.retrieve() // 응답값을 가져옴 
				.bodyToFlux(ReviewDTO.class)
				.collectList().block();
	    model.addAttribute("Review",result);
		return "apitest";	*/
		
			//RestTemplate ver.
		ResponseEntity<List<ReviewDTO>> PowerReviewResponse = reviewTemplate.exchange("/review/getReviewList1", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> powerReviews= PowerReviewResponse.getBody();
        
        // 상품 API 호출을 위한 상품코드 재추출
        List<String> prdSeqList = Lists.transform(powerReviews.stream()
									                .filter( CommonUtils.distinctByKey(review -> review.getPrdSeq()) )
									                .collect( Collectors.toList() ), new Function<ReviewDTO, String>() {
        	@Override
        	public String apply(ReviewDTO reviewDTO) {
        		return reviewDTO.getPrdSeq();
        	}
        }); 
        
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonStr = mapper.writeValueAsString(prdSeqList);
    	HttpEntity<?> prdReqEntity = CommonUtils.apiClientHttpEntity(MediaType.APPLICATION_JSON_VALUE, jsonStr);
    	
    	ResponseEntity<List<ProductDTO>> productResponse = productTemplate.exchange("/product", HttpMethod.POST, prdReqEntity, new ParameterizedTypeReference<List<ProductDTO>>() {});
    	List<ProductDTO> productList = productResponse.getBody();

        for(ProductDTO product : productList) {
        	for(ReviewDTO review : powerReviews) {
        		if (review.getPrdSeq().equals(product.getPrdSeq())) {
        			review.setProduct(product);
        		}
        	}
        }
        model.addAttribute("powerReview", powerReviews);
        
		return "powerReview";
	}
	
	
    @GetMapping("/getReviewList2")	//리뷰 전체 출력
    public String getReviewList2(Model model) {
    	/*	//WebClient ver.
    	WebClient webClient = builder.build();
		List<ReviewDTO> result = webClient.get().uri("/getReviewList2")
				.retrieve() // 응답값을 가져옴 
				.bodyToFlux(ReviewDTO.class)
				.collectList().block();
		
	    model.addAttribute("Review",result);
		return "apitest";	*/
    	
    		//RestTemplate ver.
        ResponseEntity<List<ReviewDTO>> reviewResponse = reviewTemplate.exchange("/review/getReviewList2", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        
        model.addAttribute("Review", result);
        return "apitest";
        		
    }    
    
    @GetMapping("/getReviewList3")
    public String getReviewList3(Model model) {
    	/* PocReviewApplication.java에 rootUri 설정과 함께 Bean으로 등록하면서 주석 및 수정
    	RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange(BASE_URL+"/getReviewList3/yulimkang", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        */
        ResponseEntity<List<ReviewDTO>> reviewResponse = reviewTemplate.exchange("/review/getReviewList3/yulimkang", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        
        model.addAttribute("Review", result);
        return "apitest";
    }    
    
    //페이징테스트
    @GetMapping("/reviewList/{page}")
    public String reviewList(Model model,@PathVariable("page") int page) {
        ResponseEntity<List<ReviewDTO>> reviewResponse = reviewTemplate.exchange("/review/getReviewList4/"+ page, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        
        model.addAttribute("Review", result);
        return "apitest";
    }    
    
    @GetMapping("/reviewDetail/{id}")	//1개 리뷰에 대한 출력
    public String reviewDetail(Model model,@PathVariable("id") String _id) throws Exception { 	
    	/* //WebClient ver.
	   	WebClient webClient = builder.build();
	   	ReviewDTO result = webClient.get().uri("/getReview/"+_id)
					.retrieve() // 응답값을 가져옴 
					.bodyToMono(ReviewDTO.class)
					.block();  
		model.addAttribute("ReviewData",result);
		return "apitest1";	*/
		
    	 //RestTemplate ver.

    	ReviewDTO review = reviewTemplate.getForObject("/review/getReview/"+_id, ReviewDTO.class);
    	model.addAttribute("ReviewData", review);
    	
    	try {
    	ReviewerDTO reviewer = reviewTemplate.getForObject("/review/Reviewer/"+review.getReviewer_id(),ReviewerDTO.class);
    	model.addAttribute("ReviewerData",reviewer);
    	
    	 int currentYear  = Calendar.getInstance().get(Calendar.YEAR);
    	 int age = ((currentYear-(Integer.parseInt(reviewer.getBirthDay().substring(0,4))))/10)*10;
    	 model.addAttribute("reviewerAge", age);	//연령대 구하기    	 
    	 
    	ResponseEntity<List<CommentDTO>> commentsResponse = reviewTemplate.exchange("/review/Comments/"+review.get_id()
    								, HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentDTO>>() {});
        List<CommentDTO> comments= commentsResponse.getBody();
    	model.addAttribute("CommentData",comments);
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	ProductDTO product = productTemplate.getForObject("/getProductListByPrdSeq/"+review.getPrdSeq(), ProductDTO.class);
    	model.addAttribute("Product", product);
    	
    	// 댓글 페이징 버튼 컨트롤 
    	ResponseEntity<String> countResponse = reviewTemplate.exchange("/review/CommentsCount/"+ _id, HttpMethod.GET, null, 
    											new ParameterizedTypeReference<String>() {}); 
    	String count = countResponse.getBody(); 
    	model.addAttribute("Count", count);
    	
    	return "detailTest";
    }
    
    // 댓글 페이징 
    @GetMapping("reviewDetail/{id}/getMoreComments/{pageNo}")
    public String reviewDetail(Model model,@PathVariable("id") String _id, @PathVariable("pageNo") String pageNo) {	
    	
    	ResponseEntity<List<CommentDTO>> commentsResponse = reviewTemplate.exchange("/review/Comments/"+ _id 
    								+"/" + pageNo
    								, HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentDTO>>() {});
        List<CommentDTO> comments= commentsResponse.getBody();
    	model.addAttribute("CommentData", comments);

    	return "detailComment";
    }
    
	/*
	 * @GetMapping("reviewDetail/{id}/getCommentsCount") public @ResponseBody String
	 * getCommentsCount(Model model, @PathVariable("id") String _id) {
	 * 
	 * ResponseEntity<String> countResponse =
	 * restTemplate.exchange("/CommentsCount/"+ _id, HttpMethod.GET, null, new
	 * ParameterizedTypeReference<String>() {}); String count =
	 * countResponse.getBody(); model.addAttribute("Count", count);
	 * System.out.println(count);
	 * 
	 * return count; }
	 */
    
}