package com.skb.demo.unittest.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.skb.demo.BaseServiceTest;
import com.skb.demo.domain.logic.RedisServiceLogic;

public class RedisServiceLogicTest extends BaseServiceTest {

	@Autowired
	RedisServiceLogic redisServiceLogic;
	
	
	private String keyVal;
	private String valueVal;
	
	@Before
	public void setUp() throws Exception {
		keyVal = "sk:demo";
		valueVal = "unit test";
	}
	
	@Test	
	public void setGet_normal_success() {
		redisServiceLogic.set(keyVal, (Object)valueVal);
		Object returnVal = redisServiceLogic.get(keyVal);
		assertEquals("결과값이 동일함",valueVal,(String)returnVal);
    }	

}
