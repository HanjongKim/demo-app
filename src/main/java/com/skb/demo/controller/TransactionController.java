package com.skb.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skb.demo.common.exception.EnumExceptionMessage;
import com.skb.demo.domain.dto.TransactionDto;
import com.skb.demo.domain.service.TransactionsService;



@RestController
@RequestMapping("/v1")
public class TransactionController {

    @Autowired
    TransactionsService transactionsService;

    @RequestMapping(value = "/transactionservice/{transaction_id}", params = "method=saveTransaction", method = RequestMethod.PUT)
    public Map<String, Object> saveTransaction(@RequestBody TransactionDto transactionDto, 
    		                                   HttpServletRequest request,
    		                                   HttpServletResponse response, 
    		                                   @PathVariable("transaction_id") Long transaction_id) {

    	Map<String, Object> result = new LinkedHashMap<String, Object>();
        boolean created = transactionsService.saveTransaction(transaction_id, transactionDto);
        
        if (created) {
        	result.put("result", EnumExceptionMessage.SUCCESS.getCode());
        	result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());
        } else {
        	result.put("result", EnumExceptionMessage.CUST_ERROR1);
        	result.put("reason", EnumExceptionMessage.CUST_ERROR1.getReasonPhrase());
        }
        return result;
    }

    @RequestMapping(value = "/transactionservice/{transaction_id}", params = "method=getTransaction", method = RequestMethod.GET)
    public Map<String, Object> getTransaction(HttpServletRequest request,
            								  HttpServletResponse response, 
            								  @PathVariable("transaction_id") Long transaction_id
            								  ) {
    	
    	Map<String, Object> result = new LinkedHashMap<String, Object>(); 
        TransactionDto transactionDto = transactionsService.getTransaction(transaction_id);
        if (transactionDto != null) {
        	result.put("transactionDto", transactionDto);
        	result.put("result", EnumExceptionMessage.SUCCESS.getCode());
        	result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());
        } else {
        	result.put("result", EnumExceptionMessage.CUST_ERROR1);
        	result.put("reason", EnumExceptionMessage.CUST_ERROR1.getReasonPhrase());        	
        }
        return result;
    }


    @RequestMapping(value = "/transactionservice/{type}", params = "method=getTransactionKeys", method = RequestMethod.GET)
    public Map<String, Object> getTransactionKeysByType(HttpServletRequest request,
			  											HttpServletResponse response, 
			  											@PathVariable("type") String type) {

    	Map<String, Object> result = new LinkedHashMap<String, Object>();
        Set<Long> transactionskeys = transactionsService.getAllTransactionsKeysByType(type);

        if (!transactionskeys.isEmpty()) {
        	result.put("transactionskeys", transactionskeys);
        	result.put("result", EnumExceptionMessage.SUCCESS.getCode());
        	result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());
        } else {
        	result.put("result", EnumExceptionMessage.CUST_ERROR1);
        	result.put("reason", EnumExceptionMessage.CUST_ERROR1.getReasonPhrase());          	
        }
        return result;
    }

    @RequestMapping(value = "/transactionservice/{transaction_id}", params = "method=getSum", method = RequestMethod.GET)
    public Map<String, Object> getSumByTransactionId(HttpServletRequest request,
			  										 HttpServletResponse response, 
			  										 @PathVariable("transaction_id") Long transaction_id) {

    	Map<String, Object> result = new LinkedHashMap<String, Object>();
        Double sum = transactionsService.getChildsAmountSumByTransactionId(transaction_id);

        if (sum > 0) {
        	result.put("sum", sum);
        	result.put("result", EnumExceptionMessage.SUCCESS.getCode());
        	result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());
        } else {
        	result.put("result", EnumExceptionMessage.CUST_ERROR1);
        	result.put("reason", EnumExceptionMessage.CUST_ERROR1.getReasonPhrase());          	
        }
        return result;
    }

}
