package com.practice.frontend.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

import com.practice.frontend.client.DefaultBankAccountRestClient;
import com.practice.frontend.client.DefaultPracticeUserRestClient;




@Configuration
public class ClientBeans {
	
	
	

	@Bean
	public DefaultBankAccountRestClient bankAccountRestClient(@Value("${practice.services.backend.uri:http://localhost:8081}") String restBaseUri
															 ) {
		return new DefaultBankAccountRestClient(RestClient.builder()
				.baseUrl(restBaseUri)
				.requestInterceptor(
						new BearerToken(backendUsername,backendPassword))
				.build())
				;
	}
	@Bean
	public DefaultPracticeUserRestClient practiceUserRestClient(@Value("${practice.services.backend.uri:http://localhost:8081}") String restBaseUri
			 ) {
		return new DefaultPracticeUserRestClient(RestClient.builder()
				.baseUrl(restBaseUri)
				.requestInterceptor(
						new BasicAuthenticationInterceptor(backendUsername,backendPassword))
				.build())
				;
}
}

