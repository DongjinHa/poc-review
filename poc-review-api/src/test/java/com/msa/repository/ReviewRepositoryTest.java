package com.msa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.msa.dto.ReviewDTO;
import com.msa.repository.ReviewRepository;
import com.msa.service.ReviewService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewRepositoryTest {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	ReviewService reviewService;
	
	@Test
	public void createRows() {
		reviewRepository.deleteAll();
		
		ReviewDTO dto = new ReviewDTO();
		dto.setName("DongjinHa2");
		dto.setHp("01049952222");
		reviewRepository.save(dto);
		
		ReviewDTO dto2 = new ReviewDTO();
		dto2.setName("DongjinHa1");
		dto2.setHp("01049951111");
		reviewRepository.save(dto2);
		
		ReviewDTO dto3 = new ReviewDTO();
		dto3.setName("DongjinHa3");
		dto3.setHp("01049953333");
		reviewRepository.save(dto3);
		
		reviewRepository.findAll().forEach(System.out::println);
		
		//reviewService.getReviewList().forEach(System.out::println);
	}

}