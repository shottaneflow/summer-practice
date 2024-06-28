package com.practice.frontend.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import com.practice.frontend.dto.*;
import com.practice.frontend.entity.*;
import com.practice.frontend.exceptions.InsufficientFunds;



public class DefaultBankAccountRestClient implements BankAccountRestClient {

	private static final ParameterizedTypeReference<List<BankAccount>> BANK_ACCOUNTS_TYPE_REFERENCE=
			new ParameterizedTypeReference<>() {
		
			};
	
	private final RestClient restClient;
	
	public DefaultBankAccountRestClient(RestClient restClient) {
		this.restClient=restClient;
	}
	@Override
	public BankAccount create(String accountName,String username) {
		try {
		return this.restClient.post()
				.uri("bankAccount-api/{username}",username)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new BankAccount(accountName))
				.retrieve()
				.body(BankAccount.class);
		}
		catch(HttpClientErrorException.BadRequest exception) {
			ProblemDetail problemDetail=exception.getResponseBodyAs(ProblemDetail.class);
			problemDetail.getProperties().get("errors");
			throw new BadRequest((List<String>)problemDetail.getProperties().get("errors"));
		}
				
	}

	@Override
	public Optional<BankAccount> findBankAccountByNumber(Long number) {
		try {
		return Optional.ofNullable(this.restClient.get()
				.uri("/bankAccount-api/bankAccount/{accountNumber}",number)
				.retrieve()
				.body(BankAccount.class));
		}
		catch(HttpClientErrorException.NotFound eception) {
			return Optional.empty();
		}
	}

	@Override
	public void moneyTransfer(Long debitAccount, Long replenishmentAccount, BigDecimal value) throws InsufficientFunds {
		try {
		this.restClient.patch()
		.uri("/bankAccount-api/bankAccount/{accountNumber}",debitAccount)
		.contentType(MediaType.APPLICATION_JSON)
		.body(new MoneyTransferRequest(replenishmentAccount,value))
		.retrieve()
		.toBodilessEntity();
		}
		catch(HttpClientErrorException.BadRequest   exception) {
			ProblemDetail problemDetail=exception.getResponseBodyAs(ProblemDetail.class);
			problemDetail.getProperties().get("errors");
			throw new BadRequest((List<String>)problemDetail.getProperties().get("errors"));
		}
		catch(HttpClientErrorException.NotFound exception) {
			 ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
	         throw new NoSuchElementException(problemDetail.getDetail());
			
		}
		catch(HttpClientErrorException.NotAcceptable exception) {
			ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
	         throw new InsufficientFunds(problemDetail.getDetail());
		}
		
		
	}
	@Override
	public List<BankAccount> getAllBankAccounts() {
		return this.restClient.get()
				.uri("/admin-api/bankAccounts")
				.retrieve()
				.body(BANK_ACCOUNTS_TYPE_REFERENCE);
	}
	@Override
	public List<BankAccount> getBankAccounts(PracticeUser user) {
		return this.restClient.get()
				.uri("/bankAccount-api/{username}",user.getUsername())
				.retrieve()
				.body(BANK_ACCOUNTS_TYPE_REFERENCE);
	}
	

}
