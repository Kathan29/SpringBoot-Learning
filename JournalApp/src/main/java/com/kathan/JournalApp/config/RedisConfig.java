package com.kathan.JournalApp.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	
	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//redisTemplate.setValueSerializer(new StringRedisSerializer());
		
		// Or if we want in our case as value is class 
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		//If we use this Generic Jackson type in value then we dont need ObjectMapper
		return redisTemplate;
	}
	
	@Bean 
	public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
		
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
												.entryTtl(Duration.ofMinutes(3));
		
		return RedisCacheManager.builder(factory)
				.cacheDefaults(cacheConfig)
				.build();
	}
}
