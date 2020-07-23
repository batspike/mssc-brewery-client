package com.samcancode.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.samcancode.msscbreweryclient.web.model.CustomerDto;

@Component
@ConfigurationProperties(value="sfg.brewery", ignoreUnknownFields=false)
public class CustomerClient {
	public final String CUST_PATH_V1 = "/api/v1/cust/";
	private String apihost;
	
	private final RestTemplate restTemplate;
	
	public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public CustomerDto getCustById(UUID uuid) {
		return restTemplate.getForObject(apihost + CUST_PATH_V1 + uuid.toString(), CustomerDto.class);
	}
	
	public URI saveNewCust(CustomerDto custDto) {
		return restTemplate.postForLocation(apihost + CUST_PATH_V1, custDto);
	}
	
	public void updateCust(UUID uuid, CustomerDto custDto) {
		restTemplate.put(apihost + CUST_PATH_V1 + uuid.toString(), custDto);
	}
	
	public void deleteCust(UUID uuid) {
		restTemplate.delete(apihost + CUST_PATH_V1 + uuid.toString());
	}
	
	public void setApihost(String apihost) {
		this.apihost = apihost;
	}
}
