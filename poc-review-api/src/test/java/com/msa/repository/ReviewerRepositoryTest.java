package com.msa.repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.msa.document.Comment;
import com.msa.document.Review;
import com.msa.document.Reviewer;

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

//		String pattern = "yyyyMMddHHmmss";
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Random ran = new Random();
		for (int i = 0; i < 500; i++) {
			
			Reviewer reviewer = new Reviewer();

			// 생년월일
			int birthYYYY = 2020-ran.nextInt(70)+14;
			
			Calendar birthDay = Calendar.getInstance();
			birthDay.set(Calendar.YEAR, birthYYYY);
			birthDay.add(Calendar.DATE, (ran.nextInt(365)+1)*-1);
			String strBirthDay = simpleDateFormat.format(birthDay.getTime());
			
			reviewer.setBirthDay(strBirthDay);

			reviewer.setNickNm("리뷰어"+(i+1)+"_"+strBirthDay);
			reviewer.setLvl(ran.nextInt(10)+1+"");
			
			String[] _sex = {"F", "M"};
			reviewer.setSex(_sex[ran.nextInt(2)]);
			
			reviewer.setSkinToneCd("SX0"+ran.nextInt(4));
			reviewer.setSkinTypeCd("SZ0"+ran.nextInt(8));
			
			if(i%10 != 0){
				String[] _skinTrub = {"SW0"+(ran.nextInt(6)+1), "SW0"+(ran.nextInt(6)+7)};
				reviewer.setSkinTrublList(_skinTrub);
			}
			
			String[] _skinEtc = {"SY13", "SY21", "SY23"};
			reviewer.setSkinEtcInfo(_skinEtc[ran.nextInt(3)]);
			
			reviewer.setProfileImg("/img/reviewer/reviewerPic"+(ran.nextInt(10)+1)+"_on.png");
			
			Calendar calendar = Calendar.getInstance();
			
			//등록일자는 무작위로
			calendar.add(Calendar.DATE, (ran.nextInt(365)+1)*-1);
			reviewer.setRegDate(calendar.getTime());
			reviewer.setUpdDate(calendar.getTime());

			reviewerRepository.save(reviewer);
			
		}

		int i = 0;
		List<Reviewer> reviewerList = reviewerRepository.findAll();
		for (Reviewer reviewer : reviewerList) {
			i++;
			
			int loopCnt = ran.nextInt(6); // 1명당 리뷰 등록 건수
			for (int x = 0; x < loopCnt; x++) {
				Review review = new Review();

				review.setReviewer_id(reviewer.get_id());
				
				String reviewCl = "";
				int num = ran.nextInt(2)+1;
				if (num == 1) {
					reviewCl = "A";
				} else {
					reviewCl = "B";
				}
	
				review.setReviewCl(reviewCl);

				String[] _prdSeq = {"11111"
						, "22222"
						, "33333"
						, "44444"
						, "55555"
						, "66666"
						, "77777"
						, "88888"
						, "99999"};
				review.setPrdSeq(_prdSeq[ran.nextInt(9)]);
				review.setBestFl((ran.nextInt(15)+1) > 10 && "A".equals(reviewCl)? "Y" : "N");
				review.setEvalScore(Integer.valueOf(ran.nextInt(5)+1));
				review.setHit(Integer.valueOf(ran.nextInt(100)+1));
				review.setRecomCnt(Integer.valueOf(ran.nextInt(15)+1));
	//			review.setCmtCnt("");
				review.setRecbScore(Integer.valueOf(ran.nextInt(10)+1));
				
				String[] _goodCnts = {"색이 많이 진하네요\n생각보다 별로에요"
									, "가성비 좋네요\n많이파세요~~~"
									, "간편해서 좋아요 예비용으로 하나씩 파우치에 넣고다니는걸 추천합니다"
									, "저 이거 완전 좋아요!\n피부가 예민하구 해서 선크림 잘 못 고르는데 이게 완전 순하고 톤업기능듀 좋아서 요거 하나면 화장 끝이네요!!\n어느정도 커버 능력도 전 있는거 같았어요!\n좋네요! 원래 쓰던 제품도 유명한 톤업 선크림이었는데 성분도 더 좋고 발림감 뭐하나 떨어지는것도 없어요!\n가격차이는 많이 나는뎅 ㅎㅎ여기로 정착할렵니닽 할인이나 이벤트 많이 해주세요"};
				
				review.setGoodCnts("내용다름표기>> "+i+" "+_goodCnts[ran.nextInt(4)]);
				
				String[] _etcCnts = {""
						, ""
						, ""
						, ""
						, "옴청좋네요!"
						, "너무 좋아서 지인한테 선물하려고 또 구매했어요!"
						, "또사러올게요~"
						, ""};
				
				review.setEtcCnts("기타다름표기>>"+i+" "+_etcCnts[ran.nextInt(8)]);
	//			review.setTplRegCnt("");
	//			review.setPrevImg("");
				
				String[] _tpList = null;
				if("A".equals(review.getReviewCl())) {
					_tpList = new String[(ran.nextInt(5)+1)];
					
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
				}
				
				review.setTplList(_tpList);
			
				Calendar calendar = Calendar.getInstance();
				
				//등록일자는 무작위로
				calendar.add(Calendar.DATE, (ran.nextInt(365)+1)*-1);
				review.setRegDate(calendar.getTime());
				review.setUpdDate(calendar.getTime());
				
				reviewRepository.save(review);

			}
			
		}
		
		ObjectId reviewer_id;
		List<Review> reviewList = reviewRepository.findAll();
		for (Review review : reviewList) {
			
			i = 0;
			int commentCnt = ran.nextInt(20)+1;
			reviewer_id = review.getReviewer_id();
			
			Date regDate = review.getRegDate();
			Calendar compareDate = Calendar.getInstance();
			compareDate.setTime(regDate);
			
			Calendar today = Calendar.getInstance();
			for (Reviewer reviewer : reviewerList) {
				Comment comment = new Comment();
				
				if (reviewer_id.equals(reviewer.get_id())) {
					continue;
				}

				if ((ran.nextInt(3)+1)%3 == 0) {
					i++;

					comment.setReview_id(review.get_id());
					comment.setReviewer_id(reviewer.get_id());
					comment.setCnts(i+"등");
					
					compareDate.add(Calendar.MINUTE, (ran.nextInt(60)+1));
					
					// 미래날짜로 등록되지 않도록함
					if (compareDate.compareTo(today) == 1) {
						break;
					}
					comment.setRegDate(compareDate.getTime());
					comment.setUpdDate(compareDate.getTime());
					
					commentRepository.save(comment);

					if (commentCnt <= i) {
						break;
					}
				}
			}
		}
	}

}
