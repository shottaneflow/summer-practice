package com.practice.backend.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.backend.entity.PracticeUser;
import com.practice.backend.service.PracticeUserService;

@RestController
@RequestMapping("/registration-api")
public class RegistrationController {
	
private final  PracticeUserService practiceUserService;
private final BCryptPasswordEncoder passwordencoder;
	
	public RegistrationController(PracticeUserService practiceUserService,BCryptPasswordEncoder passwordencoder) {
		this.practiceUserService=practiceUserService;
		this.passwordencoder=passwordencoder;
		
		
	}
	@PostMapping()
	public  void saveUser(@RequestBody PracticeUser practiceUser) {
		practiceUser.setPincode(passwordencoder.encode(practiceUser.getPincode()));
		this.practiceUserService.save(practiceUser);
		
	}
}
