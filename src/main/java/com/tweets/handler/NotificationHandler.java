package com.tweets.handler;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tweets.communicator.UserServiceCommunicator;
import com.tweets.datamodel.User;

@Service
public class NotificationHandler {

	@Autowired
	private UserServiceCommunicator userServiceCommunicator;

	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	public NotificationHandler(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String userId, String subject, String message) {
		User user = userServiceCommunicator.getUsersDetails(Arrays.asList(userId)).get(0);
		String toEmail = user.getEmail();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		mailSender.send(mailMessage);
		System.out.println("Email sent to: " + toEmail);
	}
}
