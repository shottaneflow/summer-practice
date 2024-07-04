package com.practice.frontend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.practice.frontend.service.PracticeUserDetailService;




@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final PracticeUserDetailService userDetailsService;

	private final PasswordEncoder passwordEncoder;
	
	public WebSecurityConfig(PracticeUserDetailService userDetailsService,
							 PasswordEncoder passwordEncoder) {
		this.userDetailsService=userDetailsService;
		this.passwordEncoder=passwordEncoder;
	}
	
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests((requests)->requests
					.requestMatchers("/registration/**").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
					)
					.formLogin((form)->form
							.loginPage("/login").permitAll()
							.defaultSuccessUrl("/home",true)
					)
					.logout(LogoutConfigurer::permitAll);
		return http.build();	
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}




	   @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder);
	        return authProvider;
	    }

	
	
}
