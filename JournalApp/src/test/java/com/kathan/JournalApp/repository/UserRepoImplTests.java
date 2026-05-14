package com.kathan.JournalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class UserRepoImplTests {

	@Autowired
	private UserRepoImpl userRepoImpl;
	
	@Test
	public void validateUserBySA() {
		assertFalse(userRepoImpl.getUserForSA().size()>=2);
	}
}
