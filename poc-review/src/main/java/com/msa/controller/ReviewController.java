package com.msa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ProductDTO;
import com.msa.dto.ReviewerDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/review")
public class ReviewController {
	/* PocReviewApplication.java에 rootUri 설정과 함께 Bean으로 등록하면서 주석 및 수정
	배포시 수정을 간편하게 하기 위해 상수로 선언
	final String BASE_URL = "http://localhost:9091/review";
	 */
	
	@Autowired
	RestTemplate restTemplate;	//PocReviewApplication.java에서 Bean 등록 및 rootUri 설정
	
	@Autowired 
	WebClient.Builder builder;	//PocReviewApplication.java에서 Bean 등록 및 baseUrl 설정

	
    @GetMapping("/getReviewList")
    public String getReviewList(Model model) {
    	model.addAttribute("Review", null);
        return "/review/review";
    }

	@GetMapping("/productList") 
	public String productList(Model model){
		
		ResponseEntity<List<ProductDTO>> productList = restTemplate.exchange("/getProductList", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {});
        List<ProductDTO> result= productList.getBody();
		
        model.addAttribute("Product", result);
		return "/product/productListMD";
	}
  
	@GetMapping("/getReviewList1") //전체가 아닌 20개만 출력하도록 getReviewList2 수정
	public String getReviewList1(Model model) {
		/*	//WebClient ver.
		WebClient webClient = builder.build();
	    List<ReviewDTO> result = webClient.get().uri("/getReviewList1")
				.retrieve() // 응답값을 가져옴 
				.bodyToFlux(ReviewDTO.class)
				.collectList().block();
	    model.addAttribute("Review",result);
		return "apitest";	*/
		
			//RestTemplate ver.
		ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("/getReviewList1", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
        List<ReviewDTO> result= reviewResponse.getBody();
        model.addAttribute("Review",result);
		return "apitest";
		
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
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("/getReviewList2", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
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
        ResponseEntity<List<ReviewDTO>> reviewResponse = restTemplate.exchange("/getReviewList3/yulimkang", HttpMethod.GET, null, new ParameterizedTypeReference<List<ReviewDTO>>() {});
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
    	ReviewDTO review = restTemplate.getForObject("/getReview/"+_id, ReviewDTO.class);
    	model.addAttribute("ReviewData", review);
    	
    	try {
    	ReviewerDTO reviewer = restTemplate.getForObject("/Reviewer/"+review.getReviewer_id(),ReviewerDTO.class);
    	model.addAttribute("ReviewerData",reviewer);
    	
    	
    	ResponseEntity<List<CommentDTO>> commentsResponse = restTemplate.exchange("/Comments/"+review.get_id()
    								, HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentDTO>>() {});
        List<CommentDTO> comments= commentsResponse.getBody();
    	model.addAttribute("CommentData",comments);
    	System.out.println(comments);
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	ProductDTO product = restTemplate.getForObject("http://localhost:9092/getProductListByPrdSeq/"+review.getPrdSeq(), ProductDTO.class);
    	
    	model.addAttribute("Product", product);
        
    	return "apitest1";		  
    }
}
