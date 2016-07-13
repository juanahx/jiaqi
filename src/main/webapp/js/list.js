/**
 * 
 */
$(function() {
	var target = getQueryString("target");
	if (target == 'travel') {
		var items = [];
		var item = {
				'href':'/travelDomesticDetail.html',
				'price':'5.0',
				'unit': '元起',
				'name':'境内旅行险',
				'img':'img/national_travel.png',
				'description':'100%给付 | 意外医疗 | 时间任选'};
		items.push(item); 
		item = {
				'href':'/travelOffshoreDetail.html',
				'price':'24.0',
				'unit': '元起',
				'name':'境外旅行险',
				'img':'img/international_traval.png',
				'description':'高额医疗 | 100%给付 | 境外救援'};
		items.push(item);
		var data = {};
		data.categoryName = '旅行平安';
		data.item = items;
		$("#list").bindTemplate({ source: data, template: $("#template-list") }); 
	} else if (target == 'traffic') {
		var items = [];
		var item = {
				'href':'/airlinesShortTermDetail.html',
				'price':'5.0',
				'unit': '元起',
				'name':'短期航空意外险',
				'img':'img/air_acc.png',
				'description':'航空意外 | 国内外都保 | 30天内不限次数'};
		items.push(item); 
		item = {
				'href':'/airlinesFullYearDetail.html',
				'price':'18.0',
				'unit': '元起',
				'name':'全年航空意外险',
				'img':'img/allyear_ari_acc.png',
				'description':'航空意外 | 国内外都保 | 一年内不限次数'};
		items.push(item);
		item = {
				'href':'/drivingFullYearDetail.html',
				'price':'28.0',
				'unit': '元起',
				'name':'全年私家车驾乘险',
				'img':'img/allyear_trip_acc.png',
				'description':'自驾车意外 | 驾乘通赔 | 保障一年'};
		items.push(item);
		item = {
				'href':'/transitFullYearDetail.html',
				'price':'33.0',
				'unit': '元起',
				'name':'全年公共交通出行险',
				'img':'img/traffic_acc.png',
				'description':'交通出行有保障 | 保期一年'};
		items.push(item);
		item = {
				'href':'/trafficComprehensiveDetail.html',
				'price':'314.0',
				'unit': '元',
				'name':'海陆空高端出行保障计划',
				'img':'img/sea_air_land_plan.png',
				'description':'全方位出行保障 | 高额保额 | 保障一年'};
		items.push(item);
		var data = {};
		data.categoryName = '交通意外';
		data.item = items;
		$("#list").bindTemplate({ source: data, template: $("#template-list") }); 
	} else if (target == 'comprehensive') {
		var items = [];
		var item = {
				'href':'/comprehensiveShortTermDetail.html',
				'price':'11.0',
				'unit': '元起',
				'name':'短期综合意外险',
				'img':'img/normal_acc.png',
				'description':'各类意外 | 伤残责任 | 保障30天'};
		items.push(item); 
		item = {
				'href':'/comprehensiveFullYearDetail.html',
				'price':'69.0',
				'unit': '元起',
				'name':'全年综合意外险',
				'img':'img/allyear_normal_acc.png',
				'description':'各类意外 | 伤残责任 | 保障一年'};
		items.push(item);
		var data = {};
		data.categoryName = '综合意外';
		data.item = items;
		$("#list").bindTemplate({ source: data, template: $("#template-list") }); 
	}
	var $body = $('body');//$('.J-move');
	function disableScroll(e) {
	    e.preventDefault();
	}

})
