package com.practice.frontend.client;

import java.util.List;
import java.util.Optional;

import com.practice.frontend.dto.JwtRequest;
import com.practice.frontend.dto.JwtResponse;
import com.practice.frontend.entity.BankAccount;
import com.practice.frontend.entity.PracticeUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface PracticeUserRestClient {

	Optional<PracticeUser> loadUserFromContext();
	void save(PracticeUser user);
	JwtResponse authenticate(JwtRequest jweRequest) throws  Exception;
	PracticeUser findPracticeUserByUsername(String username);
	Optional<PracticeUser> findByName(String username);
	
	
}
