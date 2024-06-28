package com.practice.frontend.client;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.practice.frontend.entity.BankAccount;
import com.practice.frontend.entity.PracticeUser;

public class DefaultPracticeUserRestClient implements PracticeUserRestClient {

	private final RestClient restClient;
	
	public DefaultPracticeUserRestClient( RestClient restClient) {
		this.restClient=restClient;
	}
	@Override
	public Optional<PracticeUser> loadUserByUsername(String username) {
		
		try {
		return Optional.ofNullable(this.restClient.get()
				.uri("/user-api/{username}",username)
				.retrieve()
				.body(PracticeUser.class));
		}
		catch(HttpClientErrorException.NotFound eception) {
			return null;//Возвращаю null для подстановки в представление
		}
	}

	@Override
	public void save(PracticeUser user) {
		
		 this.restClient.post()
				.uri("/registration-api")
				.contentType(MediaType.APPLICATION_JSON)
				.body(user)
				.retrieve()
				.toBodilessEntity();
				
		
	}
	
	@Override
	public String getPincodeByBankAccountNumber(Long accountNumber) {
		
		return null;
	}


}
