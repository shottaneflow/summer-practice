package com.practice.backend.controller;

import com.practice.backend.entity.PracticeUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.practice.backend.exceptions.*;
import org.springframework.security.core.userdetails.UserDetails;
import com.practice.backend.dto.JwtRequest;
import com.practice.backend.service.PracticeUserService;
import com.practice.backend.util.JwtTokenUtils;


@RestController
@RequestMapping("/auth-api")
public class AuthController {

	
	private final PracticeUserService practiceUserService;
	
	private final JwtTokenUtils jwtTokenUtils;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthController(PracticeUserService practiceUserService,JwtTokenUtils jwtTokenUtils,
						  AuthenticationManager authenticationManager) {
		this.practiceUserService=practiceUserService;
		this.jwtTokenUtils= jwtTokenUtils;
		this.authenticationManager= authenticationManager;
		
	
	}
	@PostMapping()
	public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
				authRequest.getPincode()));
		}
		catch(BadCredentialsException e) {
			return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"Неправильный  логин или пароль"),HttpStatus.UNAUTHORIZED);
		}
		UserDetails userDetails=practiceUserService.loadUserByUsername(authRequest.getUsername());

		String token=jwtTokenUtils.generateToken(userDetails);
		return ResponseEntity.ok(token);
				
	}
	@GetMapping("{username}")
	public PracticeUser findByUsername(@PathVariable String username){
		return this.practiceUserService.findUserByUsername(username);
	}
}
