package com.skb.demo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.skb.demo.config.RedisTestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisTestConfig.class)
public class BaseRedisTest {
	
}
