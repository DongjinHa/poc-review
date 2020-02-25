var osObj = {
	'agent' : navigator.userAgent.toLocaleLowerCase(),
	'html' : document.getElementsByTagName('html')[0],
	'htmlCls' : document.getElementsByTagName('html')[0].getAttribute('class'),
	'osVer' : null,
	'deviceVer' : null,
}

if(osObj.agent.indexOf('msie') > -1) {
	var device = osObj.agent.substring(osObj.agent.indexOf('msie') + 4);
	osObj.deviceVer = Math.floor(device.substring(0, device.indexOf(';')));
	osObj.osVer = 'ie' + osObj.deviceVer;
} else {
	osObj.osVer = '';
}
if(osObj.agent.indexOf('iphone') > -1 || osObj.agent.indexOf('ipad') > -1 || osObj.agent.indexOf('android') > -1) {
	if(osObj.agent.indexOf('iphone') > -1 || osObj.agent.indexOf('ipad') > -1) {
		var device = osObj.agent.substring(osObj.agent.indexOf('os') + 3);
		var deviceVer = device.substring(0, device.indexOf('like mac os x'));
		osObj.osVer = 'ios' + deviceVer;
	} else if(osObj.agent.indexOf('android') > -1) {
		var device = osObj.agent.substring(osObj.agent.indexOf('android') + 8);
		var deviceVer = device.substring(0, device.indexOf(';'));
		andVer = deviceVer.replace(/[.]/gi,'_');
		osObj.osVer = 'android' + andVer;

		if(osObj.agent.indexOf('samsung') > -1) osObj.osVer += ' samsung';
	}
	if(osObj.agent.indexOf('innimemapp') > -1) osObj.osVer += ' webview';
	if(osObj.agent.indexOf('naver') > -1) osObj.osVer += ' naver';
	osObj.osVer +=  ' mobile';
}

(osObj.htmlCls !== null) ? osObj.html.setAttribute('class', osObj.htmlClass + ' ' + osObj.osVer) : osObj.html.setAttribute('class', osObj.osVer);

var defaultObj = {
	'win': $(window),
	'doc': $('html'),
	'currentBtn' : ''
}

/* 파라미터 체크 */
function getUrlParams() {
	var params = {}
	window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value });
	return params;
}

/* 메인 슬라이드 */
var slideObj = {}
var keyVisualSlide = function() {
	var obj = 'keyVisualSlide';
	var btnAuto = $('.'+obj).find('.btnAuto');
	
	btnAuto.on('click', function(){
		var $this = $(this);
		if($this.hasClass('stop')) {
			$this.removeClass('stop');
			slide.autoplay.start();
		} else {
			$this.addClass('stop');
			slide.autoplay.stop();
		}
	});
	var slide = new Swiper('.' + obj + ' .slide', {
		loop: true,
		speed: 800,
		autoplay: {delay: 5000},
		pagination: {
			el: '.' + obj + ' .slidePage',
			clickable: true,
			renderBullet: function (index, className) {
				return '<button type="button" class="' + className + '">' + (index + 1) + '</button>';
			},
		}
	});
	$('.' + obj).on({
		'mouseenter' : function(){slide.autoplay.stop();},
		'mouseleave' : function(){
			if(!btnAuto.hasClass('stop')) {slide.autoplay.start();}
		}
	});
	slideObj[obj] = slide;
}
var innitemSlide = function() {
	var obj = 'innitemSlide';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				slide = $('.'+obj+' .swiper-wrapper').slick({
					infinite: true,
					slidesToShow: 4,
					slidesToScroll: 4,
					prevArrow : $('.' + obj + ' .btnPrev'),
					nextArrow : $('.' + obj + ' .btnNext'),
				});
				slideObj[obj] = slide;
			}
		});
	}
}
var myShopSlide  = function() {
	var obj = 'mainMyshop';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				slide = $('.'+obj+' .swiper-wrapper').slick({
					infinite: true,
					slidesToShow: 4,
					slidesToScroll: 4,
					prevArrow : $('.' + obj + ' .btnPrev'),
					nextArrow : $('.' + obj + ' .btnNext'),
				});
				slideObj[obj] = slide;
			}
		});
	}
}
var bestSellerSlide = function() {
	var obj = 'bestSeller';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				slide = new Swiper('.' + obj + ' .slide', {
					loop: true,
					speed: 800,
					autoplay: {delay: 5000},
					navigation: {
						prevEl: '.' + obj + ' .btnPrev',
						nextEl: '.' + obj + ' .btnNext',
					}
				});
				slideObj[obj] = slide;
				$('.' + obj).on({
					'mouseenter' : function(){slide.autoplay.stop();},
					'mouseleave' : function(){slide.autoplay.start();}
				});
				$('.reviewList').niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none'});
			}
		});
	}
}
var mdRecomSlide = function() {
	var obj = 'mdRecomSlide';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				slide = $('.'+obj+' .swiper-wrapper').slick({
					infinite: true,
					slidesToShow: 3,
					slidesToScroll: 3,
					prevArrow : $('.' + obj + ' .btnPrev'),
					nextArrow : $('.' + obj + ' .btnNext'),
				});
				slideObj[obj] = slide;
			}
		});
	}
}
var reviewSlide = function() {
	var obj = 'reviewSlide';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				slide = $('.'+obj+' .swiper-wrapper').slick({
					infinite: true,
					slidesToShow: 2,
					slidesToScroll: 2,
					prevArrow : $('.' + obj + ' .btnPrev'),
					nextArrow : $('.' + obj + ' .btnNext'),
				});
				slideObj[obj] = slide;
			}
		});
	}
}
var newPdtSlide = function() {
	var obj = 'newPdtSlide';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				slide = $('.'+obj+' .swiper-wrapper').slick({
					infinite: true,
					slidesToShow: 4,
					slidesToScroll: 4,
					prevArrow : $('.' + obj + ' .btnPrev'),
					nextArrow : $('.' + obj + ' .btnNext'),
				});
				slideObj[obj] = slide;
			}
		});
	}
}
var mainRecomPdtLine = function() {
	var obj = 'mainRecomPdtLine';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				var wrap = $('.' + obj);
				var tab = wrap.find('.slideTab li');
				var tabBtn = tab.find('button');
				var idx = 0;
				tab.eq(idx).addClass('active').siblings().removeClass('active');
				slide = new Swiper('.' + obj + ' .slide', {
					speed: 800,
					on: {
						slideChange : function() {
							idx = this.activeIndex;
							tab.eq(idx).addClass('active').siblings().removeClass('active');
						}
					}
				});
				slideObj[obj] = slide;
				tabBtn.on('mouseenter', function(){
					var $this = $(this);
					var btnIdx = $this.parent().index();
					slide.slideTo(btnIdx);
				});
			}
		});
	}
}
var hotDealSlide = function(){
	var obj = 'mainHotDeal';
	var slide = '';
	if($('.'+obj).length) {
		window.addEventListener('scroll', function(){
			var objTop = $('.'+obj).offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(slide) == 'string' && winH + scrollTop >= objTop - 500) {
				if($('.' + obj + ' .swiper-slide').length > 8) {
					slide = $('.'+obj+' .swiper-wrapper').slick({
						infinite: true,
						slidesToShow: 4,
						slidesToScroll: 4,
						rows: 2,
						prevArrow : $('.' + obj + ' .btnPrev'),
						nextArrow : $('.' + obj + ' .btnNext'),
					});
					slideObj[obj] = slide;
				} else {
					$('.' + obj + ' .slideBtn').hide();
				}
			}
		});
	}
}
/* //메인 슬라이드 */

/* 메인 랭킹 */
var ranking = function(obj) {
	var rankingWrap = $(obj);
	var rankingInterval = '';
	if(rankingWrap.length) {
		window.addEventListener('scroll', function(){
			var objTop = rankingWrap.offset().top;
			var winH = this.innerHeight;
			var scrollTop = this.scrollY || document.documentElement.scrollTop;
			if(typeof(rankingInterval) == 'string' && winH + scrollTop >= objTop - 500) {
				var rankingTab = rankingWrap.find('.rankingTab');
				var tabBtn = rankingTab.find('button');
				var tabCont = rankingWrap.find('.rankingList');
				var list = tabCont.find('li');
				var timer;

				rankingInterval = function(){
					timer = setInterval(function(){
						var activeCont = rankingWrap.find('.rankingCont > .active');
						var listLength = activeCont.find('li').length - 1;
						var activeList = activeCont.find('.active');
						if(activeList.index() == listLength) {
							activeCont.find('li').eq(0).addClass('active').siblings().removeClass('active');
						} else {
							activeList.next().addClass('active').siblings().removeClass('active');
						}
					},3000);
				}
				rankingInterval();

				tabBtn.on('click', function(){
					var $this = $(this);
					var idx = $this.parent().index();
					$this.parent().addClass('active').siblings().removeClass('active');
					tabCont.eq(idx).addClass('active').siblings().removeClass('active');
					clearInterval(timer);
					rankingInterval();
				});
				list.on({
					'mouseenter' : function(){
						var $this = $(this);
						$this.addClass('active').siblings().removeClass('active');
						clearInterval(timer);
					},
					'mouseleave' : function(){
						rankingInterval();
					}
				});
			}
		});
	}
}

var forUitemSlide = function(cls) {
	var obj = cls;
	var slide = new Swiper('.' + obj + ' .slide', {
		loop: true,
		speed: 800,
		slidesPerView : 5,
		slidesPerGroup : 5,
		loopFillGroupWithBlank: true,
		navigation: {
			prevEl: '.' + obj + ' .btnPrev',
			nextEl: '.' + obj + ' .btnNext',
		}
	});
	slideObj[obj] = slide;
}

var bannerSlide = function(cls) {
	var obj = cls;
	if($('.'+cls).find('.swiper-slide').length > 1) {
		var slide = new Swiper('.' + obj + ' .slide', {
			loop: true,
			speed: 800,
			autoplay: {delay: 5000},
			pagination: {
				el: '.' + obj + ' .slidePage',
				clickable: true,
				renderBullet: function (index, className) {
					return '<button type="button" class="' + className + '">' + (index + 1) + '</button>';
				},
			}
		});
		slideObj[obj] = slide;
	}
}

/* 이벤트 슬라이드 */
var eventVisualSlide = function() {
	var obj = 'eventVisualSlide';
	if($('.'+obj+ ' .swiper-slide').length > 1) {
		var slide = new Swiper('.' + obj + ' .slide', {
			loop: true,
			speed: 800,
			autoplay: {delay: 5000},
			pagination: {
				el: '.' + obj + ' .slidePage',
				clickable: true,
				renderBullet: function (index, className) {
					return '<button type="button" class="' + className + '">' + (index + 1) + '</button>';
				},
			}
		});
		slideObj[obj] = slide;
	}
}
/* //이벤트 슬라이드 */

/* 장바구니 함께 사면 좋은 제품 */
var orderPdtSlide = function() {
	var obj = 'orderPdtSlide';
	var slide = new Swiper('.' + obj + ' .slide', {
		loop: true,
		speed: 800,
		slidesPerView : 4,
		slidesPerGroup : 1,
		navigation: {
			prevEl: '.' + obj + ' .btnPrev',
			nextEl: '.' + obj + ' .btnNext',
		}
	});
	slideObj[obj] = slide;
}

/* 상품 상세보기 */
var pdtView = function(isRoll) {
	//if(isRoll == '' || isRoll == null || typeof isRoll === 'undefined') isRoll == "N";

	var obj = 'pdtViewImg';
	if($('.' + obj + ' .swiper-slide').length > 1) {
		var slide = new Swiper('.' + obj + ' .slide', {
			loop: true,
			speed: 800,
			autoplay: {delay: 5000},
			pagination: {
				el: '.' + obj + ' .slidePage',
				clickable: true,
				renderBullet: function (index, className) {
					return '<button type="button" class="' + className + '">' + (index + 1) + '</button>';
				},
			}
		});
		slideObj[obj] = slide;
	}

	//if(isRoll != 'Y') slide.autoplay.stop();
}
var pdtViewRelItem = function(obj) {

	if($('.' + obj + ' .swiper-slide').length > 5) {
		var slide = new Swiper('.' + obj + ' .slide', {
			loop: true,
			speed: 800,
			slidesPerView : 5,
			slidesPerGroup : 5,
			navigation: {
				prevEl: '.' + obj + ' .btnPrev',
				nextEl: '.' + obj + ' .btnNext',
			}
		});
	} else {
		$('.' + obj + ' .slideBtn').hide();
	}
	slideObj[obj] = slide;
}

/* 파워리뷰 */
var powerReviewSlide = function() {
	var obj = 'powerReviewSlide';
	var slide = new Swiper('.' + obj + ' .slide', {
		speed: 800,
		slidesPerView : 'auto',
		slidesPerGroup : 1,
		navigation: {
			prevEl: '.' + obj + ' .btnPrev',
			nextEl: '.' + obj + ' .btnNext',
		}
	});
	slideObj[obj] = slide;
}

/* 추천리뷰 */
var recomReviewSlide = function() {
	var obj = 'recomReviewSlide';
	var slide = new Swiper('.' + obj + ' .slide', {
		speed: 800,
		slidesPerView : 'auto',
		slidesPerGroup : 1,
		navigation: {
			prevEl: '.' + obj + ' .btnPrev',
			nextEl: '.' + obj + ' .btnNext',
		}
	});
	slideObj[obj] = slide;
}

/* 리뷰 상세 */
var reviewDetailSlide = function() {
	var obj = 'reviewDetailSlide';
	if($('.' + obj + ' .swiper-slide').length > 1) {
		var slide = new Swiper('.' + obj + ' .slide', {
			loop: true,
			speed: 800,
			navigation: {
				prevEl: '.' + obj + ' .btnPrev',
				nextEl: '.' + obj + ' .btnNext',
			}
		});
		slideObj[obj] = slide;
	} else {
		$('.' + obj + ' .slideControl').hide();
	}
}

/* 헤더 유틸 레이어 */
var headerUtillLayer = function(obj, state) {
	var layers = $('#header .utilArea .layerHeader');
	var layerWrap = $(obj);
	layerWrap.find('.cartWrap .cartList').niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none'});
	if(layerWrap.is(':hidden')) {
		layers.stop().slideUp(200);
		layerWrap.stop().slideDown(200);
	}
	if(obj == '.layerSearch') {
		layerWrap.find('.keywordInput .inputTxt').focus();
	}
	layerWrap.off().on('mouseleave', function(){
		layerWrap.stop().slideUp(200);
		if(obj == '.layerSearch') {
			layerWrap.find('.keywordInput .inputTxt').val('');
			layerWrap.find('.keywordRolling, #hashTag').show();
			layerWrap.find('#ark').hide().find('.ark_content_list').empty();
		}
	});
}

/* 헤더 키워드 롤링 */
var keywordRolling = function() {
	var wrap = $('.keywordRolling ul');
	var list = wrap.find('li');
	var link = list.find('a');
	var listH = list.height();
	var limit = listH * list.length;
	var top = 0;

	if(list.length == 0) wrap.parent().remove();
	list.eq(0).clone().appendTo(wrap);
	setInterval(function(){
		top += listH;
		wrap.animate({'margin-top': '-'+top+'px'},500, function(){
			if(top == limit) {
				top = 0;
				wrap.css('margin-top', top);
			}
		});
	}, 3000);

	list.off().on('click', function(){
		wrap.parent().hide();
		$('#querytop').focus();
	});
	link.on('click', function(){
		if(event && event.stopPropagation) {
			event.stopPropagation();
		} else if (window.event) {
			window.event.cancelBubble = true;
		}
	});
}

/* 로케이션 메뉴 */
var locDepList = function() {
	var target = event.target || event.srcElement;
	var $this = $(target);
	var locWrap = $this.closest('.location');
	var locDep = $this.parent();
	var depList = locWrap.find('.list');
	depList.stop().slideUp(200);
	$this.next('.list').stop().slideDown(200, function(){
		depList.niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none'});
	}).parent().addClass('active').siblings().removeClass('active');
	locDep.off().on('mouseleave', function() {
		depList.stop().slideUp(200, function(){
			depList.getNiceScroll().remove();
			depList.attr('style','');
		});
	});
}

/* 플로팅메뉴 최근 본 상품 */
var pdtHistoryShow = function() {
	var historyWrap = $('.floatingMenu .pdtList');
	var historyList = historyWrap.find('.list');
	if(historyWrap.hasClass('hideList')) {
		setTimeout(function(){
			historyList.niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none'});
		},200);
	} else {
		historyList.getNiceScroll().remove();
		historyList.attr('style','');
	}
	historyWrap.toggleClass('hideList');
}

/* 탑 배너 */
var docTopBannerClose = function() {
	var bannerWrap = $('.docTopBanner');
	bannerWrap.slideUp(300);
}

/* gnb */
var gnbControl = function(state) {
	var gnbWrap = $('#gnb');
	(state != 'close') ? gnbWrap.toggleClass('open') : gnbWrap.removeClass('open')
}
/* gnb 메뉴 */
var depActive = function() {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	$this.parent().addClass('active').siblings().removeClass('active');
}
/* snb 라인 */
var lineListMore = function(){
	var snb = $('#snb .inner');
	var contents = $('.contents');
	snb.toggleClass('allLine');
	contents.css('min-height', snb.outerHeight());
}

/* 메인 동영상 플레이 */
var vodPlay = function() {
	var target = event.currentTarget || event.srcElement;
	var btnPlay = $(target);
	if(typeof(yotubeId) != 'undefined') {
		if(ytState == false) {
			ytSet(yotubeId, 'popularVod', btnPlay);
		} else {
			btnPlay.fadeOut(1500);
			ytObj[yotubeId].playVideo();
		}
	} else {
		var vod = document.querySelector('#popularVod');
		btnPlay.fadeOut(1000);
		vod.play();
		vod.addEventListener('ended', function(){
			btnPlay.fadeIn(1000);
		});
	}
}

/* 유튜브 API */
var ytState = false;
var ytObj = {};
var ytSet = function(ytId, tagId, btn) {
	if(ytState == false) {
		var tag = document.createElement('script');
		tag.src = "https://www.youtube.com/iframe_api";
		var firstScriptTag = document.getElementsByTagName('script')[0];
		firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
		ytState = true;
	}
	createPlayer(ytId, tagId, btn);
}
function createPlayer(ytId, tagId, btn) {
	setTimeout(function() {
		ytObj[ytId] = new YT.Player(tagId, {
			width: '100%',
			height: '100%',
			videoId: ytId,
			playerVars : {
				'controls' : 0,
				'showinfo' : 0,
				'rel' : 0,
				'playsinline' : 1
			},
			events: {
				'onReady': function(){
					$('#'+tagId).addClass('ready');
					if(tagId == 'popularVod') {
						btn.fadeOut(1500);
						ytObj[ytId+'btn'] = btn;
						ytObj[ytId].playVideo();
					}
				},
				'onStateChange': function(event){
					if(event.data === 0) {
						ytObj[ytId+'btn'].fadeIn(300);
					}
				}
			}
		});
	}, 1000);
}
var ytPlay = function(ytId) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	if(ytState == true) {
		$this.fadeOut(1500);
		ytObj[ytId+'btn'] = $this;
		ytObj[ytId].playVideo();
	}
}

/* 스크롤 탑 이동 */
var docTop = function() {
	defaultObj.doc.animate({scrollTop : 0}, 500);
}
var qnaScroll = function(id){
	if($('#'+id).length) {
		var top = $('#'+id).offset().top - 100;
		defaultObj.doc.animate({scrollTop : top}, 300);
		$('#'+id).addClass('active').find('.con').slideDown(300);
	}
}

/* 스크롤 픽스 */
var fixedFunc = function(scrollTop) {
	var wrap = $('#wrap');
	var header = $('#header');
	var footer = $('#footer');
	var snb = $('#snb');
	var totalPayment = $('.totalPayment');
	var pdtViewCount = $('.pdtViewCount');
	var pdtTabWrap = $('.pdtTabWrap');
	if(header.length) {
		var startPos = header.offset().top;
		if(scrollTop > startPos) {
			if(!wrap.hasClass('fixed')) wrap.addClass('fixed');
		} else {
			if(wrap.hasClass('fixed')) wrap.removeClass('fixed');
		}
	}
	if(totalPayment.length) {
		var startPos = totalPayment.offset().top;
		var fixedWrap = totalPayment.find('.inner');
		var endNum = scrollTop + fixedWrap.height() + 110;
		var endPos = footer.offset().top;
		if(scrollTop >= startPos) {
			if(!fixedWrap.hasClass('fixed') && !fixedWrap.hasClass('end')) {
				fixedWrap.addClass('fixed');
			}
		} else {
			if(fixedWrap.hasClass('fixed') && !fixedWrap.hasClass('end')) fixedWrap.removeClass('fixed');
		}
		if(endNum >= endPos) {
			if(fixedWrap.hasClass('fixed') && !fixedWrap.hasClass('end')) fixedWrap.removeClass('fixed').addClass('end');
		} else {
			if(!fixedWrap.hasClass('fixed') && fixedWrap.hasClass('end')) fixedWrap.removeClass('end');
		}
	}
	// if(snb.length) {
	// 	var startPos = snb.offset().top;
	// 	var fixedWrap = snb.find('.inner');
	// 	var endNum = scrollTop + fixedWrap.height() + 110;
	// 	var endPos = footer.offset().top;
	// 	if(scrollTop >= startPos) {
	// 		if(!fixedWrap.hasClass('fixed') && !fixedWrap.hasClass('end')) {
	// 			fixedWrap.addClass('fixed');
	// 		}
	// 	} else {
	// 		if(fixedWrap.hasClass('fixed') && !fixedWrap.hasClass('end')) fixedWrap.removeClass('fixed');
	// 	}
	// 	if(endNum >= endPos) {
	// 		if(fixedWrap.hasClass('fixed') && !fixedWrap.hasClass('end')) fixedWrap.removeClass('fixed').addClass('end');
	// 	} else {
	// 		if(!fixedWrap.hasClass('fixed') && fixedWrap.hasClass('end')) fixedWrap.removeClass('end');
	// 	}
	// }
	if(pdtViewCount.length) {
		var objTop = pdtViewCount.offset().top;
		var objHeight = pdtViewCount.height();
		var startPos = objTop + objHeight;
		if (scrollTop >= startPos) {
			if(!pdtViewCount.hasClass('stikyOn')) pdtViewCount.height(objHeight).addClass('stikyOn');
		} else {
			if(pdtViewCount.hasClass('stikyOn')) pdtViewCount.css('height','').removeClass('stikyOn').find('.pdtOptWrap').attr('style','');
		}
	}
	if(pdtTabWrap.length) {
		var objTop = pdtTabWrap.offset().top;
		if (scrollTop >= objTop) {
			if(!pdtTabWrap.hasClass('fixed')) pdtTabWrap.addClass('fixed');
		} else {
			if(pdtTabWrap.hasClass('fixed')) pdtTabWrap.removeClass('fixed');
		}
	}
}

/* 롤링 */
var rollingSlide = function(obj, dir) {
	var wrap = $(obj);
	var relWrap = wrap.find('> ul');
	var item = relWrap.find('> li');
	var itemW = item.width();
	var resetNum = item.length * itemW;
	var cloneHtml = relWrap.html();
	var pos = 0;
	var timer;
	var style = {};

	relWrap.prepend(cloneHtml + cloneHtml).append(cloneHtml + cloneHtml);
	relWrap.css('margin-left', '-' + resetNum + 'px');

	wrap.active = function(){
		timer = setInterval(function(){
			pos--;
			if('-' + resetNum == pos) {
				style[dir] = 0;
				pos = 0;
			} else {
				style[dir] = pos + 'px';
			}
			relWrap.css(style);
		},45);
	}
	wrap.active();

	relWrap.on({
		'mouseenter': function(){
			clearInterval(timer);
		},
		'mouseleave': function(){
			wrap.active();
		}
	});
}

/* layer */
var scrollState = 0;
var scrollLock = function(state) {
	var html = $('html');
	var wrap = $('#wrap');

	if(state == 'on') {
		scrollState = $(window).scrollTop();
		html.addClass('layerOn');
		wrap.css('margin-top','-'+scrollState+'px');
	} else if(state == 'off') {
		if(html.hasClass('layerOn')) {
			html.removeClass('layerOn');
			wrap.removeAttr('style');
			$(window).scrollTop(scrollState);
			scrollState = 0;
		}
	}
}
var layerOpen = function(id) {

	if($('.layerTooltip').length) $('.layerTooltip').remove();
	if($('html').hasClass('layerOn')) layerClose();

	var layerWrap = $('#'+id);
	var layer = layerWrap.find('.layerWrap');

	if(layerWrap.length) scrollLock('on');

	layerWrap.show();

	if(layerWrap.find('.scrollWrap').length) {
		layerWrap.find('.scrollWrap').niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none', horizrailenabled: false});
	}
	/*layerWrap.off().on('click', function(){
		if(id != 'loginLayer' && id != 'pdtViewQnaWrite' && id != 'myShopChk' && id != 'okPtAlt2' && id != 'apJoinChkLayer') layerClose();
	});*/
	layer.off().on('click', function(){event.stopPropagation();});
}
/*var layerClose = function() {

	var layerWrap = $('.layerPop');

	scrollLock('off');

	layerWrap.hide();

	if($('.layerTooltip').length > 0) $('.layerTooltip').remove();

}*/

var layerClose = function(id) {
	
	var layerWrap = $('.layerPop');

	scrollLock('off');

	layerWrap.hide();

	if($('.layerTooltip').length > 0) $('.layerTooltip').remove();
	
	if(id) {
		if (id == "layCertiPhoneFail") {
			location.reload();
		}
	} 
}

var layerAlert = function(txt) {
	var body = $('body');
	var reTxt = txt.replace(/(\n|\r\n)/g, '<br>');
	var alertLayer = '<div class="layerAlert"><p class="txt">'+reTxt+'</p><button type="button" class="btnType3">확인</button></div>';

	if($('.layerAlert').length > 0) $('.layerAlert').remove();
	body.append(alertLayer);
	var alertWrap = $('.layerAlert');
	var btnClose = alertWrap.find('.btnType3');
	btnClose.focus().off().on('click', function(){alertWrap.remove()});
}

var tostPop = function(txt, type) {
	var body = $('body');
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	var posY = $this.offset().top + $this.outerHeight() + 20;
	var posX = $this.offset().left + $this.outerWidth() / 2;
	var reTxt = txt.replace(/(\n|\r\n)/g, '<br>');
	var tostLayer = '';
	if(type == 'layer') {
		posY = posY - window.scrollY;
		tostLayer = '<div class="tostPop tostTypeLayer"><p class="txt">'+reTxt+'</p></div>';
	} else {
		tostLayer = '<div class="tostPop"><p class="txt">'+reTxt+'</p></div>';
	}

	body.append(tostLayer);
	var tostPop = $('.tostPop');
	posX = posX - tostPop.outerWidth() / 2;
	tostPop.css({'top': posY + 'px', 'left' : posX + 'px'}).fadeIn(300);
	setTimeout(function(){
		tostPop.fadeOut(300, function(){
			tostPop.remove();
		});
	},2000);

}

/* 툴팁 */
var tooltip = function(cls, type) {
	var body = $('body');
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	var posY;
	var posX;
	var tooltipLayer = '<div class="layerTooltip"><div class="cont"></div><button type="button" class="btnTooltipClose">툴팁닫기</button></div>';
	var pos = function(){
		posY = $this.offset().top + 10;
		posX = $this.offset().left + 35;
		if(posX > defaultObj.win.width() - defaultObj.win.width() / 3) {
			posX = $(window).width() - posX + 35;
			tooltipWrap.css({'top': posY + 'px', 'right' : posX + 'px', 'left' : 'auto'});
		} else {
			tooltipWrap.css({'top': posY + 'px', 'left' : posX + 'px', 'right' : 'auto'});
		}
	}
	if($('.' + cls).length == 0) {

		if($('.layerTooltip').length > 0) $('.layerTooltip').remove();

		(type == 'fixed')? $this.parent().append(tooltipLayer) : body.append(tooltipLayer);

		var tooltipWrap = $('.layerTooltip');
		var btnClose = tooltipWrap.find('.btnTooltipClose');

		tooltipWrap.find('.cont').load('/kr/ko/DirectPageToolTip.do?pageName=tooltip .' + cls, function(data, status){
			if(status == 'success') {
				if(type != 'fixed') {
					pos();
					$(window).on('resize', function(){
						pos();
					});
				}
			}
		});
		btnClose.off().on('click', function(){tooltipWrap.remove()});
	}
}

/* 탭 */
var tabActive = function(id, idx) {
	if(id) {
		var $this = $('#'+ id);
		var tab = $this.find(' > ul > li');
		tab.eq(idx).addClass('active').siblings().removeClass('active');
	} else {
		var target = event.currentTarget || event.srcElement;
		var $this = $(target);
		var idx = $this.parent().index();
		$this.parent().addClass('active').siblings().removeClass('active');
	}
	var tabWrap = $this.closest('.tabWrap');
	var tabCont = tabWrap.find('.tabContents .tabCont');
	tabCont.eq(idx).addClass('active').siblings().removeClass('active');
}
var radioTabActive = function(target) {
	if(!target){
		target = event.currentTarget || event.srcElement;
	}
	var $this = $(target);
	var id = $this.val();
	if(id == 'naver'){
		alert('네이버페이 PC 서비스 제공이 어렵습니다.\n모바일웹/APP을 이용해주시거나 다른 결제수단을 선택해주세요.');
		$("input:radio[id='etcList1']").prop("checked", true);
		return false;
	}else{
		var tabWrap = $this.closest('.radioTab').next('.radioTabContents');
		if($('#'+id).length) {
			tabWrap.find('#'+id).addClass('active').siblings().removeClass('active');
		} else {
			tabWrap.find('.radioTabCont').removeClass('active');
		}
	}
}

/* 아코디언 */
var acdActive = function(type) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	if(type == 'clsType') {
		if(!$this.parent().hasClass('active')) {
			$this.parent().addClass('active').siblings().removeClass('active');
		} else {
			$this.parent().removeClass('active');
		}
	} else if(type == 'tblType') {
		if($this.closest('tr').next().hasClass('questionCont')) {
			$this.closest('tr').next('.questionCont').toggleClass('active');
		}
	} else if($this.parent().hasClass('active')) {
		$this.parent().removeClass('active').find('.con').slideUp(200);
	} else {
		$this.next('.con').slideDown(200)
		.parent().addClass('active')
		.siblings().removeClass('active')
		.find('.con').slideUp(200);
	}
}

/* select */
var defaultTxt = [];
var stepOptReset = function(){
	var selBox = $('#pdtOptBox');
	var selBtn = selBox.find('.selTit');
	var allInput = selBox.find('input[type="radio"]');
	selBtn.each(function(idx){
		var $this = $(this);
		$this.html(defaultTxt[idx]);
		if(!selBtn.parent().hasClass('addBox')) $this.prop('disabled', true);
	});

	allInput.prop('checked', false);
	pdtOptFunc();
}
var pdtOptFunc = function(type) {
	var selBox = $('#pdtOptBox');
	var firSel = selBox.find('.selectArea').eq(0).find('.selTit');
	var selBtn = selBox.find('.selTit');
	var input = selBox.find('input[type="radio"]');
	var option = selBox.find('li label');
	var valChange = false;
	var selChk = function(_this) {
		var thisSel = _this.closest('.selectArea');
		var thisSelBtn = thisSel.find('.selTit');
		var txt = _this.parent().find('.optTxt').text();
		thisSelBtn.removeClass('open').next('.selList').slideUp(200);

		if(firSel.data('type') == 'step') { //타입이 스탭일 경우 다음 옵션 박스 오픈
			thisSelBtn.html(txt);
			if(!thisSel.next('.selectArea').hasClass('addBox')) {
				thisSel.next('.selectArea').find('.selTit').prop('disabled', false).addClass('open').next('.selList').slideDown(200);
			}
		}
	}

	//옵션 리셋시 전체 닫힘
	selBtn.removeClass('open').next('.selList').slideUp(200);

	//추가 구성품이 아닐 경우 첫번째 옵션 열린 상태
	//if(!firSel.parent().hasClass('addBox')) {firSel.prop('disabled', false).addClass('open').next('.selList').show();}

	defaultTxt = [];
	selBtn.each(function(){ //옵션 리셋 세팅
		var $this = $(this);
		defaultTxt.push($this.html());
	})

	selBtn.off().on('click', function(){
		var $this = $(this);
		if($this.hasClass('open')) {
			selBtn.removeClass('open').next('.selList').slideUp(200);
		} else {
			selBtn.removeClass('open').next('.selList').slideUp(200);
			$this.addClass('open');
			$this.next('.selList').slideDown(200).find('li:first-child input').focus();
		}
	});

	selBox.on('click', 'label', function(){
		event.stopPropagation();
		if(!$(this).parent().find('input').is(":disabled")) {
			var $this = $(this);
			selChk($this);
		}
	});
	selBox.on('keydown', 'input[type="radio"]', function() {
		if(event.keyCode == 13) {
			var $this = $(this);
			selChk($this);
		}
	});

	if(valChange == true) {
		if(firSel.data('type') == 'step') {
			var nextSel = $this.parent().next('.selectArea').find('.selTit');
			nextSel.prop('disabled', false);
			selActive(nextSel);
		}
	}
}
var stikyMenuOpen = function() {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	$('.pdtOptWrap').slideToggle(200);
	$this.toggleClass('open');
	if(!$('#pdtOptBox .selTit').eq(0).hasClass('open')) $('#pdtOptBox .selTit').eq(0).addClass('open').next('.selList').slideDown(200);
}
var selActive = function() {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	var selWrap = $this.closest('.selectArea');
	var selList = $this.next('.selList');
	var input = selList.find('input[type="radio"]');
	var option = selList.find('li label');

	var selChk = function(txt){
		$this.removeClass('open').html(txt);
		selList.slideUp(200);
	}

	$this.addClass('open');
	selList.slideDown(200).find('li:first-child input').focus();

	input.on('keydown', function(){
		if(event.keyCode == 13) {
			var txt = $(this).next('label').html();
			selChk(txt);
		}
	})
	option.off().on('click', function(){
		if(!$(this).parent().find('input').is(":disabled")) {
			var txt = $(this).html();
			selChk(txt);
		}
	});

	selWrap.off().on('mouseleave', function(){
		selChk();
	});
}

/* 네임별 셀렉트박스 동기화 */
var selChange = function(nameOut, valIn) {
	$('input[name="'+nameOut+'"]').each(function(){
		var $this = $(this);
		$this.prop('checked', false);
		if($this.val() == valIn) {
			outTxt = $this.next('label').text();
			$this.next('label').trigger('click').closest('.selectArea').find('.selTit').text(outTxt);
		}
	});
}


/* 찜하기 레이어 */
var wishSel = function(myshop_yn, myShopRegFl) {
	if (myshop_yn == 'Y' && myShopRegFl != 'Y') {
		//마이샵전용상품일 경우 찜 불가
		return;
	}else{
		var body = $('body');
		var target = event.currentTarget || event.srcElement;
		var $this = $(target);
		var tostCreat = function(txt, cls) {
			var markup = '<div class="layerWish'+cls+'"><p>'+txt+'</p></div>'
			body.append(markup);
			var wishTost = $('[class*="layerWish"]');
			wishTost.fadeIn(500, function(){
				setTimeout(function(){
					wishTost.fadeOut(500, function(){
						wishTost.remove();
					})
				},1000);
			});
		}
		if($this.hasClass('on')) {
			$this.removeClass('on');
			tostCreat('찜 목록에서 삭제되었습니다.', 'Off');
		} else {
			$this.addClass('on');
			tostCreat('찜 목록에 추가되었습니다.', 'On');
		}
	}
}

/* 리뷰 포인트 선택 */
var pointAct = function(obj) {
	var $this = $(obj);
	var idx = $this.parent().index() + 1;
	var pointWrap = $this.closest('.pointSel').find('div');
	pointWrap.attr('class','').addClass('pointSel'+idx);
}

/* 기타 show/hide 스크립트 */
var togglelFunc = function(cls) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	$this.toggleClass(cls).next().slideToggle(200);
}
var giftWrapToggle = function() {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	$this.toggleClass('close').parent().next('.giftList').slideToggle(200);
}
var pdtSort = function(state) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	var sortWrap = $this.closest('.pdtSort');
	var sortBox = sortWrap.find('.pdtSortBox');

	if(event && event.stopPropagation) {
		event.stopPropagation();
	} else if (window.event) {
		window.event.cancelBubble = true;
	}


	var sortClose = function(){
		sortWrap.removeClass('open');
		setTimeout(function(){
			sortBox.find('ul').getNiceScroll().remove();
		},300);
	}
	if(state == 'close') {
		sortClose();
	} else {
		sortWrap.addClass('open');
		sortBox.find('ul').niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none'});
		sortBox.on('click', function(){
			if(event && event.stopPropagation) {
				event.stopPropagation();
			} else if (window.event) {
				window.event.cancelBubble = true;
			}
		});
		defaultObj.doc.off().on('click', function(){
			sortClose();
		});
	}
}
var shareLayer = function(state) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	if(state == 'open') {
		$this.closest('.shareWrap').addClass('open');
	} else if(state == 'close') {
		$this.closest('.shareWrap').removeClass('open');
	}
}
var reivewFilter = function(state) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	var filterWrap = $this.closest('.filter');
	var filterLayer = filterWrap.find('.filterLayer');
	if(state == 'open') {
		filterWrap.addClass('open');
		filterLayer.fadeIn(200);
	} else if(state == 'close') {
		filterWrap.removeClass('open');
		filterLayer.fadeOut(300);
	}
}
var reviewResultShow = function() {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	$this.closest('.reviewSearchResult').toggleClass('open');
}

//스크롤 이동
var scrollMov = function(id) {
	if($(id).length) {
		var objTop = $(id).offset().top;
		$(window).scrollTop(objTop - 63);
	}
}

var mainPopClose = function(state, seq) {
	var popupWrap = $('.mainPopup');
	if(state == 'hide') {$.jutil.setCookie("mainPopup_"+seq, state, 1);}
	popupWrap.hide();
}

defaultObj.win.on({
	'ready' : function(){
		var popupId = $('.mainPopup').data('target');
		if($.jutil.getCookie("mainPopup_"+popupId) != 'hide') {$('.mainPopup').fadeIn(200);}
	},
	'load' : function() {
		/* 인풋체크 전체 */
		if($('.inputChk').length) {
			$('.inputChk input').each(function(){
				var $this = $(this);
				if($this.attr('onclick') || $this.attr('onchange')) {
					var idString = $this.attr('onclick') || $this.attr('onchange');
					if(idString.indexOf('allChk') == 0) {
						var start = idString.indexOf("'");
						var end = idString.lastIndexOf("'");
						allChk(idString.substring(start + 1, end), $this);
					}
				}
			});
		}
	},
	'scroll': function() {
		var scrollTop = defaultObj.doc.scrollTop();
		fixedFunc(scrollTop);
	}
});

/* 인풋 파일 */
var inputFile = function(obj){
	var $this = $(obj);
	var inputF = $this.find('input[type="file"]');
	var inputT = $this.parent().find('input[type="text"]');

	inputF.off().on('change', function(){
		var _this = $(this);
		var tmpStr = _this.val();
		var cnt = 0;
		while(true){
			cnt = tmpStr.indexOf("/");
			if(cnt == -1) break;
			tmpStr = tmpStr.substring(cnt+1);
		}
		while(true){
			cnt = tmpStr.indexOf("\\");
			if(cnt == -1) break;
			tmpStr = tmpStr.substring(cnt+1);
		}

		inputT.val(tmpStr);
	})
}

/* 인풋 전체 체크 */
var allChk = function(id, _this) {
	if(!_this) {
		var target = event.currentTarget || event.srcElement;
		var allChk = $(target);
	} else {
		var allChk = _this;
	}
	var checkbox = $('#'+id).find('input[type="checkbox"]').not(':disabled');
	var chkLeng = checkbox.length;
	var chkState = 0;

	(allChk.is(':checked') == true) ? checkbox.prop('checked', true) : checkbox.prop('checked', false);

	checkbox.off().on('change', function(){
		if(this.checked == false) allChk.prop('checked', false);
		checkbox.each(function(){
			if(this.checked) chkState++;
			if(chkLeng == chkState) allChk.prop('checked', true);
		});
		chkState = 0;
	});
}

/* 제품 수량 */
var qtyCalc = function(state) {
	var target = event.currentTarget || event.srcElement;
	var $this = $(target);
	var pdtQty = $this.parent().find('input.pdtQty');
	var qty = pdtQty.val();
	if(state == 'plus') {
		qty++;
	} else if(state == 'minus' && qty > 1) {
		qty--;
	}
	pdtQty.val(qty).trigger('change');
}

var pdtQtyFunc = function(obj, max) {
	var $this = $(obj);
	if(parseInt($this.val()) > parseInt(max)) {
        alert('이 제품의 1회 최대 구매 가능한 수량은 '+max+'개 입니다.');
        $this.val(max);
    }
}

/* Datepicker */
function initDatepicker(selector, config) {
	var $container = $(selector);

	if ($container.length === 0) return;

	if (!("datepicker" in jQuery.fn)) {
		console.error("No library found named `$.fn.datepicker`.")
		return;
	}

	var defaults = {
		// 현지화
		dateFormat: "yy-mm-dd",
		monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
		dayNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],
		dayNamesShort: ["일","월","화","수","목","금","토"],
		dayNamesMin: ["일","월","화","수","목","금","토"],
		nextText: "다음",
		prevText: "이전",
		currentText: "오늘",
		closeText: "닫기",
		firstDay: 0,
		showMonthAfterYear: true,
		yearSuffix: "년",
		// 그 외 위젯 옵션
		selectOtherMonths: true,
		showButtonPanel: false,
		showOtherMonths: true
	};

	$container.datepicker($.extend(defaults, config));
}
$(function() {
	initDatepicker(".datepicker");
});
/* //Datepicker */

var termsOpen = function(tit,cont,scr) {
	if($('#termsLayer').length) {$('#termsLayer').remove();}
	var body = $('body');
	var html = '';
	html += '<div id="termsLayer" class="layerPop">';
	(scr == 'none') ? html += '<section class="layerWrap" style="width: 960px;">' : html += '<section class="layerWrap scrollLayer" style="width: 960px;">';
	html += '<h3 class="layerTitle">'+tit+'</h3>';
	html += '<div class="layerContents termsLayer">';
	html += '<div class="scrollWrap"></div>'
	html += '</div>';
	html += '<button type="button" class="btnLayerClose" onclick="layerClose();">레이어 팝업 닫기</button>'
	html += '</setion>';
	html += '</div>';
	body.append(html);

	scrollLock('on');
	var termsWrap = $('#termsLayer');

	$.ajax({
		type: 'get',
		url: '/kr/ko/DirectPageTerms.do?pageName='+cont,
		success: function(data) {
			termsWrap.find('.scrollWrap').html(data);
			termsWrap.show();
			if(scr != 'none') termsWrap.find('.scrollWrap').niceScroll({cursorcolor:"#b2b2b2", cursorwidth:2, cursorborder:'none'});
		}
	});
}
function searchProVisionStoreList(pageNo) {
	if(pageNo == null){pageNo = "1";}
	var body = $('body');
	var html = '';
	html += '<div id="storeList" class="layerPop">';
	html += '<section class="layerWrap" style="width: 480px;">';
	html += '<h3 class="layerTitle">이니스프리 MY SHOP 가맹점 리스트</h3>';
	html += '<div id="proVisionStoreList" class="layerContents">';
	html += '</div>';
	html += '<button type="button" class="btnLayerClose" onclick="$(\'#storeList\').remove();layerOpen(\'termsLayer\')">레이어 팝업 닫기</button>'
	html += '</setion>';
	html += '</div>';
	body.append(html);
	layerOpen('storeList');

	$.ajax({
		type:'post',
		data:{
			 pageNo : pageNo,
			 listScale : "10",
			 gubun : "myshop"
		},
		url:'/kr/ko/MemberProVision2StoreList.do',
		success:function(data) {
			$('#proVisionStoreList').html(data);
			layerOpen('storeList');
		}
	});
}

function searchProVisionStoreList2(pageNo) {
	if(pageNo == null){pageNo = "1";}
	var body = $('body');
	var html = '';
	html += '<div id="storeList" class="layerPop">';
	html += '<section class="layerWrap" style="width: 480px;">';
	html += '<h3 class="layerTitle">이니스프리 가맹사업자 리스트</h3>';
	html += '<div id="proVisionStoreList2" class="layerContents">';
	html += '</div>';
	html += '<button type="button" class="btnLayerClose" onclick="$(\'#storeList\').remove();layerOpen(\'termsLayer\')">레이어 팝업 닫기</button>'
	html += '</setion>';
	html += '</div>';
	body.append(html);
	layerOpen('storeList');

	$.ajax({
		type:'post',
		data:{
			 pageNo : pageNo,
			 listScale : "10",
			 gubun : "store"
		},
		url:'/kr/ko/MemberProVision2StoreList.do',
		success:function(data) {
			$('#proVisionStoreList2').html(data);
			layerOpen('storeList');
		}
	});
}

//페이지 로딩
function loadingShow() { $('.loadingArea').show(); }
function loadingHide(obj) { $('.loadingArea').fadeOut(250); }

$(document).on('click','.pdtQty', function(){
	keyChk(this);
});

function keyChk(_this) {
	_this.addEventListener('keyup', function(){
		if((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)) {
			return;
		} else {
			_this.value = _this.value.replace(/[^0-9]/g,'');
		}
	});
}
