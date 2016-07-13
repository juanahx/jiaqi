package com.alberg.jiaqi.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alberg.jiaqi.integration.util.IntegrationUtils;
import com.alberg.jiaqi.service.GuotaiPlatformService;

@Component
@WebServlet("/loginOnlineStore")
public class OnlineStoreLogin extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(OnlineStoreLogin.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 3302743854234029446L;
	private static final String ACT_USER_BIND = "userBind";
	private static final String ACT_HOME = "home";
	private static final String ACT_MY_INSURANCE = "myinsurance";

	@Autowired
	private GuotaiPlatformService guotaiPlatformService; 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String openId = req.getParameter("openId");
		//openId = "oomSRjllxIkT-wfR5OHfNjRD-VcA";
		if (null == openId) {
			openId = (String)req.getSession().getAttribute("openId");
		}
		if (null == openId || openId.trim().equals("")){
			OutputStreamWriter ow = new OutputStreamWriter(resp.getOutputStream(),"UTF-8");    
			ow.write("Can only be opened in weixin");
			ow.flush();
			return;
		}
		// 保存openId
		req.getSession().setAttribute("openId", openId);
		String act = req.getParameter("act");
		if (ACT_HOME.equals(act)) {
			resp.sendRedirect(URLConstants.HOME_HTML_URL);
		} else if (ACT_USER_BIND.equals(act)) {
			resp.sendRedirect(URLConstants.IDENTIFY_BINDING_VIEW_SERVLET);
		} else if (ACT_MY_INSURANCE.equals(act)) {
			//String userId = guotaiPlatformService.getUserIdByOpenId(openId);
			// 检查是否已经绑定
			//CustomerInfoResponseJson customerInfoResponseJson = IntegrationUtils.getInstance(req.getSession()).getCustomerInfo(openId);
			try {
				if (IntegrationUtils.getInstance(req.getSession()).isBindwechat(openId)) {
					//resp.sendRedirect(URLConstants.IDENTIFY_BINDING_VIEW_SERVLET);
					//该用户尚未绑定，要求其登录到绑定页面
					resp.sendRedirect(URLConstants.MY_INSURANCE_HTML_URL);
				} else {
					resp.sendRedirect(URLConstants.BINDWHECAT_URL);
				}
			} catch (Exception e){
				logger.error("bind failed!", e);
				// 出现错误，导向home url
				resp.sendRedirect(URLConstants.HOME_HTML_URL);	
			}
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

}
