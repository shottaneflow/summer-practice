package com.practice.frontend.client;

import com.practice.frontend.dto.JwtRequest;
import com.practice.frontend.entity.PracticeUser;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Optional;

public class DefaultAuthRegistrationRestClient implements AuthRegistrationRestClient {

    private final RestClient restClient;

    public DefaultAuthRegistrationRestClient(RestClient restClient) {this.restClient = restClient;}

    @Override
    public Optional<PracticeUser> findByName(String username){

        return Optional.ofNullable(this.restClient.get()
                .uri("/auth-api/{username}",username)
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
    @Override
    public String authenticate(JwtRequest jweRequest) throws Exception {
        try {
            return this.restClient.post()
                    .uri("/auth-api")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jweRequest)
                    .retrieve()
                    .body(String.class);
        }
        catch(HttpClientErrorException.Unauthorized exception) {
            ProblemDetail problemDetail=exception.getResponseBodyAs(ProblemDetail.class);
            throw new IllegalAccessException(problemDetail.getDetail());
        }


    }



}
