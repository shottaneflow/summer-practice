package com.practice.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.backend.entity.BankAccount;
import com.practice.backend.service.BankAccountService;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

private final BankAccountService bankAccountService;
	
	
	
	public AdminController(BankAccountService bankAccountService) {
		this.bankAccountService=bankAccountService;
	}
	
	@GetMapping("/bankAccounts")
	public Iterable<BankAccount> findAllBankAccounts(){
		return this.bankAccountService.getBankAccounts();
	}
}
