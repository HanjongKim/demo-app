package com.skb.demo.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

/**
 * Eureka API 기본 샘픔
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value= "/v1")
public class EurekaServiceController{

	private static final Logger logger = LoggerFactory.getLogger(EurekaServiceController.class.getName());
	
	// RestTemplate
	@Autowired
	private RestTemplate restTemplate;
		
	// RestTemplate + Ribbon
	@Autowired
	@LoadBalanced
	private RestTemplate loadBalanced;
	
	@Autowired
	private EurekaClient discoveryClient;
	
	// EurekaClient Test
	@RequestMapping(value= "/eureka/rest/{serviceName}", method=RequestMethod.GET)
	public Map<String, Object> eurekaDiscoveryTest(HttpServletRequest request, HttpServletResponse response, @PathVariable String serviceName) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();
		//String str = "https://xpg-cntm-dev.skb-suy-dev01.mybluemix.net/v1/synopsis/normal?IF=IF-XPG-CNTM-001&response_format=json&ui_name=BSUHD2&service_code=0&id_package=20&cur_menu=108463&stb_id=%7B5FA8A750-FD44-11E5-A490-FDCFBFF8EC17%7D&ver=v1&sw_ver=0.1.163&stb_model=SMT-E5030&sub_pack=25&iptv_pack=1&resltn_cd=144x206&pur_resltn_cd=144x206&pre_cd=96x72&pre_b_cd=404x227&rst_type=4k&yn_recent=N&g_gubun=30&g_code=1662&hash_id&track_id&con_id=%7BA2C46D9E-1B04-11E6-B49C-5702654CECC8%7D";
		//URI host = new URI(str);
		// Eureka만을 이용한 호출
		InstanceInfo instance = discoveryClient.getNextServerFromEureka(serviceName, false);
		//ResponseEntity<String> entity = this.restTemplate.optionsForAllow(host);
		ResponseEntity<String> entity = this.restTemplate.getForEntity(instance.getHomePageUrl() + "/eureka/apps", String.class);
		
	    logger.debug("Eureka : " + instance.getHomePageUrl());
	    logger.debug("Eureka : " + instance.getHostName());
	    //logger.info("Eureka : " + aa.toString());
		
		node.put("Node", "Value");
		result.put("Result", node);
		
		return result;
	}
		
	// Ribbon Test
	@RequestMapping(value= "/eureka/ribbon/{serviceName}", method=RequestMethod.GET)
	public Map<String, Object> ribbonEurekaDiscoveryTest(HttpServletRequest request, HttpServletResponse response, @PathVariable String serviceName) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> node = new HashMap<String, Object>();
		// Ribbon 을 이용한 호출
		ResponseEntity<String> entity1; 
		
		try {
			entity1 = this.loadBalanced.getForEntity("http://" + serviceName + "/eureka/apps", String.class);
			
			logger.debug("Ribbon : " + entity1.getBody());
			if(entity1.getStatusCode() == HttpStatus.OK){
				logger.info("SUCCESS:");
			}else{
				logger.info("FAIL");
			}
		} catch (Exception e) {
			logger.info("Exception Fail : " + e.getMessage());
			// TODO: handle exception
		}
		
		node.put("Node", "Value");
		result.put("Result", node);
		
		return result;
	}
}
