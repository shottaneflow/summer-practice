package com.practice.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practice.frontend.client.BankAccountRestClient;

@Controller
public class AdminController {
	
	private final BankAccountRestClient bankAccountRestClient;
	
	public AdminController(BankAccountRestClient bankAccountRestClient) {
		this.bankAccountRestClient=bankAccountRestClient;
	}
	
	
	@GetMapping("/admin/bankAccounts")
	public String getBankAccountsPage(Model model) {	
		model.addAttribute("accounts",this.bankAccountRestClient.getAllBankAccounts());
		
		return "bankAccounts";
	}

}
