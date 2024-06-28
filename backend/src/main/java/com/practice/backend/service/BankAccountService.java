package com.practice.backend.service;
import java.math.BigDecimal;
import java.util.Optional;
import com.practice.backend.entity.BankAccount;
import com.practice.backend.exceptions.InsufficientFunds;

public interface BankAccountService {
	BankAccount create(String name);
	Optional<BankAccount> findBankAccountByNumber(Long number);
	Iterable<BankAccount> getBankAccounts();
	void moneyTransfer(Long debitAccount,Long replenishmentAccount,BigDecimal value) throws InsufficientFunds;
	
}
