package com.practice.backend.token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class DefaultTokenCookieFactory implements Function<Authentication, Token> {
	
	
	 private Duration tokenTtl = Duration.ofMinutes(30);

	    @Override
	    public Token apply(Authentication authentication) {
	        var now = Instant.now();
	        return new Token(UUID.randomUUID(), authentication.getName(),
	                authentication.getAuthorities().stream()
	                        .map(GrantedAuthority::getAuthority).toList(),
	                now, now.plus(this.tokenTtl));
	    }

	    public void setTokenTtl(Duration tokenTtl) {
	        this.tokenTtl = tokenTtl;
	    }
}
