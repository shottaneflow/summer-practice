package com.practice.frontend.config;



import com.practice.frontend.client.DefaultAuthRegistrationRestClient;
import com.practice.frontend.interceptor.JwtRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.practice.frontend.client.DefaultBankAccountRestClient;


@Configuration
public class ClientBeans {
	
	private final JwtRequestInterceptor jwtRequestInterceptor;

	public ClientBeans(JwtRequestInterceptor jwtRequestInterceptor) {this.jwtRequestInterceptor = jwtRequestInterceptor;}
	

	@Bean
	public DefaultBankAccountRestClient bankAccountRestClient(@Value("${practice.services.backend.uri:http://localhost:8081}") String restBaseUri
															 ) {
		return new DefaultBankAccountRestClient(RestClient.builder()
				.baseUrl(restBaseUri)
				.requestInterceptor(jwtRequestInterceptor)
				.build())
				;
	}

	@Bean
	public DefaultAuthRegistrationRestClient defaultAuthRegistrationRestClient(@Value("${practice.services.backend.uri:http://localhost:8081}") String restBaseUri
	){
		return new DefaultAuthRegistrationRestClient(RestClient.builder()
				.baseUrl(restBaseUri)
				.build());
	}
}

