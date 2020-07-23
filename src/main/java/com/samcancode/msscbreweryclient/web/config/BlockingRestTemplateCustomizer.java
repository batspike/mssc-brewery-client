package com.samcancode.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
	private final Integer max_total_connections;
	private final Integer default_max_total_connections;
	private final Integer connection_request_timeout;
	private final Integer socket_timeout;

	public BlockingRestTemplateCustomizer(
			@Value("${scc.http_request_config.max_total_connections}") Integer max_total_connections, 
			@Value("${scc.http_request_config.default_max_total_connections}") Integer default_max_total_connections,
			@Value("${scc.http_request_config.connection_request_timeout}") Integer connection_request_timeout, 
			@Value("${scc.http_request_config.socket_timeout}") Integer socket_timeout) {
		
		this.max_total_connections = max_total_connections;
		this.default_max_total_connections = default_max_total_connections;
		this.connection_request_timeout = connection_request_timeout;
		this.socket_timeout = socket_timeout;
	}

	// Apache Http Async Client config
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(max_total_connections);
		connectionManager.setDefaultMaxPerRoute(default_max_total_connections);
		
		RequestConfig requestConfig = RequestConfig
										.custom()
										.setConnectionRequestTimeout(connection_request_timeout)
										.setSocketTimeout(socket_timeout)
										.build();
		
		CloseableHttpClient httpClient = HttpClients
											.custom()
											.setConnectionManager(connectionManager)
											.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
											.setDefaultRequestConfig(requestConfig)
											.build();
		
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	
	@Override
	public void customize(RestTemplate restTemplate) {
		// use the http client setup above for our RestTemplate
		System.out.println("********** Customized BlockingRestTemplate");
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
	}

}
