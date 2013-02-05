package com.sidways.leap.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paypal.dg.PaypalFunctions;

@Controller
public class CheckAction {

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnURL = "http://www.leap4.net:8088/leapnew/orderconfirm";
		String cancelURL = "http://www.leap4.net:8088/leapnew/cancel";
		Map item = new HashMap();
		item.put("name", "Leap4Net");
		item.put("amt", request.getParameter("price"));
		item.put("qty", "1");
		item.put("outId", request.getParameter("id"));
		PaypalFunctions ppf = new PaypalFunctions();
		HashMap nvp = ppf.setExpressCheckout(request.getParameter("price"), returnURL, cancelURL, item);
		String strAck = nvp.get("ACK").toString();
		if (strAck != null && strAck.equalsIgnoreCase("Success")) {
			String redirectURL = "https://www.paypal.com/incontext?token=" + nvp.get("TOKEN").toString();
			response.sendRedirect(redirectURL);
		} else {
			String ErrorCode = nvp.get("L_ERRORCODE0").toString();
			String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
			String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
			String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
		}
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnURL = "http://www.leap4.net:8088/leapnew/orderconfirm";
		String cancelURL = "http://www.leap4.net:8088/leapnew/cancel";
		Map item = new HashMap();
		item.put("name", "Leap4Net");
		item.put("amt", request.getParameter("price"));
		item.put("qty", "1");
		item.put("outId", request.getParameter("id"));
		PaypalFunctions ppf = new PaypalFunctions();
		HashMap nvp = ppf.setExpressCheckout(request.getParameter("price"), returnURL, cancelURL, item);
		String strAck = nvp.get("ACK").toString();
		if (strAck != null && strAck.equalsIgnoreCase("Success")) {
			String redirectURL = "https://www.paypal.com/incontext?token=" + nvp.get("TOKEN").toString();
			response.sendRedirect(redirectURL);
		} else {
			String ErrorCode = nvp.get("L_ERRORCODE0").toString();
			String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
			String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
			String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
		}
	}
}
