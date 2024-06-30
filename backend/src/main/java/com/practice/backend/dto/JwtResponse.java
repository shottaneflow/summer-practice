package com.practice.backend.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtResponse {
	
	private String token;
	private UserDetails userDetails;
	

	
	public JwtResponse(String token,
					   UserDetails userDetails) {
		this.userDetails=userDetails;
		this.token=token;

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



}
