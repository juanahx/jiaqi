/*
 * main
 * author andy
 * time 2015-04-1
 * update 2015-04-1
 */

var navBar = '<div class="g-nvm J-nav">'
	+ '<div class="m-mus">'
	+ '<ul>'
	+ '<li class="li-4">'
	+ '<a href="/list.html?target=comprehensive">'
	+ '<span class="icn"></span>'
	+ '<span>综合意外</span>'
	+ '</a>'
	+ '</li>'
	+ '<li class="li-3">'
	+ '<a href="/list.html?target=traffic">'
	+ '<span class="icn"></span>'
	+ '<span>交通意外</span>'
	+ '</a>'
	+ '</li>'
	+ '<li class="li-1">'
	+ '<a href="/list.html?target=travel">'
	+ '<span class="icn"></span>'
	+ '<span>旅行平安</span>'
	+ '</a>'
	+ '</li>'
	+ '</ul>'
	+ '</div>'
	+ '</div>';

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

		$(clickEvent).on("click",function(){ $(showDiv).show();});
		$(showDiv).find("li").on("click",function(){
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
			/*
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
				
			},1000);*/
			
		});
		
	},


	init:function(){
		
		$("#navDiv").html(navBar);
		
		var zpSelf = this;

		// tab
		zpSelf.tab();

		//zpSelf.selectEffects(".select-js",".show-div","#change-z");
		
		zpSelf.idCode();
		
		$(".J-sls").on("click","li",function(){
        	if($(this).index() === 1){
        		$("#birthdaySelect").show();
        	}else{
        		$("#birthdaySelect").hide();
        	}
        });
		
		
		// more
		$(".J-more").click(function(){
			$(this).prev().removeClass("p-h54");
		});
		
		//
		var $body = $('body');//$('.J-move');
		function disableScroll(e) {
		    e.preventDefault();
		}

		$(".J-mu").on("click",function(){
			if($(".J-bd").hasClass("g-bdy")){
				$(".J-bd").removeClass("g-bdy");
				$body.off('touchmove', disableScroll);
			}else{
				$(".J-bd").addClass("g-bdy");
				$(".J-trans").show();
				$body.on('touchmove', disableScroll);
			}
			disable_scroll();
			//e.preventDefault();
		});
	
		
		$(".J-trans").on("click",function(){
			$(".J-bd").removeClass("g-bdy");
			$body.off('touchmove', disableScroll);
			$(".J-trans").hide();
			enable_scroll();
			//e.preventDefault();
		});
		
		// 轮播      
		if($(".swiper-container").length) {
		    var mySwiper = new Swiper('.swiper-container', {
				autoplay: 5000,//可选选项，自动滑动
				pagination : '.swiper-pagination',
				paginationBulletRender: function (index, className) {
				      return '<span class="' + className + '"></span>';
				},
				loop : true
			});
		}
		
		// 
		var sHeight = $(window).height() || window.screen.height || screen.height || 736;
	
		if(sHeight !== 0) $(".J-trans,.J-nav").css("height",sHeight);
		
		if($(".J-hmc").length){
			
			var sOffsetTop = $(".J-hmc").offset().top;
			
			$(".J-hmc").css("height", sHeight - parseInt(sOffsetTop) -12);
			
		}
		//产品详情
		if($(".J-vv").length){
			$(".J-vv li").find("span").animate({"bottom":'45%',"opacity":1},2000);
			$(".J-vv li").find("i").animate({"opacity":0.3},2000);
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

function showValidationErrorDialog(message) {
	return $.dialog({
		content : message,
		title : 'alert',
		time : 2000
	});	
}

function showMessageDialog(message, timeoutcallback) {
	return $.dialog({
		content : message,
		title : '',
		time : 2000,
		timeoutcallback : timeoutcallback
	});
}

function showMessageDialogWithoutTimeout(message) {
	return $.dialog({
		content : message,
		title : ''
	});
}




window.onload = function(){
	common.init();
}

Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}


function DateDiff(sDate1, sDate2)
{ 
	var aDate, oDate1, oDate2, iDays;
	aDate = sDate1.split("-");
	oDate1 = new Date(aDate[0],aDate[1]-1,aDate[2]);
	aDate = sDate2.split("-");
	oDate2 = new Date(aDate[0],aDate[1]-1,aDate[2]);
	
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24);  
	if((oDate1 - oDate2)<0){
		return -iDays;
	}
	return iDays; 
}

function dateAdd(sDate, days){
	var aDate = sDate.split("-");
	var tmpDate = new Date(aDate[0],aDate[1]-1,aDate[2]);
	var time = days * 24 * 60 * 60 * 1000;
	var plusDate = new Date(tmpDate.getTime() + time);
	return plusDate;
}

function dateAddExec(date, days){
	var time = days * 24 * 60 * 60 * 1000;
	var plusDate = new Date(date.getTime() + time);
	return plusDate;
}

String.prototype.startWith=function(str){     
  var reg=new RegExp("^"+str);     
  return reg.test(this);        
}  

String.prototype.endWith=function(str){     
  var reg=new RegExp(str+"$");     
  return reg.test(this);        
}

function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
} 

$(function() {
	/*
    var useragent = navigator.userAgent;
    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
        alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
        var opened = window.open('about:blank', '_self');
        opened.opener = null;
        opened.close();
    }

*/

	window.setInterval("keepMeAlive();", 120000);

	$('.travel .fixed').bind('click', function() {
        window.location.href = $(this).find('dd a').attr('href');
    });
	
})


function keepMeAlive(){
	$.ajax({
		type : 'GET',
		url : 'rest/onlinestoreresource/keepAlive',
		// data to be added to query string:
		data : {
		},
		// type of data we are expecting in return:
		dataType : 'text',
		timeout : 300000,
		context : $('body'),
		success : function(data) {},
		error : function(xhr, type, error) {}
	});
}

function isIdCard(cardid) {

	// 身份证正则表达式(18位)

	var isIdCard2 = /^[1-9]\d{5}(19\d{2}|[2-9]\d{3})((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])(\d{4}|\d{3}X)$/i;

	var stard = "10X98765432"; // 最后一位身份证的号码

	var first = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]; // 1-17系数

	var sum = 0;

	if (!isIdCard2.test(cardid)) {

		return false;

	}

	var year = cardid.substr(6, 4);

	var month = cardid.substr(10, 2);

	var day = cardid.substr(12, 2);

	var birthday = cardid.substr(6, 8);

	if (birthday != dateToString(new Date(year + '/' + month + '/' + day))) {// 校验日期是否合法

		return false;

	}

	for (var i = 0; i < cardid.length - 1; i++) {

		sum += cardid[i] * first[i];

	}

	var result = sum % 11;

	var last = stard[result]; // 计算出来的最后一位身份证号码

	if (cardid[cardid.length - 1].toUpperCase() == last) {

		return true;

	} else {

		return false;

	}

}

// 日期转字符串 返回日期格式20080808

function dateToString(date) {

	if (date instanceof Date) {

		var year = date.getFullYear();

		var month = date.getMonth() + 1;

		month = month < 10 ? '0' + month : month;

		var day = date.getDate();

		day = day < 10 ? '0' + day : day;

		return year + "" + month + "" + day;

	}

	return '';

}


function isMobile(value) {
	var isMob = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	if (isMob.test(value)) {
		return true;
	} else {
		return false;
	}
}


function isEmail(value) {
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
	if (!pattern.test(value)) {   
	    return false;  
	}  
	return true;  
}

function getNum(text) {
	var value = text.replace(/[^0-9]/ig,'');
	return value;
}

function getBirthFromId(value) {
    var birth = value.substring(6, 10) + "-" + value.substring(10, 12) + "-" + value.substring(12, 14);
    return birth;
}


function getGenderFromId(value) {
    var gender = value.slice(14, 17) % 2 ? "1" : "2"; 
    return gender;
}

/*******************************   dnot delete me ******************************************/
function setbeignDate(){
	var now = new Date();
	//默认加一天
    var beginDate = new Date(now.valueOf() + 1*24*60*60*1000);
    //$('#beginDate').val(beginDate.format('yyyy-MM-dd'));
    $(".J-beginDate").text(beginDate.format('yyyy-MM-dd'));
    $("#meeting").val(beginDate.format('yyyy-MM-dd'));
}

function setBeginEndDate(){
	var now = new Date();
	//默认加一天
    var beginDate = new Date(now.valueOf() + 1*24*60*60*1000);
    $(".J-beginDate").text(beginDate.format('yyyy-MM-dd'));
    $("#beignMeeting").val(beginDate.format('yyyy-MM-dd'));
    
    var endDate = new Date(now.valueOf() + 2*24*60*60*1000);
    $(".J-endDate").text(endDate.format('yyyy-MM-dd'));
    $("#endMeeting").val(endDate.format('yyyy-MM-dd'));   
}


function selectIdentify(value){
	var text = "";
	switch (value){
	case "1":
		text = "身份证";
		break;
	case "2":
		text = "军人证";
		break;
	case "3":
		text = "护照";
		break;
	case "4":
		text = "台胞证";
		break;
	case "5":
		text = "港澳通行证";
		break;
	case "7":
		text = "其他";
		break;
	default:
	}
	return text;
}

function isLeapYear(iYear) {
	if (iYear % 4 == 0 && iYear % 100 != 0) {
		return true;
	}
	
	if (iYear % 400 == 0) {
		return true;
	}
	
	return false;
}

// 身份证验证
var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",

        21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",

        33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",

        42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",

        51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",

        63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"

       };





checkCard = function(idCard) {
	var card = idCard;
	if(card === '') {
		return false;
	}

	if(isCardNo(card) === false){
		return false;
	}

	if(checkProvince(card) === false){
		return false;
	}

	if(checkBirthday(card) === false){
		return false;
	}

	if(checkParity(card) === false){
		return false;
	}

	return true;
};

isCardNo = function(card){
	var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
	if(reg.test(card) === false){
		return false;
	}
	return true;
};

checkProvince = function(card){
	var province = card.substr(0,2);
	if(vcity[province] == undefined){
		return false;
	}
	return true;
};

checkBirthday = function(card){
	var len = card.length;
	if(len == '15'){
	    var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
	    var arr_data = card.match(re_fifteen);
	    var year = arr_data[2];
	    var month = arr_data[3];
	    var day = arr_data[4];
	    var birthday = new Date('19'+year+'/'+month+'/'+day);
	    return verifyBirthday('19'+year,month,day,birthday);
	}

	if(len == '18'){
	    var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
	    var arr_data = card.match(re_eighteen);
	    var year = arr_data[2];
	    var month = arr_data[3];
	    var day = arr_data[4];
	    var birthday = new Date(year+'/'+month+'/'+day);
	    return verifyBirthday(year,month,day,birthday);
	}
	return false;
};

verifyBirthday = function(year,month,day,birthday){
	var now = new Date();
	var now_year = now.getFullYear();
	if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day){
		var time = now_year - year;
		if(time >= 3 && time <= 100){
			return true;
		}
    return false;
	}
	return false;
};

checkParity = function(card){
	card = changeFivteenToEighteen(card);
	var len = card.length;
	if(len == '18'){
		var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
		var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
		var cardTemp = 0, i, valnum;
		for(i = 0; i < 17; i ++) {
			cardTemp += card.substr(i, 1) * arrInt[i];
		}
		valnum = arrCh[cardTemp % 11];
		if (valnum == card.substr(17, 1)){
			return true;
		}
		return false;
	}
	return false;
};


changeFivteenToEighteen = function(card){
	if(card.length == '15'){
	    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	    var cardTemp = 0, i;  
	    card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
	    for(i = 0; i < 17; i ++){
	        cardTemp += card.substr(i, 1) * arrInt[i];
	    }
	    card += arrCh[cardTemp % 11];
	    return card;
	}
	return card;
};

function getIdSex(idCard){
	if(idCard.length==18){
        sexno=idCard.substring(16,17)
    } else if(idCard.length==15){
        sexno=idCard.substring(14,15)
    } else {
    	return "";
    }
	
	var tempid = sexno % 2;
	var sex = '1';
    if(tempid==0){
        sex= '2';
    }else{
        sex = '1';
    }
	return sex;
}

function getIdCardBirthday(idCard){
	var len = idCard.length;
	if(len == '15'){
		var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
	    var arr_data = idCard.match(re_fifteen);
		var year = arr_data[2];
	    var month = parseInt(arr_data[3]);
	    var day = parseInt(arr_data[4]);
	    year = 1900 + year;
	    return {
	    	"year" : year,
	    	"month" : month,
	    	"day" : day
	    };
	}
	
	if(len == '18'){
	    var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
	    var arr_data = idCard.match(re_eighteen);
	    var year = arr_data[2];
	    var month = parseInt(arr_data[3]);
	    var day = parseInt(arr_data[4]);
	    return  {
	    	"year" : year,
	    	"month" : month,
	    	"day" : day
	    };
	}
	return null;
}

function parseToAge(yMD){
	var date = new Date();
	date.setFullYear(yMD.year);
	date.setMonth(yMD.month - 1);
	date.setDate(yMD.day);
	return date;
}

function formatNummber(src, pos) {  
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);  
}

function formatCurrencyTenThou(num) {  
    num = num.toString().replace(/\$|\,/g,'');  
    if(isNaN(num))  
    num = "0";  
    sign = (num == (num = Math.abs(num)));  
    num = Math.floor(num*10+0.50000000001);  
    cents = num%10;  
    num = Math.floor(num/10).toString();  
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)  
    num = num.substring(0,num.length-(4*i+3))+','+  
    num.substring(num.length-(4*i+3));  
    return (((sign)?'':'-') + num + '.' + cents);  
} 


