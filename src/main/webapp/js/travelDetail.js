$(function() {
	$('#travelDetail').hide();
	$('#assuranceInfo').hide();
	$('#guarantorInfo').hide();
	$('#orderConfirm').hide();
	$('#jubaodiqu').hide();
	$('#policy').hide();
	$('#header').hide();
	if (window.location.hash == '' || window.location.hash == '#detail') {
		$('#travelDetail').show();
		$('#header').show();
	} else if (window.location.hash == '#assurance') {
		$('#assuranceInfo').show();
		$('#header').hide();
	} else if (window.location.hash == '#guarantorInfo') {
		$('#guarantorInfo').show();
		$('#header').hide();
	} else if (window.location.hash == '#orderConfirm') {
		$('#orderConfirm').show();
		$('#header').hide();
	} else if (window.location.hash == '#policy') {
		$('#policy').show();
		$('#header').hide();
	} else if (window.location.hash == '#jubaodiqu'){
		$('#jubaodiqu').show();
		$('#header').hide();
	}
	

	$('#agreecheck').on('click', function() {
		if ($('#agreecheck').attr('class') == 'uncheck') {
			$('#agreecheck').attr('class', 'check');
			$('#showGuarantorInfoButton').removeClass('disable');
		} else if ($('#agreecheck').attr('class') == 'check') {
			$('#agreecheck').attr('class', 'uncheck');
			$('#showGuarantorInfoButton').addClass('disable');
		} 
	});	

	
	
	$('#showDetailButton').on('click', function() {
		window.location.hash = "#detail"; 
	});	

	$('#showAssuranceInfoButton').on('click', function() {
		window.location.hash = "#assurance"; 
	});	
	
	$('#showPolicyButton').on('click', function() {
		window.location.hash = "#policy"; 
	});
	
	$('#showGuarantorInfoButton').on('click', function() {
		if ($('#agreecheck').attr('class') == 'uncheck') {
			return;
		}
		var waitingDialog = showMessageDialogWithoutTimeout("正在获取您的信息...");
		// 直接取用户信息
		$.ajax({
			type : 'POST',
			url : 'rest/onlinestoreresource/custominfo?random='+Math.random(),
			// data to be added to query string:
			data : {},
			// type of data we are expecting in return:
			dataType : 'json',
			timeout : 3000,
			context : $('body'),
			success : function(data) {
				if (data.RETCD == 0 ){
					// 关闭窗口
					waitingDialog.close();
					// 跳转页面
					window.location.hash = "#guarantorInfo";
					// 初始化信息					
					$('#username').val(data.CUST_NM);
					$('#identification').val(data.ID_NO);
					$('#mobile').val(data.MOBILE);
					$('#email').val(data.EMAIL);
					//$('#birthdaySelect').val(data.BIRTHDAY);
					var idType = "8";
					if (data.ID_TYPE=='身份证') {
						idType = "1";
					} else if  (data.ID_TYPE=='军人证') {
						idType = "2";
					} else if  (data.ID_TYPE=='护照') {
						idType = "3";
					} else if  (data.ID_TYPE=='台胞证') {
						idType = "4";
					} else if  (data.ID_TYPE=='港澳通行证') {
						idType = "5";
					} else if  (data.ID_TYPE=='其他') {
						idType = "7";
					}
					// 设置身份证类型
					$("#change-z").val(selectIdentify(idType));
					$("#selId").find("option[value='" + idType + "']").attr("selected",true);
		        	if (idType != "1" && idType != "8" ){
		       			$('#birthdaySelect').css('display','block'); 
		       			$('#gendar').css('display','block');
		       			var now = new Date();
		       			$("#txtbirthday").val(now.format('yyyy-MM-dd'));
		       		    $("#dateBirthday").val(now.format('yyyy-MM-dd'));
		       		    
		       		 	$("#txtGendar").val("男");
		       		 	$("#selGendar").find("option[value='1']").attr("selected",true);
		        	} else {
		    			$('#birthdaySelect').css('display','none'); 
		    			$('#gendar').css('display','none'); 
		    		}
					
				} else {
					showValidationErrorDialog("未预期的错误:数据返回有问题!");
				}
				
			},
			error : function(xhr, type, error) {
				waitingDialog.close();
				 if (null != xhr.responseText) {
					 var errorMessage = $.parseJSON(xhr.responseText).message;
					 showValidationErrorDialog(errorMessage);
				 } else {
				     console.log(arguments);
					 showValidationErrorDialog("未预期的错误:" + error);
				 }
			 
			}			
			
		});		
	});
	
	// 发送验证码
	$('#verificationCodeButton').on('click', function() {
		var mobile = $.trim($('#mobile').val());
		if (mobile == '') {
			showValidationErrorDialog('手机号必填');
			return false;
		}
		if (!isMobile(mobile)){
			showValidationErrorDialog('手机号不合法');
			return false;
		}
		sendSMS();

		s = 59;
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
	
	
	$('#showOrderConfirmButton').on('click', function() {
		var userName = $('#username').val();
		var idNumber = $('#identification').val();
		var birthday = $('#txtbirthday').val();
		var gendar = $('#txtGendar').val();
		var email = $('#email').val();
		var mobile = $('#mobile').val();
		var idType = $('#selId').val();
		var country = "";
		var city = "";
		var jobCategory = "1";		
		var code = $.trim($('#code').val());
		var pageCode = $('#pageCode').val();
		var productId = $('#orderConfirmProductCode').val();
		var issueTime = "00";
		var days = "";		
		var birthday = $("#txtbirthday").val();
		var beginDateStr = $('.J-beginDate').text();
		var issueDate = beginDateStr;
		var endDateStr;
		var dateRange;
				
		// 计算天数
		var now = new Date();
		var payText1 = "";
		var payText2 = "";
		switch (pageCode){
		case "airlinesFullYearDetail": {
			if ( isLeapYear(now.getFullYear())){
				days = "366";
			}
			else {
				days = "365";
			}
			payText1 = "航空意外身故：赔付";
			payText2 = "航空意外伤残：赔付";
			break;
		}
		case "airlinesShortTermDetail": {
			days = "30";
			payText1 = "航空意外身故：赔付";
			payText2 = "航空意外伤残：赔付";
			break;
		}
		case "comprehensiveFullYearDetail": {
			if ( isLeapYear(now.getFullYear())){
				days = "366";
			}
			else {
				days = "365"
			}
			payText1 = "意外身故保障：赔付";
			payText2 = "意外伤残保障：赔付";
			break;
		}
		case "comprehensiveShortTermDetail":{
			days = "30";
			payText1 = "意外身故保障：赔付";
			payText2 = "意外伤残保障：赔付";
			break;
		}
		case "drivingFullYearDetail": {
			if ( isLeapYear(now.getFullYear())){
				days = "366";
			}
			else {
				days = "365"
			}
			payText1 = "驾、乘私家车意外身故：赔付";
			payText2 = "驾、乘私家车意外伤残：赔付";
			break;
		}
		case "trafficComprehensiveDetail": {
			if ( isLeapYear(now.getFullYear())){
				days = "366";
			}
			else {
				days = "365"
			}
			payText1 = "9类交通工具意外身故：赔付";
			payText2 = "9类交通工具意外身故：赔付";
			break;
		}
		case "transitFullYearDetail": {
			if ( isLeapYear(now.getFullYear())){
				days = "366";
			}
			else {
				days = "365"
			}
			payText1 = "公交意外身故：赔付";
			payText2 = "公交意外伤残：赔付";
			break;
		}
		case "travelDomesticDetail": {
			var dateDiff = DateDiff($('.J-endDate').html(), $('.J-beginDate').html());
			days = dateDiff + "";
			break;
		}
			
		case "travelOffshoreDetail": {
			var dateDiff = DateDiff($('.J-endDate').html(), $('.J-beginDate').html());
			days = dateDiff + "";
			break;
		}
		}
		var amount = $('#moneySelect').val();
		var prem = 0;
		if (productId == '' || productId == ''){
			prem = 10;
		} else {
			prem = $('#moneyEm').text();			
		}

		var cnfm = $('#code').val();
		
		//前端检查用户购买参数
		var gender = $('#selGendar').val();
		if (userName == '') {
			showValidationErrorDialog('用户姓名必填');
			return false;
		}
		
		if ( idType == "0" ){
			showValidationErrorDialog('请选择证件类型');
			return false;
		}
		
		if (idNumber == '') {
			showValidationErrorDialog('证件号码必填');
			return false;
		}
		
		if (idType == 1) {
			birthdayAge = getIdCardBirthday(idNumber);
			birthday = birthdayAge.year + "-" + birthdayAge.month + "-" + birthdayAge.day;
			gender = getIdSex(idNumber);
		}
		
		if (idType == "1" && !checkCard(idNumber)) {
			showValidationErrorDialog("身份证不合法");
			return false;
		}
		if (idType != 1 && birthday == '') {
			showValidationErrorDialog('生日必填');
			return false;
		}
		
		if (idType != 1 && birthday != ''){
			if (!checkMyBirthday(birthday)){
				return false;
			}
		}
		
		if (idType != 1 && (gender == '' || gender == '0')) {
			showValidationErrorDialog('性别必填');
			return false;
		}
		
		if (mobile == '') {
			showValidationErrorDialog('手机号码必填');
			return false;
		}
		if (!isMobile(mobile)) {
			showValidationErrorDialog("手机号不合法");
			return false;
		}
		if (code == null || $.trim(code) == ''){
			showValidationErrorDialog("手机验证码必须填");
			return false;
		}
		
		if (email == '') {
			showValidationErrorDialog('电子邮箱必填');
			return false;
		}
		if (!isEmail(email)) {
			showValidationErrorDialog("电子邮箱不合法");
			return false;
		}
		var referee = $('#referee').val();
		
		var waitingDialog = showMessageDialogWithoutTimeout("正在检验中...请耐心等待");
		//调用后端代码校验
		$.ajax({
			type : 'POST',
			url : 'rest/onlinestoreresource/checkOrder',
			// data to be added to query string:
			data : {
				"productId" : productId,
				"issueDate" : issueDate,
				"issueTime" : issueTime,
				"days" : days,
				"amount" : amount,
				"prem" : prem,
				"customerName" : userName,
				"idType" : idType,
				"idNumber" : idNumber,
				"gender" : gender,
				"birthday" : birthday,
				"mobile" : mobile,
				"email" : email,
				"country" : country,
				"city" : city,
				"cnfm" :cnfm,
				"jobCategory" : jobCategory,
				"referee" : referee
			},
			// type of data we are expecting in return:
			dataType : 'json',
			timeout : 300000,
			context : $('body'),
			success : function(data) {
				waitingDialog.close();
				// 后端验证正确，去确认页面
				if(data.RETCD == 0){
					var strType = "身份证";
					if (idType == "1"){
						strType = "身份证";
					} else if (idType == "2"){
						strType = "军人证";
					} else if (idType == "3"){
						strType = "护照";
					} else if (idType == "4"){
						strType = "台胞证";
					} else if (idType == "5"){
						strType = "港澳通行证";
					} else if (idType == "7"){
						strType = "其他";
					}
					
					// 被保人信息
					$('#orderConfirmUsername').text(userName);
					$('#orderConfirmIdentification').text(idNumber);
					$('#orderConfirmIdType').text(strType);
					$('#orderConfirmMobile').text(mobile);
					

					var beginDateStr = $('.J-beginDate').text();
					var endDateStr;
					var dateRange;
					if (null != $('.J-endDate').text()) {
						endDateStr = $('.J-endDate').text();
						dateRange = parseInt(Math.abs(Date.parse(endDateStr.replace(/-/g, "/")) - Date.parse(beginDateStr.replace(/-/g, "/"))) / 1000 / 60 / 60 /24);
						dateRange = dateRange + '天';
						//alert(dateRange);
					} else {
						dateRange = $('#dateRange').text();
						if (dateRange.endWith('天')) {
							endDateStr = new Date(new Date(Date.parse(beginDateStr.replace(/-/g, '/'))).valueOf() + (dateRange.substr(0, dateRange.length - 1))*24*60*60*1000 - 24*60*60*1000);
							endDateStr = endDateStr.format('yyyy-MM-dd');
						} else if (dateRange.endWith('年')) {
							endDateStr = new Date(Date.parse(beginDateStr.replace(/-/g, "/")) - 24 * 60 * 60 * 1000);
							endDateStr.setFullYear(endDateStr.getFullYear() + parseInt(dateRange.substr(0, dateRange.length - 1))); 
							endDateStr = endDateStr.format('yyyy-MM-dd');
						} else if (dateRange.endWith('岁')) {
							endDateStr = new Date(Date.parse(birthday.replace(/-/g, "/")));
							endDateStr.setFullYear(endDateStr.getFullYear() + parseInt(getNum(dateRange))); 
							endDateStr = endDateStr.format('yyyy-MM-dd');	
						}
					}
						
					var orderConfirmEfficentTime = beginDateStr + ' 至 ' + endDateStr;
					$('#orderConfirmEfficentTime').text(orderConfirmEfficentTime);
					$('#orderConfirmEfficentRange').text(dateRange);
					$('#orderConfirmTotal').text($('#moneyEm').text() + '元');
					var p1 = $('#punish1').text();
					var p2 = $('#punish2').text();
					if ( payText1 != "" && payText2 != "" ){
						$('#punish1').text(payText1 + $('#moneySelect').val() + '万元');
						$('#punish2').text(payText2 + $('#moneySelect').val() + '万元');
					} else {
						$('#punish1').text(p1);
						$('#punish2').text(p2);
					}
					window.location.hash = "#orderConfirm";
				} else {
					showValidationErrorDialog(data.ERRMSG);
				}
				
			},
			error : function(xhr, type, error) {
				 waitingDialog.close();
				 if (null != xhr.responseText) {
					 var errorMessage = $.parseJSON(xhr.responseText).message;
					 showValidationErrorDialog(errorMessage);
				 } else {
				     console.log(arguments);
					 showValidationErrorDialog("未预期的错误:" + error);
				 }
			 
			}
		});
		
	});

	$('#identityType').on('selectivity-selected',function(e) {
		if (e.id != 1) {
			$('#birthdaySelect').css('display','block'); 
			$('#gender').css('display','block'); 
		} else {
			$('#birthdaySelect').css('display','none'); 
			$('#gender').css('display','none'); 
		}
	});
   

	$('.selectivity-single-select-input').attr("readonly","readonly");
		
	
	$('#payButton').on('click', function() {
		var waitingDialog = showMessageDialogWithoutTimeout("正在获取您的信息...");
		$.ajax({
			type : 'POST',
			url : 'rest/onlinestoreresource/buy',
			// data to be added to query string:
			data : { "action" : "buy"
			},
			// type of data we are expecting in return:
			dataType : 'json',
			timeout : 300000,
			context : $('body'),
			success : function(data) {
				//alert("data is:" + data);
				waitingDialog.close();
				//var temp = ""; 
				//for(var i in data){
				//	temp += i+":"+data[i]+"\n"; 
				//} 
				//alert(temp);
				
				if(data.saveMessage == 'success'){					
					var appId = data.appId;
					var timestamp = data.timeStamp;
					var nonceStr = data.nonceStr;
					var packages = data.package;
					var finalsign = data.finalsign;
							
					 WeixinJSBridge.invoke('getBrandWCPayRequest',{
					 "appId" : appId,"timeStamp" : timestamp, "nonceStr" :nonceStr, "package" : packages,"signType" : "MD5", "paySign" : finalsign
					}, function(res) {
						//alert(res.err_msg);
						//WeixinJSBridge.log(res.err_msg);
						if (res.err_msg == "get_brand_wcpay_request:ok") {
							showMessageDialog("支付成功");
							window.location.href = "/home.html" ; 
							//WeixinJSBridge.call('closeWindow');
						} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
							showMessageDialog("用户取消支付");
						} else {
							showValidationErrorDialog("支付失败!");							
							//WeixinJSBridge.call('closeWindow');
						}
					});
				} else {
					showValidationErrorDialog(data.errorMsg);
				}
				
			},
			error : function(xhr, type, error) {
				 waitingDialog.close();
				 if (null != xhr.responseText) {
					 var errorMessage = $.parseJSON(xhr.responseText).message;
					 showValidationErrorDialog(errorMessage);
				 } else {
				     console.log(arguments);
					 showValidationErrorDialog("未预期的错误:" + error);;
				 }
			 
			}
		});
		
	});
	//触发click事件
	$('#agreecheck').trigger("click");
	
});

window.onhashchange = function(){
	$('#travelDetail').hide();
	$('#assuranceInfo').hide();
	$('#jubaodiqu').hide();
	$('#guarantorInfo').hide();
	$('#orderConfirm').hide();
	$('#policy').hide();
	if (window.location.hash == '' || window.location.hash == '#detail') {
		$('#travelDetail').show();
		$('#header').show();
	} else if (window.location.hash == '#assurance') {
		$('#assuranceInfo').show();
		$('#header').hide();
	} else if (window.location.hash == '#guarantorInfo') {
		$('#guarantorInfo').show();
		$('#header').hide();
	} else if (window.location.hash == '#orderConfirm') {
		$('#orderConfirm').show();
		$('#header').hide();
	} else if (window.location.hash == '#policy') {
		$('#policy').show();
		$('#header').hide();
	} else if (window.location.hash = '#jubaodiqu'){
		$('#jubaodiqu').show();
		$('#header').hide();
	}
}


function checkValidAge(birthday){
	var pageCode = $('#pageCode').val();
	var minAge = 18;
	var maxAge = 60;
	var age = "0";
	var isNeedDay = false;
	var errorMsg = "";
	switch (pageCode){
	case "airlinesFullYearDetail":
	case "drivingFullYearDetail":
	case "trafficComprehensiveDetail":
	case "transitFullYearDetail": {
		minAge = 18;
		maxAge = 70;
		errorMsg = "此产品的适用年龄在 18岁到70岁之间";
		break;
	}
	case "airlinesShortTermDetail":
	case "comprehensiveFullYearDetail":
	case "comprehensiveShortTermDetail": {
		minAge = 18;
		maxAge = 60;
		errorMsg = "此产品的适用年龄在 18岁到60岁之间";
		break;
	}
	case "travelDomesticDetail": 
	case "travelOffshoreDetail": {
		isNeedDay = true;
		minAge = 0;
		maxAge = 75;
		errorMsg = "此产品的适用年龄在90天到75岁之间";
		break;
	}
	}	
	
	var birthdayTime = parseToAge(birthday);
	var now = new Date();
	var diffYear =  (now.getTime() - birthdayTime.getTime()) / 1000 / 60 / 60 / 24 / 365;
	if ( isNeedDay == false ){
		if ( diffYear >= minAge && diffYear <= maxAge ){
			return true;
		}
	} else {
		var diffDay = (now.getTime() - birthdayTime.getTime()) / 1000 / 60 / 60 / 24;
		if ( diffDay >= 90 && diffYear <= 75 ){
			return true;
		}
	}
	showValidationErrorDialog(errorMsg);
	return false;
}

function checkId(id){
	var idType = $("#selId").val();
	if ( idType == "1" ){
		if (checkCard(id)){
			// 检查年级是否符合当前保险
			age = getIdCardBirthday(id);
			checkValidAge(age);
		} else {
			showValidationErrorDialog("身份证不合法");
		}
	}
}

function checkMyBirthday(birthday){
	var birth = new Date(Date.parse(birthday.replace(/-/g, "/")));
	age = {
			"year" : birth.getFullYear(),
			"month" : birth.getMonth() + 1,
			"day" : birth.getDate()
	}
	return checkValidAge(age);
}

function sendSMS() {
	var mobile = $.trim($('#mobile').val());
	var waitingDialog = showMessageDialogWithoutTimeout("正在发送验证码...请耐心等待");
	$.ajax({
		type : 'POST',
		url : 'rest/onlinestoreresource/sendSMS',
		// data to be added to query string:
		data : {
			"mobile" : mobile	
		},
		// type of data we are expecting in return:
		dataType : 'text',
		timeout : 300000,
		context : $('body'),
		success : function(data) {
			waitingDialog.close();
			var data = eval('(' + data + ')');
			if (data.RETCD != 0) {
				alert(data.ERRMSG);
				return;
			}
			showMessageDialog("验证码已发送");	
			//verificationCodeFromServer = data.CODE;
			//alert(data.CODE);
		},
		/*
		error : function(xhr, type, error) {
			waitingDialog.close();
			alert('Ajax error!:' + error);
			alert("type:" + type);
			alert("error:" + error);
		}
		*/
		 error : function(xhr, status, error) {
			 waitingDialog.close();
			 if (null != xhr.responseText) {
				 var errorMessage = $.parseJSON(xhr.responseText).message;
				 showValidationErrorDialog(errorMessage);
			 } else {
			     console.log(arguments);
				 showValidationErrorDialog("未预期的错误:" + error);;
			 }
		 }
	});

}