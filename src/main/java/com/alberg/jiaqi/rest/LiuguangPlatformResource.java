package com.alberg.jiaqi.rest;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alberg.jiaqi.rest.weixinmessage.InputMessage;
import com.alberg.jiaqi.rest.weixinmessage.Item;
import com.alberg.jiaqi.rest.weixinmessage.NewsOutputMessage;
import com.alberg.jiaqi.rest.weixinmessage.OutputMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * Root resource (exposed at "liuguangplatformresource" path)
 */
@Path("liuguangplatformresource")
public class LiuguangPlatformResource {

	private static final Logger logger = LogManager.getLogger();
	private final static String TOKEN = "guzhij";
	private final static String MSG_KEY_USERBIND = "USERBIND";
	private final static String MSG_KEY_HOME = "HOME";
	private final static String HOST_URL = "http://103.249.252.210";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String verifyService(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,@QueryParam("nonce") String nonce,@QueryParam("echostr") String echostr) {
    	if (checkSignature(signature, timestamp, nonce, echostr)){
    		return echostr;
    	} else {
    		return "Mismatch";
    	}
    }
    
    public boolean checkSignature(String signature, String timestamp,String nonce, String echostr) {
        logger.info("signature:" + signature);
        logger.info("timestamp:" + timestamp);
        logger.info("nonce:" + nonce);
        logger.info("echostr:" + echostr);

    	String[] signatureList = new String[] {TOKEN, timestamp, nonce};
    	Arrays.sort(signatureList);
    	String tempSignature = "";
    	for (String item : signatureList) {
    		tempSignature = tempSignature + item;
    	}
    	tempSignature = DigestUtils.sha1Hex(tempSignature);
        logger.info("tempSignature:" + tempSignature);
    	
    	if (signature.equals(tempSignature)){
    		return true;
    	} else {
    		return false;
    	}
    }
        
    
    @POST
    public String eventHandler(String event ,@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,@QueryParam("nonce") String nonce,@QueryParam("echostr") String echostr) {
    	if (!checkSignature(signature, timestamp, nonce, echostr)) {
    		return "";
    	}
    	//logger.info("Message:" + message);
    	logger.info("Event:" + event);
    	    	
    	XStream xs = new XStream(new DomDriver());
        xs.alias("xml", InputMessage.class);
        InputMessage inputMessage = (InputMessage) xs.fromXML(event);
        logger.info("Message is:" + inputMessage);
        
        if (MessageType.Event.toString().equals(inputMessage.getMsgType())) {
        	return eventMessageHandler(inputMessage);
        }
    	return "";
    }
    
    private String eventMessageHandler(InputMessage textMessage) {
    	if (MSG_KEY_USERBIND.equals(textMessage.getEventKey()) || MSG_KEY_HOME.equals(textMessage.getEventKey()) ) {
    		NewsOutputMessage newsOutputMessage = new NewsOutputMessage();
    		try {
				setOutputMsgInfo(newsOutputMessage, textMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		newsOutputMessage.setArticleCount(1);
    		List<Item> articles = new ArrayList<Item>();
    		Item item = new Item();
    		if (MSG_KEY_USERBIND.equals(textMessage.getEventKey())) {
	    		item.setDescription("点我用户绑定");
	    		item.setTitle("点我用户绑定");
	    		//item.setUrl(HOST_URL + "/viewIdentificationBind?openId=" + textMessage.getFromUserName());
	    		item.setUrl(HOST_URL + "/loginOnlineStore?act=userBind&openId=" + textMessage.getFromUserName());
    		} else if (MSG_KEY_HOME.equals(textMessage.getEventKey())) {
    	    		item.setDescription("点我进入商城");
    	    		item.setTitle("点我进入商城");
    	    		//item.setUrl(HOST_URL + "/viewIdentificationBind?openId=" + textMessage.getFromUserName());
    	    		item.setUrl(HOST_URL + "/loginOnlineStore?act=home&openId=" + textMessage.getFromUserName());
        	}
    		articles.add(item);
    		newsOutputMessage.setArticles(articles);
    		
    		XStream xstream = new XStream(new XppDriver(){  
                @Override  
                public HierarchicalStreamWriter createWriter(Writer out) {  
                    return new PrettyPrintWriter(out) {  
                        @Override  
                        protected void writeText(QuickWriter writer,  
                                                        String text) {  
                            if (!text.startsWith("<![CDATA[")) {  
                                text = "<![CDATA[" + text + "]]>";  
                            }  
                            writer.write(text);  
                        }  
                    };  
                }  
            });
    		xstream.alias("xml", newsOutputMessage.getClass()); 
    		xstream.alias("item", Item.class);
    		return xstream.toXML(newsOutputMessage); 
    	} 
    	return "";
    }
    
	private static void setOutputMsgInfo(OutputMessage oms, InputMessage msg)
			throws Exception {
		Class<?> outMsg = oms.getClass().getSuperclass();
		Field CreateTime = outMsg.getDeclaredField("CreateTime");
		Field ToUserName = outMsg.getDeclaredField("ToUserName");
		Field FromUserName = outMsg.getDeclaredField("FromUserName");

		ToUserName.setAccessible(true);
		CreateTime.setAccessible(true);
		FromUserName.setAccessible(true);

		CreateTime.set(oms, new Date().getTime());
		ToUserName.set(oms, msg.getFromUserName());
		FromUserName.set(oms, msg.getToUserName());
	}
}
