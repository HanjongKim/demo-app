package com.skb.demo.config.bluemix;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis Config 연동 샘픔
 * @author Administrator
 *
 */
@Profile({"stg", "prd" }) // Cloud환경 인식하는 Annotation(manifest.yml의 Env정보와 매팽)
@Configuration 
public class RedisCloudConfig/*extends AbstractCloudConfig*/{

	@Value("${redis.service.timeout:400}")
	private String timeout;					
	
	@Value("${redis.service.maxredirects:1}")
	private String maxRedirects;			
	
	@Value("${vcap.service.redis.nodes}")
	private String nodes;
	
	@Value("${vcap.service.redis.password}")
	private String password;


	@Bean
	public RedisClusterConfiguration getClusterConfiguration() {

		Map<String, Object> source = new HashMap<String, Object>();

		source.put("spring.redis.cluster.nodes", this.nodes);
		source.put("spring.redis.cluster.timeout", timeout);
		source.put("spring.redis.cluster.max-redirects", maxRedirects);

		return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
			
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(600);
		poolConfig.setMaxIdle(60);
		poolConfig.setMinIdle(30);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);

		JedisConnectionFactory factory = new JedisConnectionFactory(getClusterConfiguration(), poolConfig);
		factory.setPassword(this.password);
		//factory.setUsePool(true);
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