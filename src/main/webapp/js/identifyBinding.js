$(function() {
	$('#bindButton').on('click', function() {
		if (!checkInput()) {
			return;
		}
		userBind();
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
	});
})


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
			//verificationCodeFromServer = data.CODE;
			//alert(data.CODE);
			waitingDialog.close();
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

function checkInput() {
	var name = $.trim($('#username').val());
	var mobile = $.trim($('#mobile').val());
	var idCode = $.trim($('#idNO').val());
	var code = $.trim($('#code').val());

	if (name == '') {
		showValidationErrorDialog('名字必须填');
		return false;
	}
	if (idCode == '') {
		showValidationErrorDialog('身份证号必须填');
		return false;
	}
	if (mobile == '') {
		showValidationErrorDialog('手机号码必填');
		return false;
	}
	if (code == '') {
		showValidationErrorDialog('验证码必填');
		return false;
	}
	return true;
}

function userBind() {
	var username = $.trim($('#username').val());
	var mobile = $.trim($('#mobile').val());
	var code = $.trim($('#code').val());
	var idType = $.trim($('#selId').val());
	var idNO = $.trim($('#idNO').val());
	var waitingDialog = showMessageDialogWithoutTimeout("绑定中...请耐心等待");
	$.ajax({
		type : 'POST',
		url : 'rest/onlinestoreresource/bindwechat',
		// data to be added to query string:
		data : {
			"name" : username,
			"mobile" : mobile,
			"idType" : idType,
			"idNO" : idNO,
			"cnfm" : code
		},
		// type of data we are expecting in return:
		dataType : 'text',
		timeout : 30000,
		context : $('body'),
		success : function(data) {
			//alert("data is:" + data);
			waitingDialog.close();
			var r = eval('(' + data + ')');
			if (r.RETCD == 0) {
				window.location.href = "/orderlist.html";
			} else {
				showMessageDialog("服务器端错误, 错误code=" + r.ERRMSG);
			}
		},
		error : function(xhr, type, error) {
			alert('服务端错误! error =' + error);
		}
	});
}