	
	function srchPhotoList(schType) 
	{
		// 페이징
		var v_pageNo = $('#pageNo').val();
		
		if(schType == "more"){
			v_pageNo *= 1;
			$('#pageNo').val(v_pageNo+1);
			v_pageNo = v_pageNo+1;
		}else{
			 v_pageNo = "1";
			 $('#pageNo').val(v_pageNo);
		}
		
		if(schType == "photo"){
			filterReset();
			$('#key').val("");
		}
		
		var v_schType	   = schType;
		var v_reviewCl     = $(':input:radio[name=reviewCl]:checked').val();
		var v_catCd01      = $('#catCd01').val();
		var v_mode         = schType;  //$('#mode').val();
		var v_myReview     = $('#myReview').val();
		var v_key          = $('#key').val();
		var v_sort         = $(":input:radio[name=sort]:checked").val();
		var v_uage         = $(":input:radio[name=uAge]:checked").val();	//필터 연령
	
		//필터 피부타입 시작
		var v_skintypecd1  = '';
		var v_skintypecd2  = '';
		var v_skintypecd3  = '';
		var v_skintypecd4  = '';
		var v_skintypecd5  = '';
		var v_skintypecd6  = '';
		var v_skintypecd7  = '';
		var v_skintypecdyn = '';
		
		if($('input:checkbox[id="reViewFilter2_1"]').is(":checked"))
		{
			v_skintypecd1 = $('input:checkbox[id="reViewFilter2_1"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_2"]').is(":checked"))
		{
			v_skintypecd2 = $('input:checkbox[id="reViewFilter2_2"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_3"]').is(":checked"))
		{
			v_skintypecd3 = $('input:checkbox[id="reViewFilter2_3"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_4"]').is(":checked"))
		{
			v_skintypecd4 = $('input:checkbox[id="reViewFilter2_4"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_5"]').is(":checked"))
		{
			v_skintypecd5 = $('input:checkbox[id="reViewFilter2_5"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_6"]').is(":checked"))
		{
			v_skintypecd6 = $('input:checkbox[id="reViewFilter2_6"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_7"]').is(":checked"))
		{
			v_skintypecd7 = $('input:checkbox[id="reViewFilter2_7"]').val();
		}
		if(v_skintypecd1 + v_skintypecd2 + v_skintypecd3 + v_skintypecd4 + v_skintypecd5 + v_skintypecd6 + v_skintypecd7 == '')
		{
			v_skintypecdyn = 'N';
		}
		else
		{
			v_skintypecdyn = 'Y';
		}
		//필터 피부타입 끝
		
		//필터 피부밝기 시작
		var v_skinetcinfo1 = '';
		var v_skinetcinfo2 = '';
		var v_skinetcinfo3 = '';
		var v_skinetcinfoyn= '';
		
		if($('input:checkbox[id="reViewFilter3_1"]').is(":checked"))
		{
			v_skinetcinfo1 = $('input:checkbox[id="reViewFilter3_1"]').val();
		}
		if($('input:checkbox[id="reViewFilter3_2"]').is(":checked"))
		{
			v_skinetcinfo2 = $('input:checkbox[id="reViewFilter3_2"]').val();
		}
		if($('input:checkbox[id="reViewFilter3_3"]').is(":checked"))
		{
			v_skinetcinfo3 = $('input:checkbox[id="reViewFilter3_3"]').val();
		}
		if(v_skinetcinfo1 + v_skinetcinfo2 + v_skinetcinfo3 == '')
		{
			v_skinetcinfoyn = 'N';
		}
		else
		{
			v_skinetcinfoyn = 'Y';
		}
		//필터 피부밝기 끝
		
		//필터 피부톤 시작
		var v_skintonecd1  = '';
		var v_skintonecd2  = '';
		var v_skintonecd3  = '';
		var v_skintonecdyn = '';
			
		if($('input:checkbox[id="reViewFilter4_1"]').is(":checked"))
		{
			v_skintonecd1 = $('input:checkbox[id="reViewFilter4_1"]').val();
		}
		if($('input:checkbox[id="reViewFilter4_2"]').is(":checked"))
		{
			v_skintonecd2 = $('input:checkbox[id="reViewFilter4_2"]').val();
		}
		if($('input:checkbox[id="reViewFilter4_3"]').is(":checked"))
		{
			v_skintonecd3 = $('input:checkbox[id="reViewFilter4_3"]').val();
		}
		if(v_skintonecd1 + v_skintonecd2 + v_skintonecd3 == '')
		{
			v_skintonecdyn = 'N';
		}
		else
		{
			v_skintonecdyn = 'Y';
		}
		//필터 피부톤 끝
		
		
		$.ajax({
			url:'/review/allreview',
			type:'post',
			data:
			{
				pageNo : v_pageNo,
				reviewCl : v_reviewCl,
//				catCd01 : v_catCd01,
				mode : v_mode,
//				myReview : v_myReview,
				key : v_key,
				sort : v_sort,
				uage : v_uage,
				skintypecd1 : v_skintypecd1,
				skintypecd2 : v_skintypecd2,
				skintypecd3 : v_skintypecd3,
				skintypecd4 : v_skintypecd4,
				skintypecd5 : v_skintypecd5,
				skintypecd6 : v_skintypecd6,
				skintypecd7 : v_skintypecd7,
				skintypecdyn : v_skintypecdyn,
				skinetcinfo1 : v_skinetcinfo1,
				skinetcinfo2 : v_skinetcinfo2,
				skinetcinfo3 : v_skinetcinfo3,
				skinetcinfoyn : v_skinetcinfoyn,
				skintonecd1 : v_skintonecd1,
				skintonecd2 : v_skintonecd2,
				skintonecd3 : v_skintonecd3,
				skintonecdyn : v_skintonecdyn
			},
			success:function(data) { 
				
				if(schType == "more"){
					
					//var htmlData = data;
					//reviewList.append( htmlData ).isotope( 'appended', htmlData );
					//reviewList.find('.img img').load(function(){
					//	reviewList.isotope();
					//});
					//setTimeout(function(){state = true;}, 1000);
					
					$('#cardData').append(data);
					//setTimeout(function(){
					//	isotopeFunc();
					//},1000);
				}else{
					/* 검색 리스트 정렬 */ //  $('.card-columns').append(data);
//					reviewList.isotope('destroy')
					$('#cardData').empty().append(data);
//					$('#reviewList').html(data);
					//setTimeout(function(){
					//	isotopeFunc();
					//},1000);
					/*//검색 리스트 정렬 */
				}
			}
		});
	}
	/*
	var state = true;
	window.addEventListener('mousewheel', function(){
		var docScrollTop = document.documentElement.scrollTop;
		var wrapH = document.querySelector('#wrap').offsetHeight;

		if(checkPageMore() < $("#reviewTotalCount").val()){
			if(docScrollTop >= wrapH - 1500) {
				if(state == true) {
					state = false;
					srchPhotoList('more');
				}
			}
		}
	});
	*/
	function filterSet()
	{
		reivewFilter('close');		
		$('#pageNo').val("0");
		srchPhotoList('sort');
	}

	function filterReset()
	{
		$('input[name="uAge"]:radio[value="all"]').prop('checked',true);

		$('.checkArea input[type=checkbox]').prop('checked', false);
		
		$("#myProfile").removeClass("active");
	}
	/* //20190404 리뷰관 수정 */
	
	function reviewProductSearchGoPage(page)
	{
		var v_pageNo       = page;
		var v_key          = $('#key').val();

		$.ajax({
			url:'/kr/ko/ProductReviewListPrdSch.do',
			type:'post',
			data:
			{
				pageNo : v_pageNo,
				key : v_key
			},
			success:function(data) {
				$('#reviewSchListPrd').html(data);
			}
		});
	}
	
	function reviewProductSearch()
	{
		if (!$('#key').val()) {
			alert("검색어를 입력해주세요.");
			return;
		}
		
		var v_pageNo       = "1";
		var v_reviewCl     = $('#reviewCl').val();
		var v_catCd01      = $('#catCd01').val();
		var v_mode         = "key";  //$('#mode').val();
		var v_myReview     = $('#myReview').val();
		var v_key          = $('#key').val();
		var v_sort         = $(":input:radio[name=sort]:checked").val();
		
		var v_uage         = $(":input:radio[name=uAge]:checked").val();	//필터 연령
		
		//필터 피부타입 시작
		var v_skintypecd1  = '';
		var v_skintypecd2  = '';
		var v_skintypecd3  = '';
		var v_skintypecd4  = '';
		var v_skintypecd5  = '';
		var v_skintypecd6  = '';
		var v_skintypecd7  = '';
		var v_skintypecdyn = '';
		
		if($('input:checkbox[id="reViewFilter2_1"]').is(":checked"))
		{
			v_skintypecd1 = $('input:checkbox[id="reViewFilter2_1"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_2"]').is(":checked"))
		{
			v_skintypecd2 = $('input:checkbox[id="reViewFilter2_2"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_3"]').is(":checked"))
		{
			v_skintypecd3 = $('input:checkbox[id="reViewFilter2_3"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_4"]').is(":checked"))
		{
			v_skintypecd4 = $('input:checkbox[id="reViewFilter2_4"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_5"]').is(":checked"))
		{
			v_skintypecd5 = $('input:checkbox[id="reViewFilter2_5"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_6"]').is(":checked"))
		{
			v_skintypecd6 = $('input:checkbox[id="reViewFilter2_6"]').val();
		}
		if($('input:checkbox[id="reViewFilter2_7"]').is(":checked"))
		{
			v_skintypecd7 = $('input:checkbox[id="reViewFilter2_7"]').val();
		}
		if(v_skintypecd1 + v_skintypecd2 + v_skintypecd3 + v_skintypecd4 + v_skintypecd5 + v_skintypecd6 + v_skintypecd7 == '')
		{
			v_skintypecdyn = 'N';
		}
		else
		{
			v_skintypecdyn = 'Y';
		}
		//필터 피부타입 끝
		
		//필터 피부밝기 시작
		var v_skinetcinfo1 = '';
		var v_skinetcinfo2 = '';
		var v_skinetcinfo3 = '';
		var v_skinetcinfoyn= '';
		
		if($('input:checkbox[id="reViewFilter3_1"]').is(":checked"))
		{
			v_skinetcinfo1 = $('input:checkbox[id="reViewFilter3_1"]').val();
		}
		if($('input:checkbox[id="reViewFilter3_2"]').is(":checked"))
		{
			v_skinetcinfo2 = $('input:checkbox[id="reViewFilter3_2"]').val();
		}
		if($('input:checkbox[id="reViewFilter3_3"]').is(":checked"))
		{
			v_skinetcinfo3 = $('input:checkbox[id="reViewFilter3_3"]').val();
		}
		if(v_skinetcinfo1 + v_skinetcinfo2 + v_skinetcinfo3 == '')
		{
			v_skinetcinfoyn = 'N';
		}
		else
		{
			v_skinetcinfoyn = 'Y';
		}
		//필터 피부밝기 끝
		
		//필터 피부톤 시작
		var v_skintonecd1  = '';
		var v_skintonecd2  = '';
		var v_skintonecd3  = '';
		var v_skintonecdyn = '';
			
		if($('input:checkbox[id="reViewFilter4_1"]').is(":checked"))
		{
			v_skintonecd1 = $('input:checkbox[id="reViewFilter4_1"]').val();
		}
		if($('input:checkbox[id="reViewFilter4_2"]').is(":checked"))
		{
			v_skintonecd2 = $('input:checkbox[id="reViewFilter4_2"]').val();
		}
		if($('input:checkbox[id="reViewFilter4_3"]').is(":checked"))
		{
			v_skintonecd3 = $('input:checkbox[id="reViewFilter4_3"]').val();
		}
		if(v_skintonecd1 + v_skintonecd2 + v_skintonecd3 == '')
		{
			v_skintonecdyn = 'N';
		}
		else
		{
			v_skintonecdyn = 'Y';
		}
		//필터 피부톤 끝
		

		
		$.ajax({
			url:'/review/allreview',
			type:'post',
			data:
			{
				pageNo : v_pageNo,
				reviewCl : v_reviewCl,
				catCd01 : v_catCd01,
				mode : v_mode,
				myReview : v_myReview,
				key : v_key,
				sort : v_sort,
				uage : v_uage,
				skintypecd1 : v_skintypecd1,
				skintypecd2 : v_skintypecd2,
				skintypecd3 : v_skintypecd3,
				skintypecd4 : v_skintypecd4,
				skintypecd5 : v_skintypecd5,
				skintypecd6 : v_skintypecd6,
				skintypecd7 : v_skintypecd7,
				skintypecdyn : v_skintypecdyn,
				skinetcinfo1 : v_skinetcinfo1,
				skinetcinfo2 : v_skinetcinfo2,
				skinetcinfo3 : v_skinetcinfo3,
				skinetcinfoyn : v_skinetcinfoyn,
				skintonecd1 : v_skintonecd1,
				skintonecd2 : v_skintonecd2,
				skintonecd3 : v_skintonecd3,
				skintonecdyn : v_skintonecdyn
			},
			success:function(data) {
				$('#cardData').empty().append(data);
			}
		});
	}
	function myProfileSet(){
		var filterGnrt = $('#filterGnrt').val();
		var filterSkintypecd = $('#filterSkintypecd').val();
		var filterSkinetcinfo = $('#filterSkinetcinfo').val();
		var filterSkintonecd = $('#filterSkintonecd').val();

	    if($("#myProfile").hasClass("active")){
	    	filterReset();
            $("#myProfile").removeClass("active");	
        }else{
        	if(filterGnrt >= 40){
        		$('input[name="uAge"]:radio[value="40"]').prop('checked',true);
        	}else{
        		$('input[name="uAge"]:radio[value="'+ filterGnrt +'"]').prop('checked',true);
        	}
        	$('input[name="skintypecd"]:checkbox[value="'+ filterSkintypecd +'"]').prop('checked',true);
        	$('input[name="skinetcinfo"]:checkbox[value="'+ filterSkinetcinfo +'"]').prop('checked',true);
        	$('input[name="skintonecd"]:checkbox[value="'+ filterSkintonecd +'"]').prop('checked',true);
            $("#myProfile").addClass("active");
        }
	}
	function reviewLoginChk(){
		alert("로그인 후 이용 가능한 서비스입니다.\n로그인 후 이용해 주세요.");
		$.jhead.fnChkLoginPagePop();			
	}

	if(getUrlParams().reviewCl == 'A' || getUrlParams().reviewCl == 'B') {
		setTimeout(function(){scrollMov('#reivewTab')},500);
	}