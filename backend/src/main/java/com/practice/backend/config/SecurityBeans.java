package com.practice.backend.config;

import com.practice.backend.token.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.practice.backend.entity.Authority;
import com.practice.backend.entity.PracticeUser;
import com.practice.backend.repository.PracticeUserRepository;
import com.practice.backend.service.PracticeUserService;
import com.practice.backend.token.GetCsrfTokenFilter;
import com.practice.backend.token.TokenCookieAuthenticationConfigurer;
import com.practice.backend.token.TokenCookieAuthenticationStrategy;
import com.practice.backend.token.TokenCookieJweStringSerializer;


@Configuration
@EnableWebSecurity
public class SecurityBeans {
	
	
	private final PracticeUserService practiceUserService;
	
	public SecurityBeans(JwtRequestFilter jwtRequestFilter,PracticeUserService practiceUserService) {
		this.practiceUserService=practiceUserService;
	}
	
	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http,
			 								 TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer,
			 								 TokenCookieJweStringSerializer tokenCookieJweStringSerialazer) throws Exception {
	 
				 var strategy=new TokenCookieAuthenticationStrategy();
				 strategy.setTokenStringSerializer(tokenCookieJweStringSerialazer);
				 http.httpBasic(Customizer.withDefaults())
				 	.addFilterAfter(new GetCsrfTokenFilter(), ExceptionTranslationFilter.class)
		            .authorizeHttpRequests(authorizeRequests ->
		                authorizeRequests
		                    .requestMatchers("/bankAccount-api/**").authenticated()
		                    .requestMatchers("/auth-api/**").permitAll()
		                    .requestMatchers("/registration-api/**").permitAll()          
		                    .requestMatchers("/user-api/**").authenticated()
		                    .requestMatchers("/admin-api/**").hasRole("ADMIN")
		                    .anyRequest().authenticated()
		            )
		            .sessionManagement(sessionManagement ->
		                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		                .sessionAuthenticationStrategy(strategy)
		            )
		            .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository())
	                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
	                        .sessionAuthenticationStrategy((authentication, request, response) -> {}));
		            
				 	http.apply(tokenCookieAuthenticationConfigurer);
				 

		        return http.build();
	}
	
	@Bean
	public TokenCookieJweStringSerializer tokenCookieJweStringSerialazer(
			@Value("${jwt.cookie-token-key}") String cookieTokenKey) throws Exception
	{
		return new TokenCookieJweStringSerializer(new DirectEncrypter(
				OctetSequenceKey.parse(cookieTokenKey)));
	}
	
	 @Bean
	    public TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer(
	            @Value("${jwt.cookie-token-key}") String cookieTokenKey,
	            PracticeUserRepository practiceUserRepository
	    ) throws Exception {
	        return new TokenCookieAuthenticationConfigurer()
	                .tokenCookieStringDeserializer(new TokenCookieJweStringDeserializer(
	                        new DirectDecrypter(
	                                OctetSequenceKey.parse(cookieTokenKey)
	                        )
	                ))
	                .practiceUserRepository(practiceUserRepository);
	    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(PracticeUserRepository practiceUserRepository) {
	    return username -> {
	        Optional<PracticeUser> optionalUser = practiceUserRepository.findByUsername(username);
	        if (optionalUser.isEmpty()) {
	            throw new UsernameNotFoundException("User not found");
	        }

	        PracticeUser practiceUser = optionalUser.get();

	        List<GrantedAuthority> grantedAuthorities = practiceUser.getAuthorities().stream()
	                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
	                .collect(Collectors.toList());

	        return User.builder()
	                .username(practiceUser.getUsername())
	                .password(practiceUser.getPincode())
	                .authorities(grantedAuthorities)
	                .build();
	    };
	}


	
	/*
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}*/
	
	
	
	/*
	 @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(practiceUserService); 
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }*/
	

}
