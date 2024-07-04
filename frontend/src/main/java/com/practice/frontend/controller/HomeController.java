package com.practice.frontend.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.frontend.entity.*;
import com.practice.frontend.exceptions.InsufficientFunds;

import com.practice.frontend.client.*;

@Controller

public class HomeController {
	
	private final BankAccountRestClient bankAccountRestClient;
	private final AuthRegistrationRestClient authRegistrationRestClient;
	
	public HomeController(BankAccountRestClient bankAccountService
							,AuthRegistrationRestClient authRegistrationRestClient) {
		this.bankAccountRestClient=bankAccountService;
		this.authRegistrationRestClient=authRegistrationRestClient;
	}
	
	
	@GetMapping("/home")
	public String getBankAccountPage(Model model,
									 Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        PracticeUser user=this.authRegistrationRestClient.findByName(username)
				.orElse(null);
        model.addAttribute("bankAccounts",bankAccountRestClient.getBankAccounts());
		
		return "home";
	}
	@GetMapping("/bankAccount/create")
	public String createBankAccountPage() {
		
		return "create";
	}
	@PostMapping("/bankAccount/create")
	public String createBankAccount(@RequestParam(name="accountName") String accountName,
									Model model) {
		BankAccount bankAccount=bankAccountRestClient.create(accountName);
		model.addAttribute("bankAccount",bankAccount);
		return "bankAccount";
	}
	


} 
