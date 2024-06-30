package com.practice.frontend.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.practice.frontend.dto.MoneyTransferRequest;
import com.practice.frontend.entity.*;
import com.practice.frontend.exceptions.InsufficientFunds;



public interface BankAccountRestClient {

	BankAccount create(String accountName);
	Optional<BankAccount> findBankAccountByNumber(Long number);
	List<BankAccount> getAllBankAccounts();
	List<BankAccount> getBankAccounts();
	void moneyTransfer(Long debitAccount, MoneyTransferRequest moneyTransferRequest) throws InsufficientFunds ;
}
