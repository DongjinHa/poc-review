package com.msa.repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.msa.dto.CommentDTO;
import com.msa.dto.ReviewDTO;
import com.msa.dto.ReviewerDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewerRepositoryTest {

	@Autowired
	ReviewerRepository reviewerRepository;

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Test
	public void createRows() {
		reviewerRepository.deleteAll();
		reviewRepository.deleteAll();
		commentRepository.deleteAll();

		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Random ran = new Random();
		for (int i = 0; i < 500; i++) {
			
			ReviewerDTO dto = new ReviewerDTO();

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
			
			dto.setProfileImg("/img/reviewer/reviewerPic"+(ran.nextInt(10)+1)+"_on.png");
			
			Calendar calendar = Calendar.getInstance();
			String nowDate = simpleDateFormat.format(calendar.getTime());
			dto.setUpdDate(nowDate);
			
			//등록일자는 무작위로
			calendar.add(Calendar.DATE, (ran.nextInt(365)+1)*-1);
			String regDate = simpleDateFormat.format(calendar.getTime());
			dto.setRegDate(regDate);

			reviewerRepository.save(dto);
			
		}

		int i = 0;
		List<ReviewerDTO> reviewerList = reviewerRepository.findAll();
		for (ReviewerDTO reviewerDTO : reviewerList) {
			i++;
			
			int loopCnt = ran.nextInt(6); // 1명당 리뷰 등록 건수
			for (int x = 0; x < loopCnt; x++) {
				ReviewDTO dto = new ReviewDTO();

				dto.setReviewer_id(reviewerDTO.get_id());
				
				String reviewCl = "";
				int num = ran.nextInt(2)+1;
				if (num == 1) {
					reviewCl = "A";
				} else {
					reviewCl = "B";
				}
	
				dto.setReviewCl(reviewCl);

				String[] _prdSeq = {"11111"
						, "22222"
						, "33333"
						, "44444"
						, "55555"
						, "66666"
						, "77777"
						, "88888"
						, "99999"};
				dto.setPrdSeq(_prdSeq[ran.nextInt(9)]);
				dto.setBestFl((ran.nextInt(15)+1) > 10? "Y" : "N");
				dto.setEvalScore((ran.nextInt(5)+1)+"");
				dto.setHit((ran.nextInt(100)+1)+"");
				dto.setRecomCnt((ran.nextInt(15)+1)+"");
	//			dto.setCmtCnt("");
				dto.setRecbScore((ran.nextInt(10)+1)+"");
				
				
				String[] _goodCnts = {"색이 많이 진하네요\n생각보다 별로에요"
									, "가성비 좋네요\n많이파세요~~~"
									, "간편해서 좋아요 예비용으로 하나씩 파우치에 넣고다니는걸 추천합니다"
									, "저 이거 완전 좋아요!\n피부가 예민하구 해서 선크림 잘 못 고르는데 이게 완전 순하고 톤업기능듀 좋아서 요거 하나면 화장 끝이네요!!\n어느정도 커버 능력도 전 있는거 같았어요!\n좋네요! 원래 쓰던 제품도 유명한 톤업 선크림이었는데 성분도 더 좋고 발림감 뭐하나 떨어지는것도 없어요!\n가격차이는 많이 나는뎅 ㅎㅎ여기로 정착할렵니닽 할인이나 이벤트 많이 해주세요"};
				
				dto.setGoodCnts("내용다름표기>>"+i+" "+_goodCnts[ran.nextInt(4)]);
				
				String[] _etcCnts = {""
						, ""
						, ""
						, ""
						, "옴청좋네요!"
						, "너무 좋아서 지인한테 선물하려고 또 구매했어요!"
						, "또사러올게요~"
						, ""};
				
				dto.setEtcCnts("기타다름표기>>"+i+" "+_etcCnts[ran.nextInt(8)]);
	//			dto.setTplRegCnt("");
	//			dto.setPrevImg("");
				
				String[] _tpList;
				if (i%5 == 0) {
					_tpList = new String[(ran.nextInt(3)+2)];
					
					boolean flag;
					int idx = 0;
					while (true) {
						flag = false;
						num = (ran.nextInt(10)+1);
						for(int y = 0; y < _tpList.length; y++) {
							//실제 경로에 맞춰 수정(reviews -> review)
							if (_tpList[y] != null && _tpList[y].equals("/img/review/"+num+".jpg")) {
								flag = true;
							}
						}
						
						if (!flag) {
							//실제 경로에 맞춰 수정(reviews -> review)
							_tpList[idx++] = "/img/review/"+num+".jpg";
							
							if (idx == _tpList.length) {
								break;
							}
						}
					}
				} else {
					num = (ran.nextInt(10)+1);
					_tpList = new String[1];
					//실제 경로에 맞춰 수정(reviews -> review)
					_tpList[0] = "/img/review/"+num+".jpg";
				}
				
				dto.setTplList(_tpList);
			
				Calendar calendar = Calendar.getInstance();
				String nowDate = simpleDateFormat.format(calendar.getTime());
				dto.setUpdDate(nowDate);
				
				//등록일자는 무작위로
				calendar.add(Calendar.DATE, (ran.nextInt(365)+1)*-1);
				String regDate = simpleDateFormat.format(calendar.getTime());
				dto.setRegDate(regDate);
				
				reviewRepository.save(dto);

			}
			
		}
		
		String reviewer_id;
		List<ReviewDTO> reviewList = reviewRepository.findAll();
		for (ReviewDTO reviewDTO : reviewList) {
			
			i = 0;
			int commentCnt = ran.nextInt(20)+1;
			reviewer_id = reviewDTO.getReviewer_id();
			for (ReviewerDTO reviewerDTO : reviewerList) {
				CommentDTO dto = new CommentDTO();
				
				if (reviewer_id.equals(reviewerDTO.get_id())) {
					continue;
				}

				if ((ran.nextInt(3)+1)%3 == 0) {
					i++;
					
					dto.setReview_id(reviewDTO.get_id());
					dto.setReviewer_id(reviewerDTO.get_id());
					dto.setCnts(i+"등");
					
					Calendar calendar = Calendar.getInstance();
					String nowDate = simpleDateFormat.format(calendar.getTime());
					dto.setUpdDate(nowDate);
					
					//등록일자는 무작위로
					calendar.add(Calendar.DATE, (ran.nextInt(365)+1)*-1);
					String regDate = simpleDateFormat.format(calendar.getTime());
					dto.setRegDate(regDate);
					
					commentRepository.save(dto);

					if (commentCnt <= i) {
						break;
					}
				}
			}
		}
	}

}
