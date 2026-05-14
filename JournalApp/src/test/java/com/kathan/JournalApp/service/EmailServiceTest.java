package com.kathan.JournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmailServiceTest {

	@Autowired
	private EmailService emailService;
	
	@Test
	void testSendEmail() {
		emailService.sendEmail("kathanmodi2001@gmail.com", "Testing Java mail sender", "Hi, app kese ho?");
	}
}
