package com.skb.demo.apitest;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skb.demo.BaseApiTest;

public class IF_XPG_MNGR_001_Test extends BaseApiTest{

	// 메이븐 테스트 실행시 -D 옵션으로 받아옵니다.
	@Value("${application.url:localhost:9000}") 
	String applicationUrl;
	
	// TestRestTemplate testRestTemplate = new TestRestTemplate(HttpClientOption.SSL); //SSL 사용시 설정
	TestRestTemplate testRestTemplate;

	String IF = "IF-XPG-RCMD-001";
	String ver = "1.0";
	String response_format = "json";
	String service_code = "0";
	String id_package = "20";
	String cur_menu = "108773";
	String stb_id = "{F93D4E1F-E4EB-11E5-A490-FDCFBFF8EC17}";
	String version = "4.1.487";
	String sw_ver = "4.1.487";
	String stb_model = "BKO-100";
	String sub_pack = "25";
	String iptv_pack = "1";
	String g_gubun = "30";
	String g_code = "000001662";
	String con_id = "{2153389A-1B04-11E6-B49C-5702654CECC8}";
	
	// 초기화
	@Before
	public void init(){
		testRestTemplate = new TestRestTemplate();
	}
	

	// Test
	@Test
	public void IF_XPG_MNGR_001_normal_Healthcheck() {
		ResponseEntity<String> entity = this.testRestTemplate
				.getForEntity("http://" + this.applicationUrl + "/health", String.class);

		// HttpStatus Code Check
		then(entity.getStatusCode().is2xxSuccessful()).isTrue();

	}
	
	// Json응답 데이터 및 Required Value Check Sample
	@Test
	public void IF_XPG_RCMD_001_abnormal_requiredValueCheck() {
		
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("https://" + this.applicationUrl + "/v1/recommend/content/")
				//.queryParam("IF",this.IF)
				.queryParam("ver",this.ver)
				.queryParam("response_format",this.response_format)
				.queryParam("service_code",this.service_code)
				.queryParam("id_package",this.id_package)
				.queryParam("cur_menu",this.cur_menu)
				.queryParam("stb_id",this.stb_id)
				.queryParam("version",this.version)
				.queryParam("sw_ver",this.sw_ver)
				.queryParam("stb_model",this.stb_model)
				.queryParam("sub_pack",this.sub_pack)
				.queryParam("iptv_pack",this.iptv_pack)
				.queryParam("g_gubun",this.g_gubun)
				.queryParam("g_code",this.g_code)
				.queryParam("con_id",this.con_id);
		
		
		UriComponents uriComponents = builder.build();
        URI url = uriComponents.toUri();
        
		ResponseEntity<String> entity = this.testRestTemplate
				.getForEntity(url, String.class);

		// HttpStatus Code Check
		then(entity.getStatusCode().is2xxSuccessful()).isTrue();

		// Get Response Body
		String json = entity.getBody();
		
		// Json Check
		assertThat(json, isJson());
		
		// Json Path/Data Check
		assertThat(json, hasJsonPath("$.result"));
		assertThat(json, hasJsonPath("$.reason"));
		assertThat(json, hasJsonPath("$.ver"));
		assertThat(json, hasJsonPath("$.IF"));
		
		// Json Data Value Check
		assertThat(json, hasJsonPath("$.result", equalTo("MP-9001")));
		
	}
}
