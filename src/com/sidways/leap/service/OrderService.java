package com.sidways.leap.service;

import java.util.Date;

import com.sidways.leap.entity.Order;
import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-7-9
 */
public interface OrderService extends GeneralDBService<Order>{

	public Order order(Type type, Payment payment, String email, String code) throws Exception;
	
	public void pay(String orderId);
	
	public OrderDetail history();
	
	public OrderDetail history(User user);
	
	public void refund(String content);

	public static enum Type {

		DAY, MONTH, YEAR;
		
		public int convertDays(){
			switch (this){
			case DAY:
				return 1;
			case MONTH:
				return 30;
			case YEAR:
				return 365;
			}
			return 0;
		}
	}

	public static enum Payment {

		PAYPAL, ALIPAY
	}
	
	public interface OrderDetail{
		
		public String getTypes();
		
		public String getStates();
		
		public Date getCreated();
		
		public Date getDueDate();
		
		public boolean isCanRefund();
	}
	
	public interface OrderGeneralViewProxy extends OrderDetail, GeneralViewProxy{
		
		public String getUsername();
		
		public String getPassword();
	}
}
