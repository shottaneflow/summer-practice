package com.practice.backend.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.practice.backend.entity.PracticeUser;
import com.practice.backend.service.PracticeUserService;

@RestController
@RequestMapping("/registration-api")
public class RegistrationController {
	
private final  PracticeUserService practiceUserService;

	
	public RegistrationController(PracticeUserService practiceUserService) {
		this.practiceUserService=practiceUserService;
	}
	@PostMapping()
	public  void saveUser(@RequestBody PracticeUser practiceUser) {
		practiceUser.setPincode(practiceUser.getPincode());
		this.practiceUserService.save(practiceUser);
		
	}

}
