package com.practice.frontend.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.practice.frontend.entity.*;
import com.practice.frontend.exceptions.InsufficientFunds;



public interface BankAccountRestClient {

	BankAccount create(String accountName,String username);
	Optional<BankAccount> findBankAccountByNumber(Long number);
	List<BankAccount> getAllBankAccounts();
	List<BankAccount> getBankAccounts(PracticeUser user);
	void moneyTransfer(Long debitAccount,Long replenishmentAccount,BigDecimal value) throws InsufficientFunds ;
}
