package com.sidways.leap.service.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.sidways.leap.entity.User;
import com.sidways.leap.service.MailService;
import com.sidways.leap.service.OrderService;
import com.sidways.leap.service.OrderService.OrderDetail;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author Kim 2012-7-10
 */
public class MailServiceImpl implements MailService {

	private List<MailSender> senders;

	private List<String> senderList;

	private Map<Type, String> contents;

	private FreeMarkerTemplateSupport support;

	private OrderService orderService;

	private String refundNotifier;

	public MailServiceImpl() {
		super();
		this.support = new FreeMarkerTemplateSupport();
	}

	public void setSenderList(List<String> senderList) {
		this.senderList = senderList;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setSenders(List<MailSender> senders) {
		this.senders = senders;
	}

	public void setContents(Map<Type, String> contents) {
		this.contents = contents;
	}

	public void setRefundNotifier(String refundNotifier) {
		this.refundNotifier = refundNotifier;
	}

	@Override
	public void pay(User user, Type type) throws Exception {
		this.generalMail(user, type);
	}

	@Override
	public void changePw(User user, Type type) throws Exception {
		this.generalMail(user, type);
	}

	public void refund(User user, String content, Type type) throws AddressException, MessagingException {
		int i = 0;
		for (MailSender sender : this.senders) {
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom(senderList.get(i));
				msg.setTo(this.refundNotifier);
				msg.setSubject(type.toString());
				msg.setText(this.support.merge(contents.get(type), new MailDetailImpl(user, this.orderService.history(user), content)));
				sender.send(msg);
			} catch (Exception e) {
			} finally {
				i++;
			}
		}
	}

	private void generalMail(User user, Type type) throws Exception {
		int i = 0;
		for (MailSender sender : this.senders) {
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom(senderList.get(i));
				msg.setTo(user.getUsername());
				msg.setSubject(type.toString());
				msg.setText(this.support.merge(contents.get(type), new MailDetailImpl(user, this.orderService.history(user), null)));
				sender.send(msg);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				i++;
			}
		}
	}

	private static class MailDetailImpl implements MailDetail {

		private User user;

		private OrderDetail orderDetail;

		private String refundContent;

		public MailDetailImpl(User user, OrderDetail orderDetail, String refundContent) {
			super();
			this.user = user;
			this.orderDetail = orderDetail;
			this.refundContent = refundContent;
		}

		public User getUser() {
			return user;
		}

		public OrderDetail getOrderDetail() {
			return orderDetail;
		}

		@Override
		public String getRefundContent() {
			return refundContent;
		}
	}

	private static class FreeMarkerTemplateSupport {

		public String merge(String templateName, MailDetail mailDetail) {
			try {
				Configuration cfg = new Configuration();
				cfg.setClassForTemplateLoading(FreeMarkerTemplateSupport.class, "template");
				cfg.setObjectWrapper(new DefaultObjectWrapper());
				cfg.setEncoding(Locale.CHINA, "utf-8");
				Template temp = cfg.getTemplate(templateName);
				StringWriter out = new StringWriter();
				temp.process(mailDetail, out);
				return out.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	static class JavaMail {

		public static void send(String to, String subject, String body) throws Exception {
			String server = "smtp.126.com";
			String port = "25";
			String username = "slovex1984@126.com";
			String password = "19850105";
			String from = "slovex1984@126.com";
			Properties props = new Properties();
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setSentDate(new Date());
			msg.setSubject(subject);
			msg.setContent(body, "text/html;charset=utf8");
			msg.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(server, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}
	}

}
