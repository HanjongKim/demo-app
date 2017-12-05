package com.skb.demo.unittest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.skb.demo.BaseControllerTest;
import com.skb.demo.controller.WebServiceController;

public class WebServiceControllerTest extends BaseControllerTest{		
	
	@Autowired
	WebServiceController webServiceController;
	
	@MockBean
	HttpServletRequest request;

	@MockBean
	HttpServletResponse response;

	@MockBean
	Map<String, Object> model;
	
	private String expectedNomalVal;
	private String expectedAbnomalVal;
	
	@Before
	public void setUp() throws Exception {
		expectedNomalVal = "main";
		expectedAbnomalVal = "abMain";
	}
	
	
	@Test
	public void main_normal_requestSuccess() throws Exception {			
		String returnVal = webServiceController.main(request, response, model);		
		assertEquals("결과값이 동일함",expectedNomalVal,returnVal);
	}
	
	@Test
	public void main_abnormal_requestSuccess() throws Exception {			
		String returnVal = webServiceController.main(request, response, model);		
		assertNotEquals("결과값이 상이함",expectedAbnomalVal,returnVal);
	}
	
}
