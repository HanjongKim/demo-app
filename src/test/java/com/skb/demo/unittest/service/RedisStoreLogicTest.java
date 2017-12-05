package com.skb.demo.unittest.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.skb.demo.BaseServiceTest;
import com.skb.demo.storeLogic.RedisStoreLogic;

public class RedisStoreLogicTest extends BaseServiceTest {

	@Autowired
	RedisStoreLogic redisStoreLogic;
	
	
	private String keyVal;
	private String valueVal;
	
	@Before
	public void setUp() throws Exception {
		keyVal = "sk:demo";
		valueVal = "unit test";
	}
	
	@Test	
	public void setGet_normal_success() {
		redisStoreLogic.set(keyVal, (Object)valueVal);
		Object returnVal = redisStoreLogic.get(keyVal);
		assertEquals("결과값이 동일함",valueVal,(String)returnVal);
    }
	
}
