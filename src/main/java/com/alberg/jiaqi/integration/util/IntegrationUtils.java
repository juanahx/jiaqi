package com.alberg.jiaqi.integration.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alberg.jiaqi.integration.json.BindWechatRequestJson;
import com.alberg.jiaqi.integration.json.BindWechatResponseJson;
import com.alberg.jiaqi.integration.json.CustomerInfoRequestJson;
import com.alberg.jiaqi.integration.json.CustomerInfoResponseJson;
import com.alberg.jiaqi.integration.json.InsuranceBuyRequestJson;
import com.alberg.jiaqi.integration.json.InsuranceBuyResponseJson;
import com.alberg.jiaqi.integration.json.InsuranceDetailJson;
import com.alberg.jiaqi.integration.json.InsuranceListRequestJson;
import com.alberg.jiaqi.integration.json.InsuranceListResponseJson;
import com.alberg.jiaqi.integration.json.IsBindRequestJson;
import com.alberg.jiaqi.integration.json.IsBindResponseJson;
import com.alberg.jiaqi.integration.json.MemberRequestJson;
import com.alberg.jiaqi.integration.json.MemberResponseJson;
import com.alberg.jiaqi.integration.json.PaymentRequestJson;
import com.alberg.jiaqi.integration.json.PaymentResponseJson;
import com.alberg.jiaqi.integration.json.SmsRequestJson;
import com.alberg.jiaqi.integration.json.SmsResponseJson;
import com.google.gson.Gson;

public class IntegrationUtils {

	private static final Logger logger = LogManager.getLogger();
	private Properties guotaiApiProperties = new Properties();
//	private String guotaiApiHost;
//	private String guotaiApiUrlSendSms;
//	private String guotaiApiUrlMemberRegister;
//	private String guotaiApiUrlLogin;
//	private String guotaiApiUrlBind;
//	private String guotaiApiUrlList;
//	private String guotaiApiUrlBuy;
//	private String guotaiApiUrlPay;
//	private String guotaiApiUrlCustomerInfo;
//	private String guotaiapiurlbindWechat;
	private static IntegrationUtils integrationUtils;
	private HttpSession session;
	// 设置默认值
	private String basicUrl = "https://shop.cathaylife.cn/servlet/HttpDispatcher/KAJ0_0210/";
	// keys
	private static final String KEY_BASIC_URL = "guotaiapibasic";
	
	private IntegrationUtils(HttpSession session) {
		this.session = session;

		try {
			guotaiApiProperties.load(getClass().getResourceAsStream("/guotaiapi.properties"));
			basicUrl = guotaiApiProperties.getProperty(KEY_BASIC_URL);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		guotaiApiHost = guotaiApiProperties.getProperty("guotaiapihost");
//		guotaiApiUrlSendSms = guotaiApiProperties.getProperty("guotaiapiurlsendsms");
//		guotaiApiUrlMemberRegister = guotaiApiProperties.getProperty("guotaiapiurlmemberregister");
//		guotaiApiUrlLogin = guotaiApiProperties.getProperty("guotaiapiurllogin");
//		guotaiApiUrlBind = guotaiApiProperties.getProperty("guotaiapiurlbind");
//		guotaiApiUrlList = guotaiApiProperties.getProperty("guotaiapiurllist");
//		guotaiApiUrlBuy = guotaiApiProperties.getProperty("guotaiapiurlcheck");
//		guotaiApiUrlPay = guotaiApiProperties.getProperty("guotaiapiurlpay");
//		guotaiApiUrlCustomerInfo = guotaiApiProperties.getProperty("guotaiapiurlgetcustomerinfo");
//		guotaiapiurlbindWechat = guotaiApiProperties.getProperty("guotaiapiurlbindWechat");
	}
	
	private Client getClient() {
		Client client = ClientBuilder.newClient().register(new RequestSessionFilter(session)).register(new ResponseSessionFilter(session));
		return client;
	}
	/*
	private InsuranceDetailJson createMockupDetail(String productName, String issueDate, String endDate, int polAMT, double price, String customerName, String idType, String idNo, String gendar, String birthday){
		InsuranceDetailJson detail = new InsuranceDetailJson();
		detail.setPROD_NAME(productName);
		detail.setISSUE_DATE(issueDate);
		detail.setEND_DATE(endDate);
		detail.setPOL_AMT(polAMT);
		detail.setPOL_PREM(price);
		detail.setCUST_NM(customerName);
		detail.setID_TYPE(idType);
		detail.setID_NO(idNo);
		detail.setGENDER(gendar);
		detail.setBIRTHDAY(birthday);
		return detail;
	}
	
	private InsuranceListResponseJson createMockupResp(){
		InsuranceListResponseJson resp = new InsuranceListResponseJson();
		InsuranceDetailJson[] details = new InsuranceDetailJson[3];
		details[0] = createMockupDetail("旅行宝意外保障计划(境内游)","2014-7-21","2014-12-21",1,12.0d,"Joe","身份证","310113199809260812","男","1998-09-26");
		details[1] = createMockupDetail("旅行宝意外保障计划(境内游)","2014-7-21","2014-09-21",1,11.0d,"Joe","身份证","310113199809260812","男","1998-09-26");
		details[2] = createMockupDetail("旅行宝意外保障计划(境内游)","2014-7-21","2014-10-21",1,15.0d,"Joe","身份证","310113199809260812","男","1998-09-26");
		resp.setRETCD(0);
		resp.setPOL_LIST(details);
		return resp;
	}
	*/
	private InsuranceListResponseJson genOrderId(InsuranceListResponseJson resp){
		int count = 0;
		if (resp.getPOL_LIST() != null && resp.getPOL_LIST().length > 0 ){
			for (InsuranceDetailJson detail : resp.getPOL_LIST()){
				detail.setOrderid(count);
				count ++;
			}
		}
		return resp;
	}
	
	public SmsResponseJson sendSMS(String mobile) {
		Gson gson = new Gson();
		//Client client = this.getClient();
//		logger.info(guotaiApiHost);
//		logger.info(guotaiApiUrlSendSms);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiApiUrlSendSms);
		//		.queryParam("greeting", "Hi World!");
		 
		SmsRequestJson smsRequestJson = new SmsRequestJson();
		smsRequestJson.setUSNM(mobile);
		smsRequestJson.setSIGN(md5("ljzcxl" + mobile));
		logger.info("Request is:" + formatRequestString(smsRequestJson));
		
		String responseString = ConnectionUtil.post(basicUrl + "sendSMS", formatRequestString(smsRequestJson), "text/plain", session);
		SmsResponseJson bean = gson.fromJson(responseString, SmsResponseJson.class);
		/*
		SmsResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36").header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
		    .post(Entity.entity(formatRequestString(smsRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		SmsResponseJson.class);
		*/    		
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}

	public MemberResponseJson memberRegister(String openId, String username, String password, String confirmation) {
		Gson gson = new Gson();
		//Client client = this.getClient();
//		logger.info(guotaiApiHost);
//		logger.info(guotaiApiUrlMemberRegister);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiApiUrlMemberRegister);
		//		.queryParam("greeting", "Hi World!");
		 
		MemberRequestJson memberRequestJson = new MemberRequestJson();
		memberRequestJson.setPSWD(password);
		memberRequestJson.setSIGN(md5("ljzcxl" + username));
		memberRequestJson.setUSNM(username);
		memberRequestJson.setWCID(openId);
		memberRequestJson.setCNFM(confirmation);
		logger.info("Request is:" + formatRequestString(memberRequestJson));

		String responseString = ConnectionUtil.post(basicUrl + "/register", formatRequestString(memberRequestJson), "text/plain", session);
		MemberResponseJson bean = gson.fromJson(responseString, MemberResponseJson.class);
		/*
		MemberResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(formatRequestString(memberRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		MemberResponseJson.class);
		    		*/
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}
	
	@Deprecated
	public MemberResponseJson login() {
		Gson gson = new Gson();
		Client client = this.getClient();
		logger.info("");
		logger.info("");
		WebTarget target = client.target("").path("");
			//.queryParam("greeting", "Hi World!");
		 
		MemberRequestJson memberRequestJson = new MemberRequestJson();
		memberRequestJson.setPSWD("pSWD");
		memberRequestJson.setSIGN("sIGN");
		memberRequestJson.setUSNM("uSNM");
		memberRequestJson.setWCID("wCID");
		MemberResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(formatRequestString(memberRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		MemberResponseJson.class);
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}	

	public BindWechatResponseJson bindWechat(String openId, String mobile, String userName, String idType, String idNO, String cnfm){
		BindWechatRequestJson request = new BindWechatRequestJson();
		request.setCNFM(cnfm);
		request.setCUST_NM(userName);
		request.setID_NO(idNO);
		request.setID_TYPE(idType);
		request.setSIGN(md5("ljzcxl" + mobile));
		request.setUSNM(mobile);
		request.setWCID(openId);
		//Client client = this.getClient();
		logger.info("request = " + request);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiapiurlbindWechat);
		Gson gson = new Gson();
		String responseString = ConnectionUtil.post(basicUrl + "bindWechat", formatRequestString(request), "text/plain", session);
		return gson.fromJson(responseString, BindWechatResponseJson.class);
	}
	
	@Deprecated
	public MemberResponseJson bind(String openId, String username, String password) {
		Gson gson = new Gson();
		//Client client = this.getClient();
//		logger.info(guotaiApiHost);
//		logger.info(guotaiApiUrlBind);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiApiUrlBind);
		//		.queryParam("greeting", "Hi World!");
		 
		MemberRequestJson memberRequestJson = new MemberRequestJson();
		memberRequestJson.setPSWD(password);
		memberRequestJson.setSIGN(md5("ljzcxl" + username));
		memberRequestJson.setUSNM(username);
		memberRequestJson.setWCID(openId);
		logger.info("Request is:" + formatRequestString(memberRequestJson));

		String responseString = ConnectionUtil.post("http://122.144.142.13/servlet/HttpDispatcher/KAJ0_0200/bindWechat", formatRequestString(memberRequestJson), "text/plain", session);
		MemberResponseJson bean = gson.fromJson(responseString, MemberResponseJson.class);
		/*
		MemberResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(formatRequestString(memberRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		MemberResponseJson.class);
		    		*/
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}	


	public InsuranceListResponseJson list(String openId) {
		Gson gson = new Gson();
		//Client client = this.getClient();
//		logger.info(guotaiApiHost);
//		logger.info(guotaiApiUrlList);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiApiUrlList);
		//		.queryParam("greeting", "Hi World!");
		 
		InsuranceListRequestJson insuranceListRequestJson = new InsuranceListRequestJson();
		insuranceListRequestJson.setWCID(openId);
		insuranceListRequestJson.setPOL_KD("1");
		insuranceListRequestJson.setSIGN(md5("ljzcxl" + openId));

		logger.info("Request is:" + formatRequestString(insuranceListRequestJson));
		String responseString = ConnectionUtil.post(basicUrl + "getPolicies", formatRequestString(insuranceListRequestJson), "text/plain", session);
		InsuranceListResponseJson bean = gson.fromJson(responseString, InsuranceListResponseJson.class);
		// 手动设置ID, 便于查看详细
		if (bean != null){
			bean = genOrderId(bean);
		} else {
			bean = new InsuranceListResponseJson();
		}
		/*
		InsuranceListResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(formatRequestString(insuranceListRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		InsuranceListResponseJson.class);
		    		*/
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}	


	public InsuranceBuyResponseJson buy(String openId, String productId, String issueDate, String issueTime, String days, String amount, String prem, 
			String customerName, String idType, String idNumber, String gender, String birthday, String mobile, String email, String country, String city, String jobCategory, String cnfm, String referee) {
		Gson gson = new Gson();
		//Client client = this.getClient();
//		logger.info(guotaiApiHost);
//		logger.info(guotaiApiUrlBuy);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiApiUrlBuy);
		//		.queryParam("greeting", "Hi World!");
		//String wechatId = "oomSRjghVtNf23kRmWc6xevLbIiE";
		String wechatId = openId;
		InsuranceBuyRequestJson insuranceBuyRequestJson = new InsuranceBuyRequestJson();
		insuranceBuyRequestJson.setWCID(wechatId);
		insuranceBuyRequestJson.setPROD_ID(productId);
		insuranceBuyRequestJson.setTNS_TMSTP("" + new Timestamp(System.currentTimeMillis()));
		insuranceBuyRequestJson.setISSUE_DATE(issueDate);
		insuranceBuyRequestJson.setISSUE_TIME(issueTime);
		insuranceBuyRequestJson.setPOL_DAYS(days);
		insuranceBuyRequestJson.setPOL_AMT(amount);
		insuranceBuyRequestJson.setPOL_PREM(prem);
		insuranceBuyRequestJson.setCUST_NM(customerName);
		insuranceBuyRequestJson.setID_TYPE(idType);
		insuranceBuyRequestJson.setID_NO(idNumber);
		insuranceBuyRequestJson.setGENDER(gender);
		insuranceBuyRequestJson.setBIRTHDAY(birthday);
		insuranceBuyRequestJson.setMOBILE(mobile);
		insuranceBuyRequestJson.setEMAIL(email);
		insuranceBuyRequestJson.setCITY("");
		insuranceBuyRequestJson.setCOUNTRY("");
		insuranceBuyRequestJson.setJOB_CAT(jobCategory);
		insuranceBuyRequestJson.setCNFM(cnfm);
		insuranceBuyRequestJson.setReferee(referee);
		insuranceBuyRequestJson.setSIGN(md5("ljzcxl" + wechatId));
		//logger.info("Request is:" + formatRequestString(insuranceBuyRequestJson));
		logger.info(gson.toJson(insuranceBuyRequestJson));
		session.setAttribute("buyInfo", insuranceBuyRequestJson);
		
		String responseString = ConnectionUtil.post(basicUrl + "buy", formatRequestString(insuranceBuyRequestJson), "text/plain", session);
		InsuranceBuyResponseJson bean = gson.fromJson(responseString, InsuranceBuyResponseJson.class);
		/*
		InsuranceBuyResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(formatRequestString(insuranceBuyRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		InsuranceBuyResponseJson.class);
		    		*/
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}

	public PaymentResponseJson pay(String openId, double prem, String paychannel, Date payTime, Date accountDate, boolean isSuccess, InsuranceBuyRequestJson buyInfo) {
		Gson gson = new Gson();

		 
		PaymentRequestJson paymentRequestJson = new PaymentRequestJson();
		//paymentRequestJson.setIS_SUCCESS("True");
		paymentRequestJson.setWCID(openId);
		//paymentRequestJson.setORD_ID(orderId);
		paymentRequestJson.setPOL_PREM(prem);
		paymentRequestJson.setPAY_CHANNEL(paychannel);

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paymentRequestJson.setPAY_TIME(sdf.format(payTime));

	    sdf = new SimpleDateFormat("yyyy-MM-dd");
		paymentRequestJson.setACNT_DATE(sdf.format(accountDate));
		paymentRequestJson.setIS_SUCCESS(isSuccess? "1" : "0" );
		paymentRequestJson.setSIGN(md5("ljzcxl"+ openId));

		paymentRequestJson.setBIRTHDAY(buyInfo.getBIRTHDAY());
		paymentRequestJson.setCITY(buyInfo.getCITY());
		paymentRequestJson.setCOUNTRY(buyInfo.getCOUNTRY());
		paymentRequestJson.setCUST_NM(buyInfo.getCUST_NM());
		paymentRequestJson.setEMAIL(buyInfo.getEMAIL());
		paymentRequestJson.setGENDER(buyInfo.getGENDER());
		paymentRequestJson.setID_NO(buyInfo.getID_NO());
		paymentRequestJson.setID_TYPE(buyInfo.getID_TYPE());
		paymentRequestJson.setISSUE_DATE(buyInfo.getISSUE_DATE());
		paymentRequestJson.setISSUE_TIME(buyInfo.getISSUE_TIME());
		paymentRequestJson.setJOB_CAT(buyInfo.getJOB_CAT());
		paymentRequestJson.setMOBILE(buyInfo.getMOBILE());
		paymentRequestJson.setPOL_AMT(buyInfo.getPOL_AMT());
		paymentRequestJson.setPOL_DAYS(buyInfo.getPOL_DAYS());
		paymentRequestJson.setPROD_ID(buyInfo.getPROD_ID());
		paymentRequestJson.setTNS_TMSTP(buyInfo.getTNS_TMSTP());
		paymentRequestJson.setENT_CD(buyInfo.getReferee());
		
		logger.info("pay info :" + paymentRequestJson);
		

		String responseString = ConnectionUtil.post(basicUrl + "pay", formatRequestString(paymentRequestJson), "text/plain", session);
		PaymentResponseJson bean = gson.fromJson(responseString, PaymentResponseJson.class);
		
		logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}
	
	private CustomerInfoResponseJson mockupCustomerInfo(){
		CustomerInfoResponseJson resp = new CustomerInfoResponseJson();
		resp.setBIRTHDAY("1998-01-01");
		resp.setCITY("上海");
		resp.setCOUNTRY("中国");
		resp.setCUST_NM("民工");
		resp.setEMAIL("minggong@163.com");
		resp.setGENDER("男");
		resp.setID_NO("310114199801010813");
		resp.setID_TYPE("1");
		resp.setJOB_CAT("jobcat");
		resp.setMOBILE("13524113388");
		resp.setRETCD(0);
		return resp;
	}
	
	public boolean isBindwechat(String openId){
		if (openId == null || openId.isEmpty()){
			throw new RuntimeException("invalid openId");
		}
		IsBindRequestJson request = new IsBindRequestJson();
		request.setWCID(openId);
		request.setSIGN(md5("ljzcxl" + openId));
		String strResponse = ConnectionUtil.post(basicUrl + "isBind", encoding(request), "text/plain", session);
		if ( strResponse == null || strResponse.isEmpty() ){
			throw new RuntimeException("invalid response");
		}
		Gson gson = new Gson();
		IsBindResponseJson bindResponse = gson.fromJson(strResponse, IsBindResponseJson.class);
		if ( bindResponse.getRETCD().intValue() == 0 ){
			return true;
		} 
		logger.info("return false bind response = " + bindResponse);
		
		return false;
	}

	public CustomerInfoResponseJson getCustomerInfo(String openId) {
		Gson gson = new Gson();
		//Client client = this.getClient();
//		logger.info(guotaiApiHost);
//		logger.info(guotaiApiUrlCustomerInfo);
		//WebTarget target = client.target(guotaiApiHost).path(guotaiApiUrlCustomerInfo);
		//		.queryParam("greeting", "Hi World!");
		 
		CustomerInfoRequestJson customerInfoRequestJson = new CustomerInfoRequestJson();
		customerInfoRequestJson.setWCID(openId);
		customerInfoRequestJson.setSIGN(md5("ljzcxl" + openId));
		CustomerInfoResponseJson bean = null;
		//logger.info("Request is:" + formatRequestString(customerInfoRequestJson));
		try {
			String responseString = ConnectionUtil.post(basicUrl + "getCustInfo", formatRequestString(customerInfoRequestJson), "text/plain", session);
			bean = gson.fromJson(responseString, CustomerInfoResponseJson.class);
		} catch (Exception e){
			logger.error("get customer info error!", e);
		}
		//CustomerInfoResponseJson bean = mockupCustomerInfo();
		
		/*
		CustomerInfoResponseJson bean =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(formatRequestString(customerInfoRequestJson),MediaType.TEXT_PLAIN_TYPE),
		    		CustomerInfoResponseJson.class);
		    		*/
		//logger.info("Response is:" + gson.toJson(bean));
		return bean;
	}
	
	public String encoding(Object jsonObject){
		Gson gson = new Gson();
		String gsonStr = gson.toJson(jsonObject);
		logger.info("Request before formatted is:" + gsonStr);

		try {
			return "data=" + URLEncoder.encode(gsonStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "data=" + gson.toJson(jsonObject);
		}
	}
	
	
	public String formatRequestString(Object jsonObject) {
		Gson gson = new Gson();
		String gsonStr = gson.toJson(jsonObject);
		logger.info("Request before formatted is:" + gsonStr);

		try {
			return "data=" + URLEncoder.encode(URLEncoder.encode(gsonStr, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "data=" + gson.toJson(jsonObject);
		}
	}
	
	public static void main(String[] args){
		//IntegrationUtils iu = IntegrationUtils.getInstance();
		//iu.memberRegister("123", "alberg", "alberg password", "confirmation");
		//iu.sendSMS("18049868603");
		//iu.list();
		System.out.println(md5("ljzcxl13524193381"));
		
	}
	
	public static IntegrationUtils getInstance(HttpSession session) {
		if (null == integrationUtils) {
			integrationUtils = new IntegrationUtils(session);
		}
		integrationUtils.session = session;
		return integrationUtils;
	}
	
	public Properties getGuotaiApiProperties() {
		return guotaiApiProperties;
	}

	public void setGuotaiApiProperties(Properties guotaiApiProperties) {
		this.guotaiApiProperties = guotaiApiProperties;
	}

	public static String md5(String input) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(input.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	

}

