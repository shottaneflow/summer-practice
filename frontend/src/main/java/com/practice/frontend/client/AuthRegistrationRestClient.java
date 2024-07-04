package com.practice.frontend.client;

import com.practice.frontend.dto.JwtRequest;
import com.practice.frontend.entity.PracticeUser;

import java.util.Optional;

public interface AuthRegistrationRestClient {
    Optional<PracticeUser> findByName(String username);
    void save(PracticeUser user);
    String authenticate(JwtRequest jweRequest) throws  Exception;

}
