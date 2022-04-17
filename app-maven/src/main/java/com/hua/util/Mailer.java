package com.hua.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {

	private String subject;
	private String messageBody;
	
	private boolean sslEnable;
	private String host;
	private String from;
	
	private String password;
	private String socketPort;
	private String smtpPort;
		
	
	public Mailer(String subject, String messageBody, boolean sslEnable, String host, String from, String password,
			String socketPort, String smtpPort) {
		super();
		this.subject = subject;
		this.messageBody = messageBody;
		this.sslEnable = sslEnable;
		this.host = host;
		this.from = from;
		this.password = password;
		this.socketPort = socketPort;
		this.smtpPort = smtpPort;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public boolean isSslEnable() {
		return sslEnable;
	}
	public void setSslEnable(boolean sslEnable) {
		this.sslEnable = sslEnable;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSocketPort() {
		return socketPort;
	}
	public void setSocketPort(String socketPort) {
		this.socketPort = socketPort;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	
	public void SendEmail(String to) {
		if (sslEnable)
			SendEmailSSL(to);
		else
			SendEmailNoSSL(to);
	}
	private void SendEmailSSL(String to) {
		
		System.out.println("SSLEmail Start");
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host); //SMTP Host
		properties.put("mail.smtp.socketFactory.port", socketPort); //SSL Port
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		properties.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
		properties.put("mail.smtp.port", smtpPort); //SMTP Port
		
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};
		
		Session session = Session.getDefaultInstance(properties, auth);
		sendEmail(session, to);

	}
	private void SendEmailNoSSL(String to) {
		
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		System.out.println("Setting properties...");
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", smtpPort); //SMTP Port
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "false");
		
		Session session = Session.getDefaultInstance(properties,null);
		sendEmail(session, to);
		
	}
	public void sendEmail(Session session, String to) {
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(messageBody, "text/html; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			// Send message
			System.out.println("Trying to transport email");
			Transport.send(message);
			System.out.println("Sent message successfully at " + to);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
