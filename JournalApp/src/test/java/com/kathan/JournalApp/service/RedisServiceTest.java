package com.kathan.JournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class RedisServiceTest {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void redisExample() {
		redisTemplate.opsForValue().set("email", "abcd@gmail.com");
		
		Object email = redisTemplate.opsForValue().get("email");
		Object name = redisTemplate.opsForValue().get("name");
		
		log.info(email.toString());
		log.info(name.toString());
	}
}
