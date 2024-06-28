package com.practice.backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.practice.backend.token.Token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.persistence.JoinColumn;


@Entity
@Table(name="t_user",schema="postgres")
public class PracticeUser {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="c_username")
	private String username;
	
	@Column(name="c_pincode")
	private String pincode;
	
	@ManyToMany
	@JoinTable(schema="postgres",name="t_user_authority",
	joinColumns= @JoinColumn(name="id_user"),
	inverseJoinColumns=@JoinColumn(name="id_authority")
	)
	private List<Authority> authorities;
	
	@OneToMany
	@JoinTable(schema="postgres",name="t_user_bank_accounts",
	joinColumns=@JoinColumn(name="id_user"),
	inverseJoinColumns=@JoinColumn(name="id_account"))
	private List<BankAccount> bankAccounts;
	
	private final Token token;
	
	public PracticeUser(Token token) {
		this.token=token;
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

	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	public void addBankAccounts(BankAccount account) {
		this.bankAccounts.add(account);
	}

	public Token getToken() {
		return token;
	}
}
