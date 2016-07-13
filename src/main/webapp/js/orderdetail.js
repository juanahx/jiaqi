$(function() {
	var waitingDialog = showMessageDialogWithoutTimeout("正在获取列表...请耐心等待");
	var orderid = getQueryString('orderid');
	if (orderid == null){
		orderid = 0;
	}
	$.ajax({
		type : 'POST',
		url : 'rest/onlinestoreresource/detail?orderid=' + orderid,
		data : {
		},
		// type of data we are expecting in return:
		dataType : 'json',
		timeout : 300000,
		context : $('body'),
		success : function(data) {
			//alert("data is:" + data);
			waitingDialog.close();
			if (data.orderid >= 0) {
				time = Date.parse(data.END_DATE) - Date.parse(data.ISSUE_DATE);
				day = time /1000 / 60 / 60 / 24;
				$("#detail1").append('<h1>订单信息</h1>'
						+ '<p>'
						+ '<span>产品名称：</span>'
						+ '<span>' + data.PROD_NAME +'</span>'
						+ '</p>'
						+ '<p>'
						+ '<span>生效时段：</span>'
						+ '<span>' + data.ISSUE_DATE + '至' + data.END_DATE + '（北京时间）</span>'
						+ '</p>'
						+ '<p>'
						+ '<span>保险责任：</span>'
						+ '<span>意外身故或伤残（10万）<br>意外医疗（1万）</span>'
						+ '</p>'
						+ '<p>'
						+ '<span>保险期限：</span>'
						+ '<span>' + day +'天</span>'
						+ '</p>'
						+ '<p>'
						+ '<span>保险合计：</span>'
						+ '<span>' + data.POL_PREM + '</span>'
						+ '</p>'
						);
				
				$("#detail2").append('<h1>投保人暨被保人信息</h1>'
						+ '<p>'
						+ '<span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>'
						+ '<span>' + data.CUST_NM +'</span>'
						+ '</p>'
						+ '<p>'
						+ '<span>证  件  号：</span>'
						+ '<span>' + data.ID_NO + '</span>'
						+ '</p>'
						+ '<p>'
						+ '<span>受  益  人：</span>'
						+ '<span>法定继承人</span>'
						+ '</p>'
						);
				
			} else {
				showMessageDialog("获取保单列表失败:" + ERRMSG);
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

})

function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
} 