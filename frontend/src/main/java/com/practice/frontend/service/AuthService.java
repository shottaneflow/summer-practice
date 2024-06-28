package com.practice.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;
import com.practice.frontend.dto.*;
import java.util.HashMap;
import java.util.Map;
import com.practice.frontend.dto.*;

@Service
public class AuthService {
    private final RestClient restClient;
    private final String authUrl;

    public AuthService(@Value("${practice.services.backend.uri.auth:http://localhost:8081/auth-api}") String authUrl) {
        this.authUrl = authUrl;
        this.restClient = RestClient.create();
    }

    public String authenticate(String username, String pincode) {
        
        try {
        	 JwtResponse response = restClient.post()
                     .uri(authUrl)
                     .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                     .body(new JwtRequest(username, pincode))
                     .retrieve()
                     .body(JwtResponse.class); 
             return response.getToken();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to authenticate", e);
        }
    }
}
