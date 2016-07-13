package com.alberg.jiaqi.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.alberg.jiaqi.integration.json.CustomerInfoResponseJson;
import com.alberg.jiaqi.integration.json.InsuranceBuyResponseJson;
import com.alberg.jiaqi.integration.json.InsuranceDetailJson;
import com.alberg.jiaqi.integration.json.InsuranceListResponseJson;
import com.alberg.jiaqi.integration.json.MemberResponseJson;
import com.alberg.jiaqi.integration.json.PaymentResponseJson;
import com.alberg.jiaqi.integration.json.SmsResponseJson;
import com.google.gson.Gson;

@Path("testresource")
public class TestResource {			
    @POST
    @Path("memberRegister")
    public boolean memberRegister(@Context HttpServletRequest req, @FormParam("user") String userName, @FormParam("mobile") String mobile,@FormParam("code") String code) {
    	return true;
    }

    @POST
    @Path("register")
    public String register(String requestString) {
    	System.out.println("Get request:" + requestString);
    	MemberResponseJson mpj = new MemberResponseJson();
    	mpj.setMEB_ID("123456");
    	mpj.setRETCD(0);
    	Gson gson = new Gson();  
    	return gson.toJson(mpj);
    }

    @POST
    @Path("bindWechat")
    public String bind() {
    	MemberResponseJson mpj = new MemberResponseJson();
    	mpj.setMEB_ID("123456");
    	mpj.setRETCD(0);
    	Gson gson = new Gson();  
    	return gson.toJson(mpj);
    }
    @POST
    @Path("getPolicies")
    public String list() {
    	InsuranceListResponseJson insuranceList = new InsuranceListResponseJson();
    	InsuranceDetailJson insuranceDetailJson = new InsuranceDetailJson();
    	//insuranceDetailJson.setBIRTHDAY(new Date());
    	insuranceDetailJson.setCUST_NM("zhangsan");
    	//insuranceDetailJson.setEND_DATE(new Date());
    	insuranceDetailJson.setGENDER("Male");
    	insuranceDetailJson.setID_NO("310107999999");
    	insuranceDetailJson.setID_TYPE("id card");
    	//insuranceDetailJson.setISSUE_DATE(new Date());
    	insuranceDetailJson.setPOL_AMT(100);
    	insuranceDetailJson.setPOL_PREM(200);
    	insuranceDetailJson.setPROD_NAME("insurance product 1");
    	insuranceList.setRETCD(0);
    	insuranceList.setPOL_LIST(new InsuranceDetailJson[]{insuranceDetailJson});
    	Gson gson = new Gson();  
    	return gson.toJson(insuranceList);
    }
    @POST
    @Path("buy")
    public String buy() {
    	InsuranceBuyResponseJson insuranceCheckResponseJson = new InsuranceBuyResponseJson();
    	insuranceCheckResponseJson.setORD_ID("1234567");
    	insuranceCheckResponseJson.setRETCD(0);
    	Gson gson = new Gson(); 
    	return gson.toJson(insuranceCheckResponseJson);
    }
    @POST
    @Path("pay")
    public String pay() {
    	PaymentResponseJson paymentResponseJson = new PaymentResponseJson();
    	paymentResponseJson.setRETCD(0);
    	Gson gson = new Gson(); 
    	return gson.toJson(paymentResponseJson);
    }

    @POST
    @Path("sendSMS")
    public String sendSMS() {
    	SmsResponseJson smsResponseJson = new SmsResponseJson();
    	smsResponseJson.setRETCD(0);
    	smsResponseJson.setCNFM("Alberg");
    	Gson gson = new Gson(); 
    	return gson.toJson(smsResponseJson);
    }

    @POST
    @Path("getCustInfo")
    public String getCustInfo() {
    	CustomerInfoResponseJson customerInfoResponseJson = new CustomerInfoResponseJson();
    	customerInfoResponseJson.setRETCD(0);
    	customerInfoResponseJson.setID_TYPE("身份证");
    	customerInfoResponseJson.setID_NO("3101078721371237");
    	customerInfoResponseJson.setCUST_NM("Alberg");
    	Gson gson = new Gson(); 
    	return gson.toJson(customerInfoResponseJson);
    }
    
}
