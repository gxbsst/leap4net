package com.sidways.leap.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.sidways.leap.entity.User;
import com.sidways.leap.service.OrderService.OrderDetail;

/**
 * @author Kim 2012-7-10
 */
public interface MailService {

	public void pay(User user, Type type) throws Exception;
	
	public void changePw(User user, Type type) throws Exception;
	
	public void refund(User user, String content, Type type) throws Exception;
	
	public enum Type {
		
		PAYED, DUEDATE, PASSWORD, REFUND;
		
		public String toString() {
			switch (this) {
			case PAYED:
				return "Leap4net,支付成功";
			case DUEDATE:
				return "Leap4net,服务即将到期";
			case PASSWORD:
				return "Leap4net,请保存好您的密码";
			case REFUND:
				return "Leap4net,退款申请";
			}
			throw new RuntimeException();
		}
	}
	
	public interface MailDetail{
		
		public User getUser();
		
		public String getRefundContent();
		
		public OrderDetail getOrderDetail();
	}
}
