package com.practice.frontend.security;

import com.practice.frontend.dto.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.frontend.client.PracticeUserRestClient;
import com.practice.frontend.entity.Authority;
import lombok.RequiredArgsConstructor;



@Service
public class PracticeUserDetailService implements UserDetailsService {


	private final PracticeUserRestClient practiceUserRestClient;


	private final  PasswordEncoder passwordEncoder;

	public PracticeUserDetailService(PracticeUserRestClient practiceUserRestClient,
									 PasswordEncoder passwordEncoder) {
		this.practiceUserRestClient=practiceUserRestClient;
		this.passwordEncoder=passwordEncoder;


		
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return this.practiceUserRestClient.findByName(username)
				.map(user->User.builder()
						.username(user.getUsername())
						.password(user.getPincode())
						.authorities(user.getAuthorities().stream()
								.map(Authority::getAuthority)
								.map(SimpleGrantedAuthority::new)
								.toList())
						.build())
				.orElseThrow(()->new UsernameNotFoundException("User %S not found".formatted(username)));

/*
		return this.practiceUserRestClient.loadUserFromContext()
				.map(user->User.builder()
						.username(user.getUsername())
						.password(user.getPincode())
						.authorities(user.getAuthorities().stream()
								.map(Authority::getAuthority)
								.map(SimpleGrantedAuthority::new)
								.toList())
						.build())
				.orElseThrow(()->new UsernameNotFoundException("User %S not found".formatted(username)));*/
			
	}

}
