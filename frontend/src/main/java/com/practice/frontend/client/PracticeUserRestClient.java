package com.practice.frontend.client;

import java.util.List;
import java.util.Optional;

import com.practice.frontend.entity.BankAccount;
import com.practice.frontend.entity.PracticeUser;

public interface PracticeUserRestClient {

	Optional<PracticeUser> loadUserByUsername(String username);
	void save(PracticeUser user);

	String getPincodeByBankAccountNumber(Long accountNumber);
	
	
}
