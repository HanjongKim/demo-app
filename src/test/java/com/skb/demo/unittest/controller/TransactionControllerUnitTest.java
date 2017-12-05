package com.skb.demo.unittest.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skb.demo.domain.service.TransactionsService;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = TransactionController.class)
@ContextConfiguration(locations = {"classpath:data.xml"})
public class TransactionControllerUnitTest {

	private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private TransactionsService transactionsServiceMock;
    
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;    
    private static TransactionControllerUnitTest lastTestInstance;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        synchronized (TransactionControllerUnitTest.class) {
            if (lastTestInstance == null) {
                lastTestInstance = this;
            }
        }
    }
    
    @AfterClass
    public static synchronized void cleanUp() throws Exception {
        lastTestInstance.redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
   
    //@Ignore
    @Test
    public void getTransaction_normal_requestSuccess() throws Exception {
    	
    	assertThat(this.transactionsServiceMock).isNotNull();
    	
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transactionservice/{id}", 10)
        		.param("method", "getTransaction")
                .contentType(APPLICATION_JSON))
       			.andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].city", is(arrival.getCity())));
//    	
//        mockMvc.perform(
//                get("/v1/transactionservice/{id}", 10)
//                .param("method", "getTransaction")
//                .accept(MediaType.APPLICATION_JSON))
//       	.andDo(print())
//        .andExpect(status().is4xxClientError());
        //.andExpect(content().string("{\"amount\":5000.0,\"type\":\"cars\"}"));
    }   


}
