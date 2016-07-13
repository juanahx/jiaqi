package com.alberg.jiaqi.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alberg.jiaqi.exceptions.UserAlreadyBindedException;
import com.alberg.jiaqi.integration.json.BindWechatResponseJson;
import com.alberg.jiaqi.integration.json.CustomerInfoResponseJson;
import com.alberg.jiaqi.integration.json.InsuranceBuyRequestJson;
import com.alberg.jiaqi.integration.json.InsuranceBuyResponseJson;
import com.alberg.jiaqi.integration.json.InsuranceDetailJson;
import com.alberg.jiaqi.integration.json.InsuranceListResponseJson;
import com.alberg.jiaqi.integration.json.MemberResponseJson;
import com.alberg.jiaqi.integration.json.PaymentResponseJson;
import com.alberg.jiaqi.integration.json.SmsResponseJson;
import com.alberg.jiaqi.integration.util.IntegrationUtils;
import com.alberg.jiaqi.persistence.ContracterInfo;
import com.alberg.jiaqi.service.GuotaiPlatformService;
import com.google.gson.Gson;

@Path("onlinestoreresource")
public class OnlineStoreResource {

	private static final Logger logger = LogManager.getLogger(OnlineStoreResource.class);
	@Autowired
	private GuotaiPlatformService guotaiPlatformService; 
		
	/*
    @POST
    @Path("userbind")
    public boolean userBind(@Context HttpServletRequest req, @FormParam("user") String userName, @FormParam("mobile") String mobile,@FormParam("password") String password) {
    	
    	try {
    		String openId = (String) req.getSession().getAttribute("openId");
    		//MemberResponseJson memberResponseJson = IntegrationUtils.getInstance().bind(openId, userName, password);
    		//if (null != memberResponseJson && memberResponseJson.getRETCD() == 0) {
    			guotaiPlatformService.userBind(openId, userName);
    		//}
		} catch (UserAlreadyBindedException e) {
			return false;
		}
 
    	return true;
    }
*/	
	@POST
    @Path("bindwechat")
	public String bindWechat(@Context HttpServletRequest req,
							@FormParam("mobile")String mobileNO,
							@FormParam("name")String name,
							@FormParam("idType")String idType,
							@FormParam("idNO")String idNO,
							@FormParam("cnfm")String cnfm){
		BindWechatResponseJson resp = new BindWechatResponseJson();
		String openId = (String) req.getSession().getAttribute("openId");
		Gson gson = new Gson();
		//openId = "oomSRjhq9fHdJofPCTR9Omv_RL9g";
		if ( StringUtils.isEmpty(openId) ||
				StringUtils.isEmpty(mobileNO) || 
				StringUtils.isEmpty(name) || 
				StringUtils.isEmpty(idType) || 
				StringUtils.isEmpty(idNO) ||
				StringUtils.isEmpty(cnfm) ){
			
			resp.setRETCD(-1);
			resp.setERRMSG("parameters are wrong!");
			return gson.toJson(resp);
		}
		try {
			resp = IntegrationUtils.getInstance(req.getSession()).bindWechat(openId, mobileNO, name, idType, idNO, cnfm);
		} catch (Exception e){
			resp.setRETCD(-1);
			resp.setERRMSG("bind failed!");
			logger.error("[bindWechat] failed!", e);
		}
		
		return gson.toJson(resp);
	}
	

    @POST
    @Path("sendSMS")
    public String sendSMS(@Context HttpServletRequest req, @FormParam("mobile") String mobile) {
		SmsResponseJson smsResponseJson = IntegrationUtils.getInstance(req.getSession()).sendSMS(mobile);
		if (smsResponseJson.getRETCD() != 0) {
			logger.info(smsResponseJson.getRETCD() + ":" + smsResponseJson.getERRMSG());
			throw new GuotaiRestException("发送验证码失败:" + smsResponseJson.getRETCD() + " " + smsResponseJson.getERRMSG());
		}
		Gson gson = new Gson();
		return gson.toJson(smsResponseJson);
    }
    
    @POST
    @Path("login")
    public boolean login(@Context HttpServletRequest req, @FormParam("user") String userName, @FormParam("password") String password) {
		String openId = (String) req.getSession().getAttribute("openId");
		MemberResponseJson memberResponseJson = IntegrationUtils.getInstance(req.getSession()).bind(openId, userName, password);
		if (memberResponseJson.getRETCD() == 0 ){
			try {
				guotaiPlatformService.userBind(openId, userName);
			} catch (UserAlreadyBindedException e) {
				e.printStackTrace();
				throw new GuotaiRestException("登录失败: 该用户已经绑定过");
			}
			return true;
		} else {
			logger.info(memberResponseJson.getERRMSG());
			throw new GuotaiRestException("登录失败:" + memberResponseJson.getRETCD() + " " + memberResponseJson.getERRMSG());
		}
    }

    @POST
    @Path("register")
    public boolean register(@Context HttpServletRequest req, @FormParam("mobile") String mobile, @FormParam("verificationCode") String verificationCode, @FormParam("passwordforregister") String password) {
		String openId = (String) req.getSession().getAttribute("openId");
		MemberResponseJson memberResponseJson = IntegrationUtils.getInstance(req.getSession()).memberRegister(openId, mobile, password, verificationCode);
		if (memberResponseJson.getRETCD() == 0 ) {
			this.login(req, mobile, password);
			return true;
		} else {
			logger.info(memberResponseJson.getERRMSG());
			throw new GuotaiRestException("注册失败:" + memberResponseJson.getRETCD() + " " + memberResponseJson.getERRMSG());
		}
    }
/*
    @POST
    @Path("pay")
    public boolean pay(@Context HttpServletRequest req, @FormParam("orderId") String orderId, @FormParam("prem") double prem, @FormParam("paychannel") String paychannel, @FormParam("payTime") Timestamp payTime, @FormParam("accountDate") Date accountDate, @FormParam("isSuccess") String isSuccess) {
		String openId = (String) req.getSession().getAttribute("openId");
		//IntegrationUtils.getInstance().pay(orderId, openId, prem, paychannel, payTime, accountDate, isSuccess);
		return true;
    }
*/    

    @POST
    @Path("buy")
    public String buy(@Context HttpServletRequest req, @Context HttpServletResponse resp) {
    	try {
    		String openId = (String) req.getSession().getAttribute("openId");
    		//openId = "oomSRjhq9fHdJofPCTR9Omv_RL9g";
    		InsuranceBuyRequestJson buyInfo = (InsuranceBuyRequestJson) req.getSession().getAttribute("buyInfo");
    		String viewID = guotaiPlatformService.saveBuyInfo(buyInfo);
			return this.pay(openId, buyInfo.getPOL_PREM(), viewID, req, resp, req.getSession());
    	} catch (Exception e){
    		logger.error("error happened!", e);
    		Map<String, String> errorMsg = new HashMap<String, String>();
    		errorMsg.put("saveMessage", "failed");
    		errorMsg.put("errorMsg", e.getMessage());
    		Gson gson = new Gson();
    		return gson.toJson(errorMsg);
    	}
		//return IntegrationUtils.getInstance().bind(openId, mobile, password);
		// guotaiPlatformService.userBind(openId, userName);
    }
    

    @POST
    @Path("list")
    public String list(@Context HttpServletRequest req) {
		String openId = (String) req.getSession().getAttribute("openId");
		Gson gson = new Gson();
		InsuranceListResponseJson resp = IntegrationUtils.getInstance(req.getSession()).list(openId);
		req.getSession().setAttribute("orders", resp);
		return gson.toJson(resp);
    }
    
    @POST
    @Path("detail")
    public String detail(@Context HttpServletRequest req, @QueryParam("orderid") Integer orderid) {
    	Integer defaultOrderid = orderid;
    	if (orderid == null){
    		defaultOrderid = 0;
    	}
    	InsuranceListResponseJson resp = (InsuranceListResponseJson) req.getSession().getAttribute("orders");
    	Gson gson = new Gson();
    	if (resp != null){
    		if (resp.getPOL_LIST() != null){
    			for(InsuranceDetailJson detail : resp.getPOL_LIST()){
    				if (detail.getOrderid() == defaultOrderid.intValue()){
    					return gson.toJson(detail);
    				}
    			}
    		}
    	}
    	
		return gson.toJson(new InsuranceDetailJson());    
	}    

    @POST
    @Path("custominfo")
    public String getCustomerInfo(@Context HttpServletRequest req) throws GuotaiRestException {
		String openId = (String) req.getSession().getAttribute("openId");
		Gson gson = new Gson();
//		CustomerInfoResponseJson customerInfoResponseJson = IntegrationUtils.getInstance(req.getSession()).getCustomerInfo(openId);
//		if (customerInfoResponseJson.getRETCD() != 0){
//			throw new GuotaiRestException(customerInfoResponseJson.getERRMSG());
//		}
		// 本地与微信号版绑定的客户信息
		ContracterInfo contractInfo = guotaiPlatformService.queryContracterInfoByOpenId(openId);
		CustomerInfoResponseJson customerInfoResponseJson = new CustomerInfoResponseJson();
		customerInfoResponseJson.setRETCD(0);
		if ( contractInfo != null ){
			customerInfoResponseJson.setBIRTHDAY(DateUtil.formatDate(contractInfo.getBirthday(), "yyyy-MM-dd"));
			customerInfoResponseJson.setCUST_NM(contractInfo.getName());
			customerInfoResponseJson.setEMAIL(contractInfo.getEmail());
			customerInfoResponseJson.setGENDER(contractInfo.getSex() == 1 ? "男":"女");
			customerInfoResponseJson.setID_NO(contractInfo.getIdentitfyNO());
			customerInfoResponseJson.setID_TYPE(String.valueOf(contractInfo.getIdentifyType()));
			customerInfoResponseJson.setMOBILE(contractInfo.getMobile());
		}
		
		return gson.toJson(customerInfoResponseJson);
    }
    
    
    @GET
    @Path("isuserbind")
    public boolean isUserBind(@QueryParam("openId") String openId) {
    	return guotaiPlatformService.isUserBind(openId, "");
    }

    @GET
    @Path("isuserlogin")
    public boolean isUserLogin(@Context HttpServletRequest req) {
    	String openId = null == req.getSession(false) ? null: (String)req.getSession(false).getAttribute("openId");
    	//return guotaiPlatformService.isUserBind(openId, "");
    	return true;
    }

    public String pay(String openId, String prem, String orderId, HttpServletRequest req,  HttpServletResponse resp, HttpSession session ) throws GuotaiRestException {
    	double premDouble = Double.parseDouble(prem);
		Gson gson = new Gson();
		return gson.toJson(PayService.getPrepaidId(openId, premDouble, orderId, req.getRemoteAddr(), session));
    }
    
    @POST
    @Path("checkOrder")
    public String checkOrder(@Context HttpServletRequest req, @FormParam("productId") String productId,
    		@FormParam("issueDate") String issueDate, 
    		@FormParam("issueTime") String issueTime, 
    		@FormParam("days") String days, 
    		@FormParam("amount") String amount, 
    		@FormParam("prem") String prem, 
    		@FormParam("customerName") String customerName, 
    		@FormParam("idType") String idType, 
    		@FormParam("idNumber") String idNumber, 
    		@FormParam("gender") String gender, 
    		@FormParam("birthday") String birthday, 
    		@FormParam("mobile") String mobile, 
    		@FormParam("email") String email, 
    		@FormParam("country") String country, 
    		@FormParam("city") String city,
    		@FormParam("cnfm") String cnfm,
    		@FormParam("jobCategory") String jobCategory,
    		@FormParam("referee") String referee){
    	
    	try {
			String openId = (String) req.getSession().getAttribute("openId");
			String ref = referee == null ? "" : referee;
			InsuranceBuyResponseJson buyResponseJson = IntegrationUtils.getInstance(req.getSession()).buy(openId, productId, issueDate, issueTime, days, amount, prem, customerName, idType, idNumber, gender, birthday, mobile, email, country, city,jobCategory,cnfm, ref);
			Gson gson = new Gson();
			if (buyResponseJson.getRETCD() != 0){
				logger.error("error happened!error = " + buyResponseJson.getERRMSG());
				InsuranceBuyResponseJson resp = new InsuranceBuyResponseJson();
				resp.setRETCD(-1);
				resp.setERRMSG(buyResponseJson.getERRMSG());
				return gson.toJson(resp);
			}
			return gson.toJson(buyResponseJson);
    	} catch (Exception e){
    		logger.error("error happened!", e);
    		InsuranceBuyResponseJson resp = new InsuranceBuyResponseJson();
    		resp.setRETCD(-1);
    		resp.setERRMSG("后台数据取得失败");
    		Gson gson = new Gson();
    		return gson.toJson(resp);
    	}    	
    	
    }
    
    @POST
    @Path("payNotify")
    public String payNotify(@Context HttpServletRequest req,  @Context HttpServletResponse resp) throws GuotaiRestException {
		logger.info("用户微信付款完成后回调");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {

		}
		resp.setCharacterEncoding("UTF-8");
		Map<String, String> requestMap = null;
		try {
			requestMap = PayService.parseXmlForPay(req);
		} catch (Exception e) {
			e.printStackTrace();
			BufferedReader reader;
			try {
				reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
				String line;
				StringBuffer document = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					document.append(line);
				}
				reader.close();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				throw new GuotaiRestException("服务器错误，不支持utf8编码");
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new GuotaiRestException("不能读取从微信服务器返回的信息");
			}
		}
		
		try {
			String return_code = requestMap.get("return_code");
			String attach = requestMap.get("attach");// 商家数据包
			String transaction_id = requestMap.get("transaction_id");// 微信支付订单号
			String orderId = requestMap.get("out_trade_no");//商户订单号
			double prem = Double.parseDouble(requestMap.get("total_fee")) / 100;//total fee
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date payTime = null;
			try {
				payTime = sdf.parse(requestMap.get("time_end"));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new GuotaiRestException("支付成功,但是解析time_end发生错误");
			}
			//String openId = requestMap.get("openId");
			InsuranceBuyRequestJson in = guotaiPlatformService.queryBuyInfoByViewId(orderId);
			logger.info("Pay successfuly");
			logger.info("orderId:" + orderId);
			logger.info("prem:" + prem);
			logger.info("payTime:" + payTime);
			logger.info("openId:" + in.getWCID());
			if ("SUCCESS".equals(return_code)) {// 支付成功
				PaymentResponseJson paymentResponseJson = IntegrationUtils.getInstance(req.getSession()).pay(in.getWCID(), prem, "微信支付", payTime, new Date(), true, in);
				if (paymentResponseJson.getRETCD() == 0) {
					logger.error("支付成功，但是通知服务器失败" + paymentResponseJson.getRETCD() + "： " + paymentResponseJson.getERRMSG());
				}
			} else {
				// 支付失败也记录到服务器
				IntegrationUtils.getInstance(req.getSession()).pay(in.getWCID(), prem, "微信支付", payTime, new Date(), false, in);
				logger.error("支付失败 " + return_code + ":" + requestMap.get("return_msg"));
			}
			return "success";
		} catch (Exception e){
			logger.error("pay notify failed!", e);
		}
		return "failed";
    }
    


    @GET
    @Path("keepAlive")
    public String keepAlive(@Context HttpServletRequest req,  @Context HttpServletResponse resp) {
		logger.info("keep alive");
		String openId = (String) req.getSession().getAttribute("openId");
		IntegrationUtils.getInstance(req.getSession()).getCustomerInfo(openId);
		return "";
    }
}
