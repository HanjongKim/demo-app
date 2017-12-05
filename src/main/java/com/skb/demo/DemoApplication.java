package com.skb.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ServletComponentScan
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@RestController
public class DemoApplication extends SpringBootServletInitializer {
 
	
	/**
	 * War 패포시 설정
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(DemoApplication.class);
	}
	
	public static void main(String[] args) {
		//SpringApplication.run(DemoApplication.class, args);
		SpringApplication application = new SpringApplication(DemoApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {
		return "MAIN";
	}
	
	
}
