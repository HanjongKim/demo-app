package com.skb.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skb.demo.domain.logic.RedisServiceLogic;

import org.springframework.boot.test.mock.mockito.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class WebServiceControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;

	//@MockBean
	//private RedisServiceLogic redisServiceLogic;
	
	@Before
    public void setup()  throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
	public void getWelcome() throws Exception {
		

		//given(this.redisServiceLogic.get("test")).willReturn(new String());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/redis").accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"Result\":{\"Node\":\"Value\"}}"));
		
	}
	
	
}
