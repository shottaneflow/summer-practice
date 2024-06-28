package com.practice.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(schema="postgres",name="t_bankaccounts")
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="accountnumber")
	private Long accountNumber;
	
	@DecimalMin(value = "0", message = "Баланс должен быть положительным")
	@Column(name="c_money")
	private BigDecimal money;
	
	@Column(name="c_accountname")
	@NotNull(message="Пустое поле имени")
	@Size(max=50)
	private String accountName;
	
	public BankAccount() {
		
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
