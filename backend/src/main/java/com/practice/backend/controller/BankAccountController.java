package com.practice.backend.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.practice.backend.service.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import com.practice.backend.exceptions.*;
import com.practice.backend.dto.MoneyTransferRequest;
import com.practice.backend.entity.*;
import com.practice.backend.exceptions.InsufficientFunds;


@RestController
@RequestMapping("bankAccount-api/bankAccount/{accountNumber:\\d+}")
public class BankAccountController {

	private final BankAccountService bankAccountService;
	
	public BankAccountController(BankAccountService bankAccountService) {
		this.bankAccountService=bankAccountService;
	}
	
	@ModelAttribute("bankAccount")
	public BankAccount getBankAccount(@PathVariable("accountNumber") Long accountNumber) {
		return this.bankAccountService.findBankAccountByNumber(accountNumber)
				.orElseThrow(()-> new NoSuchElementException("Банковский аккаунт не найден"));
	}
	
	@GetMapping
	public BankAccount findBankAccount(@ModelAttribute("bankAccount") BankAccount bankAccount) {
		 return bankAccount;
	}
	
	@PatchMapping
	@Transactional
	public ResponseEntity<?> moneyTransfer(@ModelAttribute("bankAccount") BankAccount currentAccount,
											@Valid @RequestBody MoneyTransferRequest moneyTransferDTO,
											BindingResult bindingResult) throws InsufficientFunds{
		if(bindingResult.hasErrors()) {
			ProblemDetail problemDetail=ProblemDetail
					.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Недостаточно средств");
			problemDetail.setProperty("errors", bindingResult.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.toList());
			return ResponseEntity.badRequest()
						.body(problemDetail);					
		}
		else {
			this.bankAccountService.moneyTransfer(currentAccount.getAccountNumber(), moneyTransferDTO.getSecondAccountNumber(), moneyTransferDTO.getAmount());
			return ResponseEntity.noContent()
					.build();
		}
	}
	@ExceptionHandler(InsufficientFunds.class)
	public ResponseEntity<ProblemDetail> handleInsufficientFundsException(InsufficientFunds exception){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, exception.getMessage()));
	}
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage()));
	}
	
}
