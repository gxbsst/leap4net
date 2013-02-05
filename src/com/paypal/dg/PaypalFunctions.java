package com.paypal.dg;

/**
 * Created by IntelliJ IDEA.
 * User: lhuynh
 * Date: Dec 6, 2007
 * Time: 5:06:52 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

public class PaypalFunctions {

	private String gv_APIUserName;
	private String gv_APIPassword;
	private String gv_APISignature;

	private String gv_APIEndpoint;
	private String gv_BNCode;

	private String gv_Version;
	private String gv_nvpHeader;
	private String gv_ProxyServer;
	private String gv_ProxyServerPort;
	private int gv_Proxy;
	private boolean gv_UseProxy;
	private String PAYPAL_URL;

	public PaypalFunctions() {// lhuynh - Actions to be Done on init of this
								// class

		// Replace <API_USERNAME> with your API Username
		// Replace <API_PASSWORD> with your API Password
		// Replace <API_SIGNATURE> with your Signature
//		gv_APIUserName = "test2_1356319825_biz_api1.sidways.com";
//		gv_APIPassword = "1356319845";
//		gv_APISignature = "An5ns1Kso7MWUdW4ErQKJJJ4qi4-AocZDCOBqHkdTuMA3quFqSyX5ilB";
		gv_APIUserName = "shannon.mao_api1.sidways.com";
		gv_APIPassword = "GTTLV7EU3FKQL24R";
		gv_APISignature = "AgM87ksEY.nObXpbAeFgxhp17oRTAsk8QHXPYf2cSq3hYezW3SyNP0zP";

		boolean bSandbox = false;

		/*
		 * Servers for NVP API Sandbox: https://api-3t.sandbox.paypal.com/nvp
		 * Live: https://api-3t.paypal.com/nvp
		 */

		/*
		 * Redirect URLs for PayPal Login Screen Sandbox:
		 * https://www.sandbox.paypal
		 * .com/webscr&cmd=_express-checkout&token=XXXX Live:
		 * https://www.paypal.
		 * com/cgi-bin/webscr?cmd=_express-checkout&token=XXXX
		 */
		String PAYPAL_DG_URL = null;
		if (bSandbox == true) {
			gv_APIEndpoint = "https://api-3t.sandbox.paypal.com/nvp";
			PAYPAL_URL = "https://www.sandbox.paypal.com/webscr?cmd=_express-checkout&token=";
			PAYPAL_DG_URL = "https://www.sandbox.paypal.com/incontext?token=";
		} else {
			gv_APIEndpoint = "https://api-3t.paypal.com/nvp";
			PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
			PAYPAL_DG_URL = "https://www.paypal.com/incontext?token=";
		}

		String HTTPREQUEST_PROXYSETTING_SERVER = "";
		String HTTPREQUEST_PROXYSETTING_PORT = "";
		boolean USE_PROXY = false;

		gv_Version = "84";

		// WinObjHttp Request proxy settings.
		gv_ProxyServer = HTTPREQUEST_PROXYSETTING_SERVER;
		gv_ProxyServerPort = HTTPREQUEST_PROXYSETTING_PORT;
		gv_Proxy = 2; // 'setting for proxy activation
		gv_UseProxy = USE_PROXY;

	}

	/*********************************************************************************
	 * SetExpressCheckout: Function to perform the SetExpressCheckout API call
	 * 
	 * Inputs: paymentAmount: Total value of the purchase currencyCodeType:
	 * Currency code value the PayPal API paymentType: 'Sale' for Digital Goods
	 * returnURL: the page where buyers return to after they are done with the
	 * payment review on PayPal cancelURL: the page where buyers return to when
	 * they cancel the payment review on PayPal
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	public HashMap setExpressCheckout(String paymentAmount, String returnURL, String cancelURL, Map item) {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */

		String currencyCodeType = "USD";
		String paymentType = "Sale";

		/*
		 * Construct the parameter string that describes the PayPal payment the
		 * varialbes were set in the web form, and the resulting string is
		 * stored in $nvpstr
		 */
		String nvpstr = "&PAYMENTREQUEST_0_AMT=" + paymentAmount + "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType + "&RETURNURL=" + URLEncoder.encode(returnURL) + "&CANCELURL=" + URLEncoder.encode(cancelURL) + "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType + "&REQCONFIRMSHIPPING=0"
				+ "&NOSHIPPING=1" + "&L_PAYMENTREQUEST_0_NAME0=" + item.get("name") + "&L_PAYMENTREQUEST_0_AMT0=" + item.get("amt") + "&L_PAYMENTREQUEST_0_QTY0=" + item.get("qty") + "&PAYMENTREQUEST_0_INVNUM=" + item.get("outId") + "&L_PAYMENTREQUEST_0_ITEMCATEGORY0=Digital";

		/*
		 * Make the call to PayPal to get the Express Checkout token If the API
		 * call succeded, then redirect the buyer to PayPal to begin to
		 * authorize payment. If an error occured, show the resulting errors
		 */

		HashMap nvp = httpcall("SetExpressCheckout", nvpstr);

		String strAck = nvp.get("ACK").toString();
		if (strAck != null && strAck.equalsIgnoreCase("Success")) {

			return nvp;
		}

		return null;
	}

	/*********************************************************************************
	 * GetShippingDetails: Function to perform the GetExpressCheckoutDetails API
	 * call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	public HashMap getPaymentDetails(String token) {
		/*
		 * Build a second API request to PayPal, using the token as the ID to
		 * get the details on the payment authorization
		 */

		String nvpstr = "&TOKEN=" + token;

		/*
		 * Make the API call and store the results in an array. If the call was
		 * a success, show the authorization details, and provide an action to
		 * complete the payment. If failed, show the error
		 */

		HashMap nvp = httpcall("GetExpressCheckoutDetails", nvpstr);
		String strAck = nvp.get("ACK").toString();

		if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {

			return nvp;
		}
		return null;
	}

	/*********************************************************************************
	 * ConfirmPayment: Function to perform the DoExpressCheckoutPayment API call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	public HashMap confirmPayment(String token, String payerID, String finalPaymentAmount, String serverName) {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */
		String currencyCodeType = "USD";
		String paymentType = "Sale";

		/*
		 * '----------------------------------------------------------------------------
		 * '---- Use the values stored in the session from the previous SetEC
		 * call
		 * '----------------------------------------------------------------------------
		 */
		String nvpstr = "&TOKEN=" + token + "&PAYERID=" + payerID + "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType + "&PAYMENTREQUEST_0_AMT=" + finalPaymentAmount;

		nvpstr = nvpstr + "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType + "&IPADDRESS=" + serverName;

		/*
		 * Make the call to PayPal to finalize payment If an error occured, show
		 * the resulting errors
		 */
		HashMap nvp = httpcall("DoExpressCheckoutPayment", nvpstr);
		String strAck = nvp.get("ACK").toString();
		if (strAck != null && (strAck.equalsIgnoreCase("Success") || strAck.equalsIgnoreCase("SuccessWithWarning"))) {
			return nvp;
		}
		return null;

	}

	/*********************************************************************************
	 * httpcall: Function to perform the API call to PayPal using API signature @
	 * methodName is name of API method. @ nvpStr is nvp string. returns a NVP
	 * string containing the response from the server.
	 *********************************************************************************/
	public HashMap httpcall(String methodName, String nvpStr) {

		String version = "2.3";
		String agent = "Mozilla/4.0";
		String respText = "";
		HashMap nvp = null; // lhuynh not used?

		// deformatNVP( nvpStr );
		String encodedData = "METHOD=" + methodName + "&VERSION=" + gv_Version + "&PWD=" + gv_APIPassword + "&USER=" + gv_APIUserName + "&SIGNATURE=" + gv_APISignature + nvpStr;

		try {
			URL postURL = new URL(gv_APIEndpoint);
			HttpURLConnection conn = (HttpURLConnection) postURL.openConnection();

			// Set connection parameters. We need to perform input and output,
			// so set both as true.
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// Set the content type we are POSTing. We impersonate it as
			// encoded form data
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent", agent);

			// conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
			conn.setRequestMethod("POST");

			// get the output stream to POST to.
			DataOutputStream output = new DataOutputStream(conn.getOutputStream());
			output.writeBytes(encodedData);
			output.flush();
			output.close();

			// Read input from the input stream.
			DataInputStream in = new DataInputStream(conn.getInputStream());
			int rc = conn.getResponseCode();
			if (rc != -1) {
				BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String _line = null;
				while (((_line = is.readLine()) != null)) {
					respText = respText + _line;
				}
				System.out.println(respText);
				nvp = deformatNVP(respText);
			}
			return nvp;
		} catch (IOException e) {
			// handle the error here
			return null;
		}
	}

	/*********************************************************************************
	 * deformatNVP: Function to break the NVP string into a HashMap pPayLoad is
	 * the NVP string. returns a HashMap object containing all the name value
	 * pairs of the string.
	 *********************************************************************************/
	public HashMap deformatNVP(String pPayload) {
		HashMap nvp = new HashMap();
		StringTokenizer stTok = new StringTokenizer(pPayload, "&");
		while (stTok.hasMoreTokens()) {
			StringTokenizer stInternalTokenizer = new StringTokenizer(stTok.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2) {
				String key = URLDecoder.decode(stInternalTokenizer.nextToken());
				String value = URLDecoder.decode(stInternalTokenizer.nextToken());
				nvp.put(key.toUpperCase(), value);
			}
		}
		return nvp;
	}

	/*********************************************************************************
	 * RedirectURL: Function to redirect the user to the PayPal site token is
	 * the parameter that was returned by PayPal returns a HashMap object
	 * containing all the name value pairs of the string.
	 *********************************************************************************/
	public void RedirectURL(HttpServletResponse response, String token) {
		String payPalURL = PAYPAL_URL + token;

		// response.sendRedirect( payPalURL );
		response.setStatus(302);
		response.setHeader("Location", payPalURL);
		response.setHeader("Connection", "close");
	}

	// end class
}
