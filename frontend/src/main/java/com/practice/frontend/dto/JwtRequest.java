package com.practice.frontend.dto;

public class JwtRequest {
	
	private String username;
	private String pincode;
	
	
	public JwtRequest() {
		
	}
	public JwtRequest(String username,String pincode) {
		this.pincode=pincode;
		this.username=username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
}
