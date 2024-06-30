package com.practice.backend.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.backend.entity.BankAccount;
import com.practice.backend.entity.PracticeUser;
import com.practice.backend.service.PracticeUserService;

@RestController
@RequestMapping("/user-api")
public class PracticeUserController {

	private final  PracticeUserService practiceUserService;
	
	public PracticeUserController(PracticeUserService practiceUserService) {
		this.practiceUserService=practiceUserService;
	}
	
	@ModelAttribute("user")
	public PracticeUser getPracticeUser(Principal principal) {
		return this.practiceUserService.findUserByUsername(principal.getName());
				
	}
	
	@GetMapping()
	public PracticeUser getUserFromContext(@ModelAttribute("user") PracticeUser practiceUser) {
		return practiceUser;
		
	}
	@GetMapping("{username}")
	public PracticeUser getUserByUsername(@PathVariable("username") String username) {
		return this.practiceUserService.findUserByUsername(username);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage()));
	}
}
