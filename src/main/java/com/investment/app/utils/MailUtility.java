package com.investment.app.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

@Component
public class MailUtility {

	public Boolean sendEmail(String to,String generatedOtp) {
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("inforeservationack@gmail.com", "dsnvsrxvgwllpjvl");
			}
		});
        
		session.setDebug(true);
		
		MimeMessage m = new MimeMessage(session);
		
		try {
			m.setFrom("inforeserveack@gmail.com");
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject("Account Verification");
			m.setText("Hello There! Your Otp for Account Verification is " + generatedOtp);
			
			Transport.send(m);
			
			return true;
			
		} catch (MessagingException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	public void sendEmailwithAttachment(String to) {
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("inforeservationack@gmail.com", "dsnvsrxvgwllpjvl");
			}
		});
        
		session.setDebug(true);
		
		MimeMessage m = new MimeMessage(session);
		
		try {
			m.setFrom("inforeserveack@gmail.com");
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			m.setSubject("Account Verification");
			
			String path = "C:\\Users\\Rahul\\Desktop\\Reservation.pdf";
			
			try {
				MimeMultipart mimeMultipart = new MimeMultipart();
				MimeBodyPart textMime = new MimeBodyPart();
				textMime.setText("Hello There! You have Received a Bonus Reward of 10 points");
				
				MimeBodyPart fileMime = new MimeBodyPart();
				File file = new File(path);
				fileMime.attachFile(file);
				mimeMultipart.addBodyPart(textMime);
				mimeMultipart.addBodyPart(fileMime);
				m.setContent(mimeMultipart);
				Transport.send(m);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	

}
