package com.practice.frontend.entity;

import java.math.BigDecimal;

public class BankAccount {
	
	
	private Long accountNumber;
	
	private BigDecimal money;
	
	private String accountName;
	
	public BankAccount() {
		
	}
	public BankAccount(String accountName) {
		this.accountName=accountName;
	}
	public BankAccount(Long accountNumber,BigDecimal money,String accountName) {
		this.accountNumber=accountNumber;
		this.money=money;
		this.accountName=accountName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
}
