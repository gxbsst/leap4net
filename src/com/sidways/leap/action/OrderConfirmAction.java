package com.sidways.leap.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paypal.dg.PaypalFunctions;

@Controller
public class OrderConfirmAction {

	@RequestMapping(value = "/orderconfirm", method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("token");
		String payerId = request.getParameter("PayerID");

		if (token != null) {
			PaypalFunctions ppf = new PaypalFunctions();
			HashMap nvp = ppf.getPaymentDetails(token);
			String strAck = nvp.get("ACK").toString();
			String finalPaymentAmount = null;
			if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
				finalPaymentAmount = nvp.get("AMT").toString();
			}
			String serverName = request.getServerName();
			String outId = nvp.get("INVNUM").toString();
			nvp = ppf.confirmPayment(token, payerId, finalPaymentAmount, serverName);

			strAck = nvp.get("ACK").toString();

			if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
				String transactionId = nvp.get("PAYMENTINFO_0_TRANSACTIONID").toString();
				String transactionType = nvp.get("PAYMENTINFO_0_TRANSACTIONTYPE").toString();
				String paymentType = nvp.get("PAYMENTINFO_0_PAYMENTTYPE").toString();
				String orderTime = nvp.get("PAYMENTINFO_0_ORDERTIME").toString();
				String amt = nvp.get("PAYMENTINFO_0_AMT").toString();
				String currencyCode = nvp.get("PAYMENTINFO_0_CURRENCYCODE").toString();
				String feeAmt = nvp.get("PAYMENTINFO_0_FEEAMT").toString();
				String taxAmt = nvp.get("PAYMENTINFO_0_TAXAMT").toString();
				String paymentStatus = nvp.get("PAYMENTINFO_0_PAYMENTSTATUS").toString();
				String pendingReason = nvp.get("PAYMENTINFO_0_PENDINGREASON").toString();
				String reasonCode = nvp.get("PAYMENTINFO_0_REASONCODE").toString();
				response.setContentType("text/html");
				response.getWriter().println("<script>\n alert(\"Payment Successful\");\n// add relevant message above or remove the line if not required \n window.onload = function(){\nif(window.opener){\nwindow.close();\n}\nelse{\nif(top.dg.isOpen() == true){\ntop.dg.closeFlow();\nreturn true;\n}\n}\n};\n</script>");
			} else {
				String ErrorCode = nvp.get("L_ERRORCODE0").toString();
				String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
				String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
				String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
				response.getWriter().println("<script>\n alert(\"Payment Failed\");\n// add relevant message above or remove the line if not required \n window.onload = function(){\nif(window.opener){\nwindow.close();\n}\nelse{\nif(top.dg.isOpen() == true){\ntop.dg.closeFlow();\nreturn true;\n}\n}\n};\n</script>");
			}
		}
	}
	
	@RequestMapping(value = "/orderconfirm", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String token = request.getParameter("token");
		String payerId = request.getParameter("PayerID");

		if (token != null) {
			PaypalFunctions ppf = new PaypalFunctions();
			HashMap nvp = ppf.getPaymentDetails(token);
			String strAck = nvp.get("ACK").toString();
			String finalPaymentAmount = null;
			if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
				finalPaymentAmount = nvp.get("AMT").toString();
			}
			String serverName = request.getServerName();
			String outId = nvp.get("INVNUM").toString();
			nvp = ppf.confirmPayment(token, payerId, finalPaymentAmount, serverName);

			strAck = nvp.get("ACK").toString();

			if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
				String transactionId = nvp.get("PAYMENTINFO_0_TRANSACTIONID").toString();
				String transactionType = nvp.get("PAYMENTINFO_0_TRANSACTIONTYPE").toString();
				String paymentType = nvp.get("PAYMENTINFO_0_PAYMENTTYPE").toString();
				String orderTime = nvp.get("PAYMENTINFO_0_ORDERTIME").toString();
				String amt = nvp.get("PAYMENTINFO_0_AMT").toString();
				String currencyCode = nvp.get("PAYMENTINFO_0_CURRENCYCODE").toString();
				String feeAmt = nvp.get("PAYMENTINFO_0_FEEAMT").toString();
				String taxAmt = nvp.get("PAYMENTINFO_0_TAXAMT").toString();
				String paymentStatus = nvp.get("PAYMENTINFO_0_PAYMENTSTATUS").toString();
				String pendingReason = nvp.get("PAYMENTINFO_0_PENDINGREASON").toString();
				String reasonCode = nvp.get("PAYMENTINFO_0_REASONCODE").toString();
				response.setContentType("text/html");
				response.getWriter().println("<script>\n alert(\"Payment Successful\");\n// add relevant message above or remove the line if not required \n window.onload = function(){\nif(window.opener){\nwindow.close();\n}\nelse{\nif(top.dg.isOpen() == true){\ntop.dg.closeFlow();\nreturn true;\n}\n}\n};\n</script>");
			} else {
				String ErrorCode = nvp.get("L_ERRORCODE0").toString();
				String ErrorShortMsg = nvp.get("L_SHORTMESSAGE0").toString();
				String ErrorLongMsg = nvp.get("L_LONGMESSAGE0").toString();
				String ErrorSeverityCode = nvp.get("L_SEVERITYCODE0").toString();
				response.getWriter().println("<script>\n alert(\"Payment Failed\");\n// add relevant message above or remove the line if not required \n window.onload = function(){\nif(window.opener){\nwindow.close();\n}\nelse{\nif(top.dg.isOpen() == true){\ntop.dg.closeFlow();\nreturn true;\n}\n}\n};\n</script>");
			}
		}
	}
}
