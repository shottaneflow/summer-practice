package com.practice.frontend.controller;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.practice.frontend.entity.*;
import com.practice.frontend.exceptions.InsufficientFunds;
import com.practice.frontend.client.BankAccountRestClient;
import com.practice.frontend.client.*;


@Controller
@RequestMapping("/bankAccount/{accountNumber:\\d+}")
public class BankAccountController {
	
	
	private final BankAccountRestClient bankAccountService;
	
	private final PracticeUserRestClient practiceUserRestClient;
	
	private final PasswordEncoder passwordEncoder;
	
	public BankAccountController(BankAccountRestClient bankAccountService,PasswordEncoder passwordEncoder,
								PracticeUserRestClient practiceUserRestClient) {
		this.bankAccountService=bankAccountService;
		this.passwordEncoder=passwordEncoder;
		this.practiceUserRestClient=practiceUserRestClient;
	}
	
	@GetMapping
	public String getBankAccountPage(Model model,
									 @PathVariable(name="accountNumber") Long number) {
	    Optional<BankAccount> optionalBankAccount = bankAccountService.findBankAccountByNumber(number);
	   
	    model.addAttribute("bankAccount", optionalBankAccount.get());
	    return "bankAccount";
	  
	}
	
	@GetMapping("replenishment")
	public String getReplenishmentPage( Model model,
										@PathVariable(name="accountNumber") Long number) {
	    Optional<BankAccount> optionalBankAccount = bankAccountService.findBankAccountByNumber(number);
	   
	    model.addAttribute("bankAccount", optionalBankAccount.get());
	    return "replenishment";
	   
	}
	
	@GetMapping("withdraw")
	public String getWithdrawPage( Model model,
										@PathVariable(name="accountNumber") Long number) {
	    Optional<BankAccount> optionalBankAccount = bankAccountService.findBankAccountByNumber(number);
	   
	    model.addAttribute("bankAccount", optionalBankAccount.get());
	    return "withdraw";
	   
	}
	
	@GetMapping("transfer")
	public String getTransferPage( Model model,
										@PathVariable(name="accountNumber") Long number) {
	    Optional<BankAccount> optionalBankAccount = bankAccountService.findBankAccountByNumber(number);
	   
	    model.addAttribute("bankAccount", optionalBankAccount.get());
	    return "transfer";
	   
	}
	
	
	
	
	@PostMapping("replenishment")
	public String replenishment(@PathVariable(name="accountNumber") Long replenishmentAccount,
								@RequestParam(name="debitAccount") Long debitAccount,
								@RequestParam(name="value") BigDecimal value,
								@RequestParam(name="pincode") String pincode) throws InsufficientFunds  {
		
		String encodePincode=passwordEncoder.encode(pincode);
		for(BankAccount account: this.bankAccountService.getAllBankAccounts()) {
			if(account.getAccountNumber().equals(debitAccount)) {
				//if(encodePincode.equals(practiceUserRestClient.getPincodeByBankAccountNumber(debitAccount)
						bankAccountService.moneyTransfer(debitAccount, replenishmentAccount,value);
			}
		}
		
			
			
		return "redirect:/bankAccounts";
	}
	@PostMapping("withdraw")
	public String withdraw(@PathVariable(name="accountNumber") Long debitAccount,
								@RequestParam(name="replenishmentAccount") Long replenishmentAccount,
								@RequestParam(name="value") BigDecimal value,
								@RequestParam(name="pincode") String pincode) throws InsufficientFunds  {
		
			this.bankAccountService.moneyTransfer(debitAccount, replenishmentAccount, value);
		
			
			
		return "redirect:/bankAccounts";
	}
	@PostMapping("transfer")
	public String transfer(@PathVariable(name="accountNumber") Long debitAccount,
								@RequestParam(name="replenishmentAccount") Long replenishmentAccount,
								@RequestParam(name="value") BigDecimal value,
								@RequestParam(name="pincode") String pincode) throws InsufficientFunds  {
			this.bankAccountService.moneyTransfer(debitAccount, replenishmentAccount, value);
		
			
			
		return "redirect:/bankAccounts";
	}
    @ExceptionHandler(InsufficientFunds.class)
    public String handleInsufficientFundsException(InsufficientFunds exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "exception";
    }
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "exception";
    }
    @ExceptionHandler(BadRequest.class)
    public String handleBadRequest(BadRequest exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "exception";
    }

}
