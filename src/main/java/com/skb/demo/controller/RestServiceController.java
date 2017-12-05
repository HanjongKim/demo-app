package com.skb.demo.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;
import com.skb.demo.common.exception.EnumExceptionMessage;
import com.skb.demo.common.logger.CommonLogger;

/**
 * Resp API 기본 샘픔
 * 
 * @author Administrator
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/v1")
public class RestServiceController {

	private static final Logger logger = LoggerFactory.getLogger(RestServiceController.class.getName());

	@Value("${test.appid:local}")
	String applicationid;

	@Autowired
	CommonLogger commonLogger;
	
	// GET
	@RequestMapping(value = "/rest", params = "method=get", method = RequestMethod.GET)
	public Map<String, Object> get(HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {
		
		commonLogger.log("IF-SCS-006", "RECV.REQ", UUID.randomUUID().toString(), UUID.randomUUID().toString(), "", "\"key\":\"value\"");
		logger.debug("DEBUG");
		logger.info("INFO");
		logger.warn("WARN");
		logger.error("ERROR");
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		String api_if = request.getParameter("if");
		String ver = request.getParameter("ver");
		String ui_name = request.getParameter("ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// GET
	@RequestMapping(value = "/rest/timeout", params = "method=get", method = RequestMethod.GET)
	public Map<String, Object> gettimeout(HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) throws Exception{
		String sleepTime = request.getParameter("sleeptime");
		commonLogger.log("IF-SCS-006", "RECV.REQ", UUID.randomUUID().toString(), UUID.randomUUID().toString(), "", "\"key\":\"value\"");
		logger.debug("DEBUG");
		logger.info("INFO");
		logger.warn("WARN");
		logger.error("ERROR");
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Thread.sleep(new Long(sleepTime));
		String api_if = request.getParameter("if");
		String ver = request.getParameter("ver");
		String ui_name = request.getParameter("ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}
		
	// POST - Get
	@RequestMapping(value = "/rest", params = "method=get", method = RequestMethod.POST)
	public Map<String, Object> post_get(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// POST - Create
	@RequestMapping(value = "/rest", params = "method=post", method = RequestMethod.POST)
	public Map<String, Object> post_post(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// POST - Put
	@RequestMapping(value = "/rest", params = "method=put", method = RequestMethod.POST)
	public Map<String, Object> post_put(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// POST - Delete
	@RequestMapping(value = "/rest", params = "method=delete", method = RequestMethod.POST)
	public Map<String, Object> post_delete(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}

	// PUT - Delete
	@RequestMapping(value = "/rest", method = RequestMethod.PUT)
	public Map<String, Object> put(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}
	
	@RequestMapping(value = "/rest", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@RequestBody String payload, HttpServletRequest request,
			HttpServletResponse response/* , @PathVariable String ver */) {

		Map<String, Object> result = new HashMap<String, Object>();

		String api_if = JsonPath.parse(payload).read("$.if");
		String ver = JsonPath.parse(payload).read("$.ver");
		String ui_name = JsonPath.parse(payload).read("$.ui_name");

		result.put("if", api_if);
		result.put("ver", ver);
		result.put("ui_name", ui_name);
		result.put("result", EnumExceptionMessage.SUCCESS.getCode());
		result.put("reason", EnumExceptionMessage.SUCCESS.getReasonPhrase());

		return result;
	}
}
