package com.sidways.leap.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.sidways.leap.service.CallBackService;
import com.sidways.leap.service.OrderService;

/**
 * @author Kim 2012-7-16
 */
public class CallBackServiceImpl implements CallBackService {

	private String partner;

	private String key;

	private String encoding;

	private AlipayNotify notify;

	private OrderService orderService;

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setNotify(AlipayNotify notify) {
		this.notify = notify;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public CallBackServiceImpl() {
		super();
		this.notify = new AlipayNotify();
	}

	@Override
	public void callback(Map<String, String[]> content) {
		Map<String, String> params = new HashMap<String, String>();
		for(String key : content.keySet()){
			params.put(key, content.get(key)[0]);
		}
		if(this.notify.verify(params)){
			this.orderService.pay(params.get("out_trade_no"));
		}
	}

	public class AlipayNotify {

		private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

		public boolean verify(Map<String, String> params) {
			String mysign = getMysign(params);
			String responseTxt = "true";
			if (params.get("notify_id") != null) {
				responseTxt = verifyResponse(params.get("notify_id"));
			}
			String sign = "";
			if (params.get("sign") != null) {
				sign = params.get("sign");
			}
			if (mysign.equals(sign) && responseTxt.equals("true")) {
				return true;
			} else {
				return false;
			}
		}

		private String getMysign(Map<String, String> Params) {
			Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
			String mysign = AlipayCore.buildMysign(sParaNew, CallBackServiceImpl.this.key, CallBackServiceImpl.this.encoding);
			return mysign;
		}

		private String verifyResponse(String notify_id) {
			String partner = CallBackServiceImpl.this.partner;
			String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;
			return checkUrl(veryfy_url);
		}

		private String checkUrl(String urlvalue) {
			String inputLine = "";
			try {
				URL url = new URL(urlvalue);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				inputLine = in.readLine().toString();
			} catch (Exception e) {
				e.printStackTrace();
				inputLine = "";
			}
			return inputLine;
		}
	}
}
