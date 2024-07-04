package com.practice.backend.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.practice.backend.entity.BankAccount;
import com.practice.backend.exceptions.InsufficientFunds;
import com.practice.backend.repository.BankAccountRepository;

@Service
public class DefaultBankAccountService implements BankAccountService {

	private final BankAccountRepository bankAccountRepository;
	
	public DefaultBankAccountService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository=bankAccountRepository;
	}
	
	@Override
	@Transactional
	public BankAccount create(String name) {
		BankAccount account=new BankAccount();
		account.setAccountName(name);
		account.setMoney(BigDecimal.ZERO);
		return this.bankAccountRepository.save(account);
		
	}

	@Override
	public Optional<BankAccount> findBankAccountByNumber(Long number) {
		return this.bankAccountRepository.findById(number);
	}

	@Override
	@Transactional
	public void moneyTransfer(Long debitAccount, Long replenishmentAccount,BigDecimal value) throws InsufficientFunds {
		BankAccount debit=this.bankAccountRepository.findById(debitAccount)
				.orElseThrow(()-> new NoSuchElementException("Аккаунт с которого списываються средства не найден"));
		BankAccount replenishment=this.bankAccountRepository.findById(replenishmentAccount)
				 .orElseThrow(()-> new NoSuchElementException("Аккаунт на который зачисляються  средства не найден"));
		if(debit.getMoney().compareTo(value)>=0) {
			   debit.setMoney(debit.getMoney().subtract(value));
			   replenishment.setMoney(replenishment.getMoney().add(value));
			}
			else {
				throw new InsufficientFunds("Недостаточно средств");
			}
		
		
	}

	@Override
	public Iterable<BankAccount> getBankAccounts() {
		return this.bankAccountRepository.findAll();
	}

	

	

}
