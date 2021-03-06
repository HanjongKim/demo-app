package com.skb.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebServiceControllerTest {
	
	@Autowired
	private MockMvc mvc;
	

	@Test
	public void getWelcome() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/v1/web").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
	}
	
	
}
