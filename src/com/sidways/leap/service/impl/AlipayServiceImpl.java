package com.sidways.leap.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.sidways.leap.entity.Order;
import com.sidways.leap.service.PayService;

/**
 * @author Kim 2012-7-16
 */
public class AlipayServiceImpl implements PayService {

	private String url;
	
	private String service;
	
	private String encoding;
	
	private String subject;
	
	private String paymentType;
	
	private String sellerEmail;
	
	private String notifyCall;
	
	private String returnCall;
	
	private String partner;
	
	private String key;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public void setNotifyCall(String notifyCall) {
		this.notifyCall = notifyCall;
	}

	public void setReturnCall(String returnCall) {
		this.returnCall = returnCall;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String buildPayURL(Order order) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		Map<String, String> params = this.buildParams(order);
		for(String key : params.keySet()){
			sb.append(key).append("=").append(params.get(key)).append("&");
		}
		return sb.toString();
	}

	private Map<String, String> buildParams(Order order) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("subject", this.subject);
		params.put("service", this.service);
		params.put("notify_url", this.notifyCall);
		params.put("return_url", this.returnCall);
		params.put("out_trade_no", order.getId());
		params.put("_input_charset", this.encoding);
		params.put("total_fee", order.getPrice().toString());
		params.put("partner", this.partner);
		params.put("seller_email", this.sellerEmail);
		params.put("payment_type", this.paymentType);
		return this.addSign(params);
	}
	
	private Map<String, String> addSign(Map<String, String> params){
        Map<String, String> sPara = AlipayCore.paraFilter(params);
        sPara.put("sign", AlipayCore.buildMysign(sPara, this.key, this.encoding));
        sPara.put("sign_type", "MD5");
        return sPara;
	}
}
