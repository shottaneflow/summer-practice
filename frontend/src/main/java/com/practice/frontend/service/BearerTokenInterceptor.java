package com.practice.frontend.service;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

	private final AuthService authService;
	
	public BearerTokenInterceptor(AuthService authService) {
		this.authService=authService;
	}
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().setBearerAuth(authService.authenticate());
        return execution.execute(request, body);
	}

}
