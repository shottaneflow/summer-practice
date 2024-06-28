package com.practice.frontend.entity;

import java.util.ArrayList;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PracticeUser {

	
	
	private Integer id;
	
	
	private String username;
	

	private String pincode;
	
	
	
	private List<Authority> authorities;
	
	private List<BankAccount> bankAccounts;
	
	public PracticeUser() {
		this.authorities=new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	public void addAuthorities(Authority authority) {
		this.authorities.add(authority);
	}
	public void addBankAccount(BankAccount bankAccount) {
		this.bankAccounts.add(bankAccount);
	}
}
