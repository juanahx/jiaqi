package com.alberg.jiaqi.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alberg.jiaqi.integration.util.IntegrationUtils;
import com.alberg.jiaqi.pay.util.GetWxOrderno;
import com.alberg.jiaqi.pay.util.RequestHandler;
import com.alberg.jiaqi.pay.util.Sha1Util;
import com.alberg.jiaqi.pay.util.TenpayUtil;

public class PayService {

	private static final Logger logger = LogManager.getLogger();
	/**
	 * @throws Exception 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public static Map<String, String> getPrepaidId(String openId, double prem, String orderId, String ipAddress, HttpSession session){


		Properties guotaiApiProperties = IntegrationUtils.getInstance(session)
				.getGuotaiApiProperties();

		String appid = guotaiApiProperties.getProperty("appid");
		String mch_id = guotaiApiProperties.getProperty("mch_id");
		String paykey = guotaiApiProperties.getProperty("paykey");

		/*
		String code = request.getParameter("code");
		String appsecret = guotaiApiProperties.getProperty("appsecret");
		if (StringUtils.isEmpty(code)) {
			throw new ServletException("Code is empty");
		}
		if (!StringUtils.isEmpty(code)) {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
					+ appid
					+ "&secret="
					+ appsecret
					+ "&code="
					+ code
					+ "&grant_type=authorization_code";
			JSONObject jsonObject = CoreUtil.httpRequest(url, "GET", "");
			openId = jsonObject.getString("openid");
			logger.info("openid--" + openId);
		} else {
			throw new ServletException("can't retrieve openId");
		}
		＊/
		/** 获取用户openid end */

		/** 获取预支付id start */
		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;

		// 子商户号 非必输
		// String sub_mch_id="";
		// 设备号 非必输
		String device_info = "";
		// 随机数
		String nonce_str = strReq;
		// 商品描述
		// String body = describe;

		// 商品描述根据情况修改
		String body = "保险";
		// 附加数据,原样返回
		String attach = "";
		// int intMoney = Integer.parseInt(WIDtotal_fee);

		// 总金额以分为单位，不带小数点
		String total_fee = String
				.valueOf((int) (prem * 100));

		// 订单生成的机器 IP
		String spbill_create_ip = ipAddress;
		if ("0:0:0:0:0:0:0:1".equals(spbill_create_ip)) {
			spbill_create_ip = "127.0.0.1";
		}

		String notify_url = guotaiApiProperties
				.getProperty("guotaipayurlnotify");
		String trade_type = "JSAPI";
		// logger.info("-----------------openid-----------"+openId);

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", orderId);

		// 这里写的金额为1 分到时修改
		// packageParams.put("total_fee", "1");
		packageParams.put("total_fee", total_fee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);

		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);
		packageParams.put("key", paykey);
		

		RequestHandler reqHandler = new RequestHandler();

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<body><![CDATA[" + body + "]]></body>"
				+ "<attach><![CDATA["
				+ attach
				+ "]]></attach>"
				+ "<out_trade_no>"
				+ orderId
				+ "</out_trade_no>"
				+
				// 金额
				"<total_fee>" + total_fee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openId + "</openid>"
				+ "<sign><![CDATA[" + sign + "]]></sign>" + "</xml>";
	    logger.info(xml);

		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		prepay_id = GetWxOrderno.getPayNo(createOrderURL, xml);
		logger.info("prepay_id-----"+prepay_id);
		if (prepay_id.equals("")) {
			throw new GuotaiRestException("支付失败");
			// response.sendRedirect("http://xiaoxiaowen.nat123.net/weixin/cxerp/error.jsp");
		}
		/** 获取预支付id end */

		/** 根据预支付id生成package 生成签名等操作 start */
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonce_str);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		finalpackage.put("key", paykey);
		String finalsign = reqHandler.createSign(finalpackage);
		finalpackage.put("finalsign", finalsign);
		finalpackage.put("saveMessage", "success");
		/** 根据预支付id生成package 生成签名等操作 end */
		return finalpackage;

	}

	/**
	 * 
	 * 方法简要描述: 将微信返回的信息封装成map 创建日期: 2014-12-3 创建人： zw 修改说明：
	 * @throws IOException 
	 * @throws DocumentException 
	 *
	 */
	public static Map<String, String> parseXmlForPay(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

}
