package com.practice.backend.config;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import com.practice.backend.entity.PracticeUser;
import com.practice.backend.repository.PracticeUserRepository;
import com.practice.backend.service.PracticeUserService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityBeans {
	
	
	private final PracticeUserService practiceUserService;

	private final JwtRequestFilter jwtRequestFilter;
	
	public SecurityBeans(JwtRequestFilter jwtRequestFilter,
						 PracticeUserService practiceUserService) {
		this.practiceUserService=practiceUserService;
		this.jwtRequestFilter=jwtRequestFilter;
	}
	
	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {

				 http
						 .csrf(csrf->csrf.disable())
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
		            )
						 .exceptionHandling(exceptionHandling->exceptionHandling
								 .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
								 )
						 .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

						 ;



				 

		        return http.build();
	}

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

	 @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(practiceUserService); 
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
	

}
