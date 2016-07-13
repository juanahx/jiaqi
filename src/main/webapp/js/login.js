var verificationCodeFromServer;
$(function() {
	$('#loginButton').on('click', function() {
		if (!checkLoginInput()) {
			return;
		}
		login();
	});
	$('#registerButton').on('click', function() {
		if ($('#agreecheck').attr('class') == 'uncheck') {
			return;
		}
		if (!checkRegisterInput()) {
			return;
		}
		register();
	});
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
	

	$('#agreecheck').on('click', function() {
		if ($('#agreecheck').attr('class') == 'uncheck') {
			$('#agreecheck').attr('class', 'check');
			$('#registerButton').removeClass('disable');
		} else if ($('#agreecheck').attr('class') == 'check') {
			$('#agreecheck').attr('class', 'uncheck');
			$('#registerButton').addClass('disable');
		} 
	});	

	
	
})

function sendSMS() {
	var mobile = $.trim($('#mobile').val());
	var waitingDialog = showMessageDialogWithoutTimeout("正在获取验证码...请耐心等待");
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
			//alert("data is:" + data);
			/*
			if (data.RETCD != 0) {
				alert(data.ERRMSG);
				return;
			}*/
			verificationCodeFromServer = data.CODE;
			//alert(verificationCodeFromServer);
			showMessageDialog("验证码已发送");	
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

function checkLoginInput() {
	var user = $.trim($('#user').val());
	var password = $.trim($('#passwordforlogin').val());

	if (user == '') {
		showValidationErrorDialog('用户名必填');
		return false;
	}
	if (password == '') {
		showValidationErrorDialog('密码必填');
		return false;
	}
	return true;
}

function checkRegisterInput() {
	var mobile = $.trim($('#mobile').val());
	var verificationCode = $.trim($('#verificationCode').val());	
	var passwordforregister = $.trim($('#passwordforregister').val());
	var passwordconfirmforregister = $.trim($('#passwordconfirmforregister').val());

	if (mobile == '') {
		showValidationErrorDialog('手机号必填');
		return false;
	}
	if (!isMobile(mobile)){
		showValidationErrorDialog('手机号不合法');
		return false;
	}
	if (verificationCode == '') {
		showValidationErrorDialog('验证码必填');
		return false;
	}
	if (verificationCode != verificationCodeFromServer) {
		showValidationErrorDialog('验证码错误');
		return false;
	}
	if (passwordforregister == '') {
		showValidationErrorDialog('密码必填');
		return false;
	}
	if (passwordconfirmforregister == '') {
		showValidationErrorDialog('密码确认必填');
		return false;
	}
	if (passwordforregister != passwordconfirmforregister) {
		showValidationErrorDialog('密码和密码确认必须一致');
		return false;
	}
	return true;
}

function login() {
	var user = $.trim($('#user').val());
	var password = $.trim($('#passwordforlogin').val());
	var waitingDialog = showMessageDialogWithoutTimeout("登录中...请耐心等待");
	$.ajax({
		type : 'POST',
		url : 'rest/onlinestoreresource/login',
		// data to be added to query string:
		data : {
			"user" : user,
			"password" : password
		},
		// type of data we are expecting in return:
		dataType : 'text',
		timeout : 30000,
		context : $('body'),
		success : function(data) {
			//alert("data is:" + data);
			waitingDialog.close();
			//var data = eval('(' + data + ')');
			if (data == "true") {
				var comeFrom = getQueryString("comefrom");
				//alert("comefrom is:" + comeFrom);
				//alert("Decode comefrom is:" + decodeURIComponent(comeFrom));
				if (null != comeFrom && "" != comeFrom) {alert("a" + comeFrom +"a");
					var timeoutcallback = function() {
						window.location.href = decodeURIComponent(comeFrom);
					}
					showMessageDialog("登录成功", timeoutcallback);	
				} else {
					var timeoutcallback = function() {
						window.location.href = "/home.html";
					}
					showMessageDialog("登录成功,即将去往主页", timeoutcallback);
				}
			} else {
				showMessageDialog("登录失败，请检查用户名密码");
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
}


function register() {	
	var mobile = $.trim($('#mobile').val());
	var verificationCode = $.trim($('#verificationCode').val());	
	var passwordforregister = $.trim($('#passwordforregister').val());
	var passwordconfirmforregister = $.trim($('#passwordconfirmforregister').val());

	var waitingDialog = showMessageDialogWithoutTimeout("注册中...请耐心等待");
	$.ajax({
		type : 'POST',
		url : 'rest/onlinestoreresource/register',
		// data to be added to query string:
		data : {
			"mobile" : mobile,
			"verificationCode": verificationCode,
			"passwordforregister" : passwordforregister,
			"passwordconfirmforregister" : passwordconfirmforregister
		},
		// type of data we are expecting in return:
		dataType : 'text',
		timeout : 300000,
		context : $('body'),
		success : function(data) {
			//alert("data is:" + data);
			waitingDialog.close();
			//var data = eval('(' + data + ')');
			if (data == "true") {
				var comeFrom = getQueryString("comefrom");
				//alert("comefrom is:" + comeFrom);
				//alert("Decode comefrom is:" + decodeURIComponent(comeFrom));
				if (null != comeFrom && "" != comeFrom) {
					//alert("a" + comeFrom +"a");
					var timeoutcallback = function() {
						window.location.href = decodeURIComponent(comeFrom);
					}
					showMessageDialog("注册成功", timeoutcallback);	
				} else {
					var timeoutcallback = function() {
						window.location.href = "/home.html";
					}
					showMessageDialog("注册成功,即将去往主页", timeoutcallback);
				}
			} else {
				showMessageDialog("注册失败，请检查输入");
				;
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
}

/*
function request(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split(
			"&");
	var paraObj = {}
	for (i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j
				.substring(j.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}
	var openId = request("openId");
	if (openId != "") {
		document.getElementById("openId").value = openId;
		//alert("Open Id is :" + document.getElementById("openId").value);
	} else{
		alert("Something is wrong");
	}
	$.ajax({
		  type: 'GET',
		  url: 'rest/onlinestoreresource/isuserbind',
		  // data to be added to query string:
		  data: { openId: openId },
		  // type of data we are expecting in return:
		  dataType: 'text',
		  timeout: 30000,
		  context: $('body'),
		  success: function(data){
			//alert("data is:" + data);
			if (data=="true") {
				$("#useralreadybinddiv").show();
			} else {
				$("#userbinddiv").show();
			}
		  },
		  error: function(xhr, type, error){
		    alert('Ajax error!');
		    alert("type:" + type);
		    alert("error:" + error);
		  }
		});
 */