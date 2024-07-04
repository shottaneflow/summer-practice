package com.practice.frontend.service;

import com.practice.frontend.client.AuthRegistrationRestClient;
import com.practice.frontend.dto.JwtRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.frontend.entity.Authority;


@Service
public class PracticeUserDetailService implements UserDetailsService {


	private final AuthRegistrationRestClient authRegistrationRestClient;


	private final  PasswordEncoder passwordEncoder;
	private final HttpSession httpSession;

	public PracticeUserDetailService(AuthRegistrationRestClient authRegistrationRestClient,
									 PasswordEncoder passwordEncoder, HttpSession httpSession) {
		this.authRegistrationRestClient=authRegistrationRestClient;
		this.passwordEncoder=passwordEncoder;
		this.httpSession = httpSession;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails= this.authRegistrationRestClient.findByName(username)
				.map(user->User.builder()
						.username(user.getUsername())
						.password(user.getPincode())
						.authorities(user.getAuthorities().stream()
								.map(Authority::getAuthority)
								.map(SimpleGrantedAuthority::new)
								.toList())
						.build())
				.orElseThrow(()->new UsernameNotFoundException("User %S not found".formatted(username)));
		JwtRequest jwtRequest=new JwtRequest(username,userDetails.getPassword());

		try {
			String token=this.authRegistrationRestClient.authenticate(jwtRequest);
			httpSession.setAttribute("jwtToken",token);
		}
		catch(Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		return userDetails;


	}

}
