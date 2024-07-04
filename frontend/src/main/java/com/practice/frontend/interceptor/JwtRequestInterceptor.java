package com.practice.frontend.interceptor;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Configuration
public class JwtRequestInterceptor implements ClientHttpRequestInterceptor {

    private final HttpSession session;

    public JwtRequestInterceptor(HttpSession session) {this.session = session;}
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken != null) {
            request.getHeaders().add("Authorization", "Bearer " + session.getAttribute("jwtToken"));
        }
        return execution.execute(request, body);
    }
}
