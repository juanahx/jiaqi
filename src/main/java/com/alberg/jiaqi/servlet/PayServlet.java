package com.alberg.jiaqi.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import com.alberg.jiaqi.pay.util.GetWxOrderno;
import com.alberg.jiaqi.pay.util.RequestHandler;
import com.alberg.jiaqi.pay.util.Sha1Util;
import com.alberg.jiaqi.pay.util.TenpayUtil;

/**
 * Servlet implementation class PayServlet
 */
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public PayServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


}
