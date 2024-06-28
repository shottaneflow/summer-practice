package com.practice.backend.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import com.practice.backend.entity.BankAccount;
import com.practice.backend.entity.PracticeUser;

public interface PracticeUserService extends  AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>  {
	void save(PracticeUser user);
	
	Optional<PracticeUser> findBankAccountById(Integer userId);
	Iterable<BankAccount> getBankAccounts(String username);
	PracticeUser findUserByUsername(String username);
	
}
