package com.kathan.JournalApp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
	private final JavaMailSender mailSender;
	
	
	public void sendEmail(String to,String subject,String body) {
		
		try{
			SimpleMailMessage simpleMail = new SimpleMailMessage();
			simpleMail.setTo(to);
			simpleMail.setSubject(subject);
			simpleMail.setText(body);
			
			mailSender.send(simpleMail);
		}catch (Exception e) {
			log.error("Exception while Sending email",e);
		}
		
		
		
		
	}
	
}
