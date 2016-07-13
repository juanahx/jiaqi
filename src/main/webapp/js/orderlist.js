$(function() {
	var waitingDialog = showMessageDialogWithoutTimeout("正在获取列表...请耐心等待");
	$.ajax({
		type : 'POST',
		url : 'rest/onlinestoreresource/list',
		data : {
		},
		// type of data we are expecting in return:
		dataType : 'json',
		timeout : 300000,
		context : $('body'),
		success : function(data) {
			//alert("data is:" + data);
			waitingDialog.close();
			if (data.RETCD == 0) {
				 hasCurrent = false;
				 hasHistory = false;
				 if (data.POL_LIST == null || data.POL_LIST.length <= 0){
					 $("#orderCurrentListDiv").append('<li>没有记录</li>');
				 } else {
				 for(var i = 0;i < data.POL_LIST.length; i++){
					 var insuranceDetail = data.POL_LIST[i];
					 	now = new Date();
					 	if ( ((now.getTime() - Date.parse(insuranceDetail.ISSUE_DATE)) > 0 && (Date.parse(insuranceDetail.END_DATE) - now.getTime()) > 0) ||
					 			(now.getTime() - Date.parse(insuranceDetail.ISSUE_DATE) < 0)){
							$("#orderCurrentListDiv").append('<li class="fixed" onclick="goToDetail(' +  insuranceDetail.orderid +')">'
									 + '<p class="g-p fl"><img src="img/pro.jpg" alt="" /></p>'
									+ '<div class="g-d fl">'
									+ '<div class="m-mls fixed">'
									+  '<p class="m1 fl">'
									+  '<span class="m-sp1">' + insuranceDetail.PROD_NAME + '元</span>'
									+  '<span class="m-sp2">'+ insuranceDetail.ISSUE_DATE + ' 至 ' + insuranceDetail.END_DATE + '</span>'
									+  '</p>'
									+  '<p class="m2 fl">'
									+  '<span class="m-sp1">' + insuranceDetail.POL_PREM +'</span>'
									+  '<span class="m-sp2">' + '保障中' +'</span>'
									+  '</p>'
									+ '</div>'
									+ '</div>'
								 + '</li>');
							hasCurrent = true;
					 	} else {
					 		$("#orderhitoryListDiv").append('<li class="fixed" onclick="goToDetail(' +  insuranceDetail.orderid +')">'
									 + '<p class="g-p fl"><img src="img/pro.jpg" alt="" /></p>'
									+ '<div class="g-d fl">'
									+ '<div class="m-mls fixed">'
									+  '<p class="m1 fl">'
									+  '<span class="m-sp1">' + insuranceDetail.PROD_NAME + '</span>'
									+  '<span class="m-sp2">'+ insuranceDetail.ISSUE_DATE + ' 至 ' + insuranceDetail.END_DATE + '</span>'
									+  '</p>'
									+  '<p class="m2 fl">'
									+  '<span class="m-sp1">' + insuranceDetail.POL_PREM +'元</span>'
									+  '<span class="m-sp2 f-sx">' + '已失效' +'</span>'
									+  '</p>'
									+ '</div>'
									+ '</div>'
								 + '</li>');
					 		hasHistory = true;
					 	}
				}
				
				if (!hasCurrent){
					$("#orderCurrentListDiv").append('<li>没有记录</li>');
				}
				 
				if (!hasHistory){
					$("#orderhitoryListDiv").append('<li>没有记录</li>');
				}
				}
						
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

function goToDetail(orderid){
	window.location.href = "/orderdetail.html?orderid=" + orderid;
}