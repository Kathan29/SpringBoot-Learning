package com.kathan.JournalApp.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
	private final RedisTemplate<String,Object> redisTemplate;
	//private final ObjectMapper objMapper;
	
	public <T> T get(String key,Class<T> entityClass) {
		try {
			/*
			Object o = redisTemplate.opsForValue().get(key);
			if(o==null)
				return null;
			return objMapper.readValue(o.toString(),entityClass);
			*/
		 return (T)redisTemplate.opsForValue().get(key);
		} catch(Exception e) {
			log.error("Exception : "+e);
			return null;
		}
	}
	
	public void set(String key,Object o,Long ttl) {
	
		try{
			//As we have set , serialize key and value as String, so need to convert
			// value into string. and key is already in string.
			
			/*
			String jsonValue = objMapper.writeValueAsString(o);
			redisTemplate.opsForValue().set(key,jsonValue,ttl,TimeUnit.SECONDS);
			*/
			
			redisTemplate.opsForValue().set(key,o,ttl,TimeUnit.SECONDS);
		}catch(Exception e) {
			log.error("Exception :"+e);
		}
		
	}
}
