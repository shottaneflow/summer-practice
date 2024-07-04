package com.practice.frontend.controller;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.practice.frontend.dto.MoneyTransferRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.practice.frontend.entity.*;
import com.practice.frontend.exceptions.InsufficientFunds;
import com.practice.frontend.client.BankAccountRestClient;
import com.practice.frontend.client.*;


@Controller
@RequestMapping("/bankAccount/{accountNumber:\\d+}")
public class BankAccountController {
	
	
	private final BankAccountRestClient bankAccountService;
	

	

	
	public BankAccountController(BankAccountRestClient bankAccountService) {
		this.bankAccountService=bankAccountService;
	}
	@ModelAttribute("bankAccount")
	public BankAccount getBankAccount(@PathVariable Long accountNumber) {
		return this.bankAccountService.findBankAccountByNumber(accountNumber)
				.orElseThrow(()->new NoSuchElementException("Банковский аккаунт не найден"));
	}
	
	@GetMapping
	public String getBankAccountPage(Model model,
									 @ModelAttribute("bankAccount") BankAccount bankAccount) {
	   
	    model.addAttribute("bankAccount", bankAccount);
	    return "bankAccount";
	  
	}
	
	@GetMapping("replenishment")
	public String getReplenishmentPage( Model model,
										@ModelAttribute("bankAccount") BankAccount bankAccount) {
	    model.addAttribute("bankAccount", bankAccount);
	    return "replenishment";
	   
	}
	
	@GetMapping("withdraw")
	public String getWithdrawPage( Model model,
								   @ModelAttribute("bankAccount") BankAccount bankAccount) {
	    model.addAttribute("bankAccount", bankAccount);
	    return "withdraw";
	   
	}
	
	@GetMapping("transfer")
	public String getTransferPage( Model model,
								   @ModelAttribute("bankAccount") BankAccount bankAccount) {
	    model.addAttribute("bankAccount", bankAccount);
	    return "transfer";
	   
	}
	
	@PostMapping("replenishment")
	public String replenishment(@PathVariable(name="accountNumber") Long replenishmentAccount,
								@RequestParam(name="debitAccount") Long debitAccount,
								@RequestParam(name="value") BigDecimal value,
								@RequestParam(name="pincode") String pincode) throws InsufficientFunds  {

		if(value.compareTo(BigDecimal.ZERO)>=0) {
			this.bankAccountService.moneyTransfer(debitAccount, new MoneyTransferRequest(replenishmentAccount, value, pincode));
		}
		return "redirect:/home";
	}
	@PostMapping("withdraw")
	public String withdraw(@PathVariable(name="accountNumber") Long debitAccount,
								@RequestParam(name="replenishmentAccount") Long replenishmentAccount,
								@RequestParam(name="value") BigDecimal value,
								@RequestParam(name="pincode") String pincode) throws InsufficientFunds  {

		if(value.compareTo(BigDecimal.ZERO)>=0) {
			this.bankAccountService.moneyTransfer(debitAccount, new MoneyTransferRequest(replenishmentAccount, value, pincode));
		}
			
		return "redirect:/home";
	}
	@PostMapping("transfer")
	public String transfer(@PathVariable(name="accountNumber") Long debitAccount,
								@RequestParam(name="replenishmentAccount") Long replenishmentAccount,
								@RequestParam(name="value") BigDecimal value,
								@RequestParam(name="pincode") String pincode) throws InsufficientFunds  {
		if(value.compareTo(BigDecimal.ZERO)>=0) {
			this.bankAccountService.moneyTransfer(debitAccount, new MoneyTransferRequest(replenishmentAccount, value, pincode));
		}
			
			
		return "redirect:/home";
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
