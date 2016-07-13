/*
 * main
 * author andy
 * time 2015-04-1
 * update 2015-04-1
 */


var common = {


	// tab
	tab:function(){
		var $tab = $(".tab"),
			$tabContainer = $(".tab-cont .tab-container");
		$tab.find("li").eq(0).addClass('active');
		$tab.on("click","li",function(){
			var $index = $(this).index();
			$tab.find("li").removeClass('active').eq($index).addClass('active');
			$tabContainer.hide().eq($index).show();
		});

	},


	// select-js

	selectEffects:function(clickEvent,showDiv,changez){

		$(clickEvent).on("click",function(){ 
			$(showDiv).show();
		});
		$(showDiv).find("li").on("click",function(){
			alert('add');
			var zpText = $(this).text();
			$(showDiv).hide();
			$(changez).val(zpText);
		});

	},
	
	// yzm
	idCode:function(){
		
		var zpYzm = $(".J-dm"),
		
			s = 59,
			
			time = null;
		
		zpYzm.on("click",function(){
			
			var zp = $(this);
			
			zp.attr("disabled",true);
			
			time = setInterval(function(){
				
				if(s == 0){
					
					zp.text("获取验证码");
					
					zp.removeAttr("disabled");
					
					s = 59;
					
					clearInterval(time);
					
				}else{
					
					zp.html(s + "秒重新获取");
					
				}
				
				s--;
				
			},1000);
			
		});
		
	},
	


	init:function(){
		var zpSelf = this;

		// tab
		zpSelf.tab();

		zpSelf.selectEffects(".select-js",".show-div","#change-z");
		
		zpSelf.idCode();
		
		// more
		$(".J-more").click(function(){
			$(this).prev().removeClass("p-h54");
		});
		
		//
		var $body = $('body');//$('.J-move');
		function disableScroll(e) {
		    e.preventDefault();
		}

		$(".J-mu").tap(function(){
			if($(".J-bd").hasClass("g-bdy")){
				$(".J-bd").removeClass("g-bdy");
				$body.off('touchmove', disableScroll);
			}else{
				$(".J-bd").addClass("g-bdy");
				$(".J-trans").show();
				$body.on('touchmove', disableScroll);
			}	
			
			disable_scroll();
			
		});
		
		$(".J-trans").tap(function(){
			$(".J-bd").removeClass("g-bdy");
			$body.off('touchmove', disableScroll);
			$(".J-trans").hide();
			enable_scroll();
		});
		
		// 轮播      
		if($(".swiper-container").length) {
		    var mySwiper = new Swiper('.swiper-container', {
				autoplay: 5000,//可选选项，自动滑动
				pagination : '.swiper-pagination'
			});
		}
		
		// 获取页面高
		var sHeight = $(window).height() || window.screen.height || screen.height || 736;
	
		if(sHeight !== 0) $(".J-trans,.J-nav").css("height",sHeight);
		
		if($(".J-hmc").length){
			
			var sOffsetTop = $(".J-hmc").offset().top;
			
			$(".J-hmc").css("height", sHeight - parseInt(sOffsetTop) -12);
			
		}
			
	}

}

var keys = [37, 38, 39, 40];

function preventDefault(e) {
    e = e || window.event;
    if (e.preventDefault)
        e.preventDefault();
    e.returnValue = false;
}

function keydown(e) {
    for (var i = keys.length; i--;) {
        if (e.keyCode === keys[i]) {
            preventDefault(e);
            return;
        }
    }
}

function wheel(e) {
    preventDefault(e);
}

function disable_scroll() {
    if (window.addEventListener) {
        window.addEventListener('DOMMouseScroll', wheel, false);
    }
    window.onmousewheel = document.onmousewheel = wheel;
    document.onkeydown = keydown;
}

function enable_scroll() {
    if (window.removeEventListener) {
        window.removeEventListener('DOMMouseScroll', wheel, false);
    }
    window.onmousewheel = document.onmousewheel = document.onkeydown = null;
}

window.onload = function(){
	common.init();
}

