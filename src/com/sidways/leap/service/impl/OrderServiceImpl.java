package com.sidways.leap.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.transaction.annotation.Transactional;

import com.sidways.leap.dao.OrderDao;
import com.sidways.leap.dao.RefundDao;
import com.sidways.leap.dao.UserDao;
import com.sidways.leap.entity.Order;
import com.sidways.leap.entity.Order.State;
import com.sidways.leap.entity.Refund;
import com.sidways.leap.entity.Role;
import com.sidways.leap.entity.User;
import com.sidways.leap.service.DiscountService;
import com.sidways.leap.service.MailService;
import com.sidways.leap.service.OrderService;
import com.sidways.leap.service.ShellService;
import com.sidways.leap.service.UserService;

/**
 * @author Kim 2012-7-9
 */
public class OrderServiceImpl extends AbstractGeneralDBService<Order> implements OrderService {

	private Log log;

	private UserDao userDao;

	private OrderDao orderDao;

	private RefundDao refundDao;

	private UserService userService;

	private ShellService shellService;

	private MailService mailService;

	private DiscountService discountService;

	private Map<Type, Double> pricesZh;

	private Map<Type, Double> pricesEn;

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public void setRefundDao(RefundDao refundDao) {
		this.refundDao = refundDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setShellService(ShellService shellService) {
		this.shellService = shellService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setPricesZh(Map<Type, Double> pricesZh) {
		this.pricesZh = pricesZh;
	}

	public void setPricesEn(Map<Type, Double> pricesEn) {
		this.pricesEn = pricesEn;
	}

	public OrderServiceImpl() {
		super();
		this.log = LogFactory.getLog(OrderServiceImpl.class);
	}

	@Override
	@Transactional
	public Order order(Type type, Payment payment, String email, String code) throws Exception {
		User user = this.userService.getCurrentUser();
		if (this.isGuest(user)) {
			user = this.buildUser(email);
			//this.mailService.changePw(user, MailService.Type.PASSWORD);
			return this.alone(this.buildUser(email), type, payment, code);
		} else {
			return this.renew(user, type, payment, code);
		}
	}

	@Transactional
	public void pay(String orderId) {
		Order order = super.baseDao.get(orderId);
		order.setState(State.PAY);
		super.baseDao.update(order);

		User user = order.getOwner();
		if (!user.getActivate()) {
			user.setActivate(true);
			this.userDao.update(user);
			this.shellService.createAt(order.getOwner().getUsername(), order.getOwner().getPassword());
		}
		try {
			this.mailService.pay(order.getOwner(), com.sidways.leap.service.MailService.Type.PAYED);
			this.mailService.changePw(user, MailService.Type.PASSWORD);
		} catch (Exception e) {
			this.log.error(e);
		}
	}

	public OrderDetail history() {
		User user = this.userService.getCurrentUser();
		if (user != null) {
			return new OrderDetailImpl(this.orderDao.getByUser(user));
		} else {
			return new OrderDetailImpl();
		}
	}

	public OrderDetail history(User user) {
		return new OrderDetailImpl(this.orderDao.getByUser(user));
	}

	@Transactional
	public void refund(String content) {
		Refund refund = new Refund();
		refund.setContent(content);
		refund.setTarget(this.compareLastOrder());
		this.refundDao.save(refund);
		try {
			this.mailService.refund(this.userService.getCurrentUser(), content, com.sidways.leap.service.MailService.Type.REFUND);
		} catch (Exception e) {
			this.log.error(e);
		}
	}

	@Override
	protected OrderGeneralViewProxy generateGeneralViewProxy(Order entity) {
		return new OrderGeneralViewProxyImpl(this.history(entity.getOwner()), entity.getOwner());
	}

	private boolean isGuest(User user) {
		return user == null ? true : false;
	}

	private Order alone(User user, Type type, Payment payment, String code) {
		Order order = buildOrder(user, payment, type, code);
		order.setDeadline(this.computeDeadLine(new Date(), type));
		super.baseDao.save(order);
		return order;
	}

	private Order renew(User user, Type type, Payment payment, String code) {
		Date lastDateline = this.compareLastDeadline(this.orderDao.getByUser(user));
		Date current = new Date();
		if (lastDateline == null || lastDateline.before(current)) {
			return this.alone(user, type, payment, code);
		} else {
			Order order = this.buildOrder(user, payment, type, code);
			order.setDeadline(this.computeDeadLine(lastDateline, type));
			super.baseDao.save(order);
			return order;
		}
	}

	private User buildUser(String email) {
		User user = this.userDao.getByUsername(email);
		if(user != null){
			return user;
		}
		user = new User();
		user.setUsername(email);
		user.setPassword(UUID.randomUUID().toString().substring(0, 8));
		user.setRole(Role.normal());
		this.userDao.save(user);
		return user;
	}

	private Order buildOrder(User user, Payment payment, Type type, String code) {
		Order order = new Order();
		
		order.setOwner(user);
		order.setType(type.toString());
		
		log.info("=====================Start building order: User: " + user.getUsername() + ", Payment: " + payment + ",Type: " + type + ", Code: " + code);
		
		if (type.equals(Type.YEAR)) {
			Double value = Double.valueOf(this.discountService.discount(code, payment.equals(Payment.ALIPAY) ? this.pricesZh.get(type): this.pricesEn.get(type)));
			log.info("===================Calculated the price: " + value);
			order.setPrice(value);
		} else {
			order.setPrice(payment.equals(Payment.ALIPAY) ? this.pricesZh.get(type): this.pricesEn.get(type));
		}
		order.setState(Order.State.UNPAY);
		return order;
	}

	private Date computeDeadLine(Date current, Type type) {
		DateTime datetime = new DateTime(current);
		return datetime.plusDays(type.convertDays()).toDate();
	}

	private Date compareLastDeadline(List<Order> orders) {
		Date lastDateline = null;
		for (Order history : orders) {
			if (lastDateline == null || history.getDeadline().after(lastDateline)) {
				lastDateline = history.getDeadline();
			}
		}
		return lastDateline;
	}

	private Date compareLastCreated(List<Order> orders) {
		Date created = null;
		for (Order history : orders) {
			if (created == null || history.getCreated().after(created)) {
				created = history.getCreated();
			}
		}
		return created;
	}

	private Order compareLastOrder() {
		Order order = null;
		Date created = null;
		for (Order history : this.orderDao.getByUser(this.userService.getCurrentUser())) {
			if (created == null || history.getCreated().after(created)) {
				created = history.getCreated();
				order = history;
			}
		}
		return order;
	}

	private String compareLastType(List<Order> orders) {
		String type = null;
		Date created = null;
		for (Order history : orders) {
			if (created == null || history.getCreated().after(created)) {
				created = history.getDeadline();
				type = history.getType();
			}
		}
		return type;
	}

	private class OrderDetailImpl implements OrderDetail {

		private Date created;

		private Date lastDeadline;

		private Date lastCreated;

		private String lastType;

		private OrderDetailImpl() {

		}

		private OrderDetailImpl(List<Order> orders) {
			super();
			this.lastDeadline = OrderServiceImpl.this.compareLastDeadline(orders);
			this.lastCreated = OrderServiceImpl.this.compareLastCreated(orders);
			this.lastType = OrderServiceImpl.this.compareLastType(orders);
			for (Order order : orders) {
				if (this.created == null || this.created.before(order.getCreated())) {
					this.created = order.getCreated();
				}
			}
		}

		public String getTypes() {
			if (this.lastDeadline != null) {
				int days = Days.daysBetween(new DateTime(new Date()), new DateTime(this.lastDeadline)).getDays() + 1;
				StringBuffer sb = new StringBuffer();
				sb.append(days).append(" ").append("Days");
				return sb.toString();
			} else {
				return "";
			}
		}

		public String getStates() {
			return "PAYED";
		}

		public Date getCreated() {
			return created;
		}

		public Date getDueDate() {
			return lastDeadline;
		}

		@Override
		public boolean isCanRefund() {
			if (this.lastCreated == null) {
				return false;
			}
			if (!this.lastType.equals("YEAR")) {
				return false;
			}
			int days = Days.daysBetween(new DateTime(this.lastCreated), new DateTime(new Date())).getDays() + 1;
			if (days >= 30) {
				return false;
			}
			return true;
		}
	}

	private class OrderGeneralViewProxyImpl implements OrderGeneralViewProxy {

		private OrderDetail orderDetail;

		private User user;

		private OrderGeneralViewProxyImpl(OrderDetail orderDetail, User user) {
			super();
			this.orderDetail = orderDetail;
			this.user = user;
		}

		@Override
		public String getTypes() {
			return orderDetail.getTypes();
		}

		@Override
		public String getStates() {
			return orderDetail.getStates();
		}

		@Override
		public Date getCreated() {
			return orderDetail.getCreated();
		}

		@Override
		public Date getDueDate() {
			return orderDetail.getDueDate();
		}

		@Override
		public String getUsername() {
			return user.getUsername();
		}

		@Override
		public String getPassword() {
			return user.getPassword();
		}

		@Override
		public boolean isCanRefund() {
			return orderDetail.isCanRefund();
		}
	}
}
