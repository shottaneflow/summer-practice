package com.practice.backend.token;

import jakarta.servlet.http.Cookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Function;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenCookieAuthenticationStrategy implements SessionAuthenticationStrategy {

	private Function<Authentication,Token> tokenCookieFactory=new DefaultTokenCookieFactory();
	
	private Function<Token,String> tokenStringSerializer=Objects::toString;
	
	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionAuthenticationException {
		
		if(authentication instanceof UsernamePasswordAuthenticationToken) {
			var token=this.tokenCookieFactory.apply(authentication);
			var tokenString=this.tokenStringSerializer.apply(token);
			
			var cookie=new Cookie("__Host-auth-token",tokenString);
			cookie.setDomain(null);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setSecure(true);
			cookie.setMaxAge((int)ChronoUnit.SECONDS.between(Instant.now(), token.expiresAt()));
			
			response.addCookie(cookie);
		}
		
	}

	public Function<Authentication, Token> getTokenCookieFactory() {
		return tokenCookieFactory;
	}

	public void setTokenCookieFactory(Function<Authentication, Token> tokenCookieFactory) {
		this.tokenCookieFactory = tokenCookieFactory;
	}

	public Function<Token, String> getTokenStringSerializer() {
		return tokenStringSerializer;
	}

	public void setTokenStringSerializer(Function<Token, String> tokenStringSerializer) {
		this.tokenStringSerializer = tokenStringSerializer;
	}

}
