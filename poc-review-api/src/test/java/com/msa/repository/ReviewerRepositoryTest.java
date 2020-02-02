package com.msa.repository;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.msa.dto.ReviewerDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewerRepositoryTest {

	@Autowired
	ReviewerRepository reviewerRepository;

	@Test
	public void createRows() {
		reviewerRepository.deleteAll();
		
		for(int i = 0; i < 50; i++) {
	
			ReviewerDTO dto = new ReviewerDTO();
			Random ran = new Random();
			
			dto.setNickNm("리뷰어"+(i+1));
			dto.setLvl(ran.nextInt(10)+"");
			
			String[] _sex = {"F", "M"};
			dto.setSex(_sex[ran.nextInt(2)]);
			
			dto.setBirthDay("19"+(ran.nextInt(7)+3)+(ran.nextInt(9)+1)+"0209");
			dto.setSkinToneCd("SX0"+ran.nextInt(4));
			dto.setSkinTypeCd("SZ0"+ran.nextInt(8));
			
			if(i%10 != 0){
				String[] _skinTrub = {"SW0"+(ran.nextInt(6)+1), "SW0"+(ran.nextInt(6)+7)};
				dto.setSkinTrublList(_skinTrub);
			}
			
			String[] _skinEtc = {"SY13", "SY21", "SY23"};
			dto.setSkinEtcInfo(_skinEtc[ran.nextInt(3)]);
			
			dto.setProfileImg("https://www.innisfree.com/kr/ko/resources/web2/images/review/reviewerPic"+(ran.nextInt(10)+1)+"_on.png");
			reviewerRepository.save(dto);
			
		}

//		reviewerRepository.findAll().forEach(System.out::println);
		
	}

}
