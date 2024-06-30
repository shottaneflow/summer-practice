package com.practice.frontend.controller;

import java.util.Collections;
import java.util.Objects;

import java.util.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.frontend.client.PracticeUserRestClient;
import com.practice.frontend.entity.Authority;
import com.practice.frontend.entity.PracticeUser;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

	private final PracticeUserRestClient practiceUserRestClient;
	
	
	
	public RegistrationController(PracticeUserRestClient practiceUserRestClient) {
		this.practiceUserRestClient=practiceUserRestClient;
	}
	
	@GetMapping
	public String getRegistrationPage() {
		return "registration";
	}
	@PostMapping
	public String registration(@RequestParam(name="username") String username,
							   @RequestParam(name="pincode") String pincode,		
							   Model model) {
		if(practiceUserRestClient.findPracticeUserByUsername(username)!=null) {
			model.addAttribute("message","Пользователь уже существует!");
			return "registration";
		}
		PracticeUser user=new PracticeUser();
		user.setUsername(username);
		user.setPincode(new BCryptPasswordEncoder().encode(pincode));
		Authority auth=new Authority();
		auth.setId(2);
		auth.setAuthority("ROLE_USER");
		user.addAuthorities(auth);
		
		this.practiceUserRestClient.save(user);

		
		
		
		
		return "redirect:/login";
	}
}
