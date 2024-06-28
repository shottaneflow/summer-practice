package com.practice.frontend.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.frontend.client.PracticeUserRestClient;
import com.practice.frontend.entity.Authority;
import lombok.RequiredArgsConstructor;



@Service
public class PracticeUserDetailService implements UserDetailsService {


	private final PracticeUserRestClient practiceUserRestClient;
	
	public PracticeUserDetailService(PracticeUserRestClient practiceUserRestClient) {
		this.practiceUserRestClient=practiceUserRestClient;
		
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.practiceUserRestClient.loadUserByUsername(username)
				.map(user->User.builder()
						.username(user.getUsername())
						.password(user.getPincode())
						.authorities(user.getAuthorities().stream()
								.map(Authority::getAuthority)
								.map(SimpleGrantedAuthority::new)
								.toList())
						.build())
				.orElseThrow(()->new UsernameNotFoundException("User %S not found".formatted(username)));
			
	}

}
