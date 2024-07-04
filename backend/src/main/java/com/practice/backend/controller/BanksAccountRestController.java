package com.practice.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.NoSuchElementException;

import com.practice.backend.entity.BankAccount;
import com.practice.backend.entity.PracticeUser;
import com.practice.backend.service.BankAccountService;
import com.practice.backend.service.PracticeUserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("bankAccount-api")
public class BanksAccountRestController {

	private final BankAccountService bankAccountService;
	private final PracticeUserService practiceUserService;
	
	
	
	public BanksAccountRestController(BankAccountService bankAccountService,PracticeUserService practiceUserService) {
		this.bankAccountService=bankAccountService;
		this.practiceUserService=practiceUserService;
	}
	@GetMapping("/bankAccounts")
	public Iterable<BankAccount> findPracticeUserBankAccounts(Principal principal){
		
		return this.practiceUserService.getBankAccounts(principal.getName());
	}
	@PostMapping("/bankAccounts")
	@Transactional
	public ResponseEntity<?> createBankAccount(@Valid @RequestBody BankAccount bankAccount,
											   Principal principal,
											   BindingResult bindingResult,
											   UriComponentsBuilder uriComponentsBuilder){
		if(bindingResult.hasErrors()) {
			ProblemDetail problemDetail=ProblemDetail
					.forStatusAndDetail(HttpStatus.BAD_REQUEST, "error 400");
			problemDetail.setProperty("errors",bindingResult.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.toList());
			return ResponseEntity.badRequest()
					.body(problemDetail);
		}
		else {
			BankAccount account=this.bankAccountService.create(bankAccount.getAccountName());
			PracticeUser practiceUser=this.practiceUserService.findUserByUsername(principal.getName());
			
					
			practiceUser.addBankAccounts(account);
			return ResponseEntity
					.created(uriComponentsBuilder
							.replacePath("/bankAccount-api/bankAccount/{accountNumber}")
							.build(Map.of("accountNumber",account.getAccountNumber())))
					.body(account);
		}
	}
	
}
