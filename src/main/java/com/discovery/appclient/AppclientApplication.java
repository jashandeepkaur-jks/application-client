package com.discovery.appclient;

import com.netflix.appinfo.InstanceInfo;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.converters.Auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class AppclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppclientApplication.class, args);
	}

	@Autowired
	private EurekaClient client;
	
	
 
	@Autowired
	RestTemplateBuilder builder;


	@RequestMapping("/")
	public String callService()
	{
		RestTemplate template = builder.build();

		InstanceInfo info= client.getNextServerFromEureka("service",false);

		String baseUrl=info.getHomePageUrl();
		System.out.println(baseUrl);
		ResponseEntity<String> resp = template.exchange(baseUrl, HttpMethod.GET,null,String.class);
		return resp.getBody();
	}

}
