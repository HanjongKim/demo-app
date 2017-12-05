package com.skb.demo.unittest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.skb.demo.BaseControllerTest;
import com.skb.demo.domain.dto.TransactionDto;
import com.skb.demo.domain.service.TransactionsService;

@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:data.xml"})
public class TransactionControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    
    private MockMvc mockMvc;

    private static TransactionControllerTest lastTestInstance;
    
    @MockBean
    private TransactionsService transactionsService;

    @AfterClass
    public static synchronized void cleanUp() throws Exception {
        lastTestInstance.redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        synchronized (TransactionControllerTest.class) {
            if (lastTestInstance == null) {
                lastTestInstance = this;
            }
        }
    }
    
    @Ignore
    @Test
    public void saveTransaction_nomal_requestSuccess() throws Exception {
        mockMvc.perform(
                put("/v1/transactionservice/{id}", 10)
                		.param("method", "saveTransaction")
                        .contentType(MediaType.APPLICATION_JSON).content(
                        "{\"amount\":5000,\"type\":\"cars\"}"
                )
        )
       			.andDo(print())
                .andExpect(status().is4xxClientError());
        //save confirm
        mockMvc.perform(
                get("/v1/transactionservice/{id}", 10)
                .param("method", "getTransaction")
                .accept(MediaType.APPLICATION_JSON))
       	.andDo(print())
        .andExpect(status().is2xxSuccessful());
    }
    
    @Ignore
    @Test
    public void getTransaction_abnomal_requestSuccess() throws Exception {
    	
        mockMvc.perform(
                get("/v1/transactionservice/{id}", 10)
                .param("method", "getTransaction")
                .accept(MediaType.APPLICATION_JSON))
       	.andDo(print())
        .andExpect(status().is4xxClientError());
        //.andExpect(content().string("{\"amount\":5000.0,\"type\":\"cars\"}"));
    }

    
    //@Ignore
    @Test
    public void getTransaction_nomal_requestSuccess() throws Exception {
    	
        TransactionDto transactionDto = new TransactionDto();        
        transactionDto.setAmount(5000.0);
        transactionDto.setType("cars");


        given(transactionsService.getTransaction(new Long(10))).willReturn(transactionDto);

        mockMvc.perform(get("/v1/transactionservice/{id}", 10)
        		.param("method", "getTransaction")
                .contentType(APPLICATION_JSON))
       			.andDo(print())
                .andExpect(status().isOk());
    	

    }
    @Ignore
    @Test
    public void test_c_getKeysByType() throws Exception {

        mockMvc.perform(
                get("/v1/transactionservice/types/{type}", "cars"))
                .andExpect(status().isOk())
                .andExpect(content().json("[10,11]"));
    }
    
    @Ignore
    @Test
    public void test_d_getSumByParentId() throws Exception {

        mockMvc.perform(
                get("/v1/transactionservice/sum/{id}", 10))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"sum\":11000.0}"));

        mockMvc.perform(
                get("/v1/transactionservice/sum/{id}", 11))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"sum\":6000.0}"));
    }

}
