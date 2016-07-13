package com.alberg.jiaqi.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.alberg.jiaqi.exceptions.UserAlreadyBindedException;
import com.alberg.jiaqi.service.GuotaiPlatformService;

@Component
@WebServlet(urlPatterns = { "/viewIdentificationBind", "/addIdentificationBind" })
public class OnlineStoreController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3302743854234029446L;

	@Autowired
	private GuotaiPlatformService guotaiPlatformService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url = req.getRequestURI();
		String openId = (String) req.getSession().getAttribute("openId");
		if (null == openId) {
			return;
		}
		if (URLConstants.IDENTIFY_BINDING_VIEW_SERVLET.equalsIgnoreCase(url)) {
			boolean isUserBind = guotaiPlatformService.isUserBind(openId, "");
			if (!isUserBind) {
				req.getRequestDispatcher(URLConstants.IDENTIFY_BINDING_HTML_URL)
						.forward(req, resp);
			} else {
				OutputStreamWriter ow = new OutputStreamWriter(resp.getOutputStream(),"UTF-8");    
				ow.write("you have been binded");
				ow.flush();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url = req.getRequestURI();
		String openId = (String) req.getSession().getAttribute("openId");
		if (null == openId) {
			return;
		}
		if (URLConstants.IDENTIFY_BINDING_ADD_SERVLET.equalsIgnoreCase(url)) {
			String userId = (String) req.getParameter("user");
			try {
				guotaiPlatformService.userBind(openId, userId);
				resp.getWriter().write("User bind successfully");
			} catch (UserAlreadyBindedException e) {
				e.printStackTrace();
				resp.getWriter().write("You already been binded, can't be binded again");
			}
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

}
