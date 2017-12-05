package com.skb.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


@Profile({"default", "dev"})
@Configuration
@ComponentScan("com.skb.demo.storeLogic.redis")
public class RedisTestConfig {

	//@Value("${vcap.service.redis.nodes}")
	private String nodes;
	
	private int port=6379;
	
	//@Value("${vcap.service.redis.password}")
	private String password;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("169.56.79.184");
		factory.setPort(this.port);
		factory.setPassword("Kei1Ohg2leiNg6qu");
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
    public RedisTemplate<String, Object> redisTemplate1() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
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
