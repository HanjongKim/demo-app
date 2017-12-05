package com.skb.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Profile({"default", "dev"})
@Configuration
public class RedisConfig {

	@Value("${vcap.service.redis.nodes}")
	private String nodes;
	
	private int port=6379;
	
	@Value("${vcap.service.redis.password}")
	private String password;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(this.nodes);
		factory.setPort(this.port);
		factory.setPassword(this.password);
		factory.setUsePool(true);
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
		return redisCacheManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
