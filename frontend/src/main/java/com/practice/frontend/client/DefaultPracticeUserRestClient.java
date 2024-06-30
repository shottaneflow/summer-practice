package com.practice.frontend.client;

import java.util.List;
import java.util.Optional;

import com.practice.frontend.dto.JwtRequest;
import com.practice.frontend.dto.JwtResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UserDetails;
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
	public Optional<PracticeUser> loadUserFromContext() {
		
		try {
		return Optional.ofNullable(this.restClient.get()
				.uri("/user-api")
				.retrieve()
				.body(PracticeUser.class));
		}
		catch(HttpClientErrorException.NotFound eception) {
			return null;//Возвращаю null для подстановки в представление
		}
	}
	public PracticeUser findPracticeUserByUsername(String username){
		try {
			return this.restClient.get()
					.uri("user-api/{username}", username)
					.retrieve()
					.body(PracticeUser.class);
		}
		catch(HttpClientErrorException.NotFound eception) {
			return null;
		}

	}
	public  Optional<PracticeUser>  findByName(String username){

			return Optional.ofNullable(this.restClient.get()
					.uri("auth-api/{username}",username)
					.retrieve()
					.body(PracticeUser.class));



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
	public JwtResponse authenticate(JwtRequest jweRequest) throws  Exception{
		try {
			return this.restClient.post()
					.uri("/auth-api")
					.contentType(MediaType.APPLICATION_JSON)
					.body(jweRequest)
					.retrieve()
					.body(JwtResponse.class);
		}
		catch(HttpClientErrorException.Unauthorized exception) {
			ProblemDetail problemDetail=exception.getResponseBodyAs(ProblemDetail.class);
				throw new IllegalAccessException(problemDetail.getDetail());
		}


	}
	



}
