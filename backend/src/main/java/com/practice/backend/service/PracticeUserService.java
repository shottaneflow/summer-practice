package com.practice.backend.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.practice.backend.entity.BankAccount;
import com.practice.backend.entity.PracticeUser;

public interface PracticeUserService extends UserDetailsService {
	void save(PracticeUser user);
	
	Optional<PracticeUser> findBankAccountById(Integer userId);
	Iterable<BankAccount> getBankAccounts(String username);
	PracticeUser findUserByUsername(String username);
	
}
