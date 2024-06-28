package com.practice.backend.token;

import java.sql.Date;
import com.practice.backend.service.*;
import java.util.function.Function;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;

import com.practice.backend.entity.PracticeUser;
import com.practice.backend.repository.PracticeUserRepository;

import jakarta.servlet.http.HttpServletResponse;


public class TokenCookieAuthenticationConfigurer
	extends AbstractHttpConfigurer<TokenCookieAuthenticationConfigurer, HttpSecurity>{
	
	private Function<String,Token> tokenCookieStringDeserializer;	
	
	private PracticeUserRepository practiceUserRepository;
	
	
	
	@Override
	public void init(HttpSecurity builder) throws Exception{
		builder.logout(logout->logout.addLogoutHandler(
				new CookieClearingLogoutHandler("__Host-auth-token"))
				.addLogoutHandler((request,response,authentication)->{
					if(authentication!=null &&
							authentication.getPrincipal() instanceof PracticeUser user) {
						this.practiceUserRepository.deactivateToken(user.getToken().id(), Date.from(user.getToken().expiresAt()));
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
				}));
	}
	
	@Override 
	public void configure(HttpSecurity builder) throws Exception{
		var cookieAuthenticationFilter=new AuthenticationFilter(
				builder.getSharedObject(AuthenticationManager.class),
				new TokenCookieAuthenticationConverter(this.tokenCookieStringDeserializer));
		cookieAuthenticationFilter.setSuccessHandler((request,response,authentication)->{});
		cookieAuthenticationFilter.setFailureHandler(
				new AuthenticationEntryPointFailureHandler(
						new Http403ForbiddenEntryPoint())
				);
				
		var authenticationProvider=new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(new DefaultPracticeUserService(this.practiceUserRepository));
		builder.addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
			.authenticationProvider(authenticationProvider);
	}


	public TokenCookieAuthenticationConfigurer tokenCookieStringDeserializer(Function<String, Token> tokenCookieStringDeserializer) {
		this.tokenCookieStringDeserializer = tokenCookieStringDeserializer;
		return this;
	}

	
	public TokenCookieAuthenticationConfigurer practiceUserRepository(PracticeUserRepository practiceUserRepository) {
        this.practiceUserRepository = practiceUserRepository;
        return this;
    }
	
	  
	   
}
