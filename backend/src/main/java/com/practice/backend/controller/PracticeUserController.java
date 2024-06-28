package com.practice.backend.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/user-api/{username:[a-zA-Z0-9]+}")
public class PracticeUserController {

	private final  PracticeUserService practiceUserService;
	
	public PracticeUserController(PracticeUserService practiceUserService) {
		this.practiceUserService=practiceUserService;
	}
	
	@ModelAttribute("user")
	public PracticeUser getPracticeUser(Principal principal) {
		return (PracticeUser)this.practiceUserService.loadUserByUsername(principal.getName());
				
	}
	
	@GetMapping()
	public PracticeUser getUserByUsername(@ModelAttribute("user") PracticeUser practiceUser) {
		return practiceUser;
		
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage()));
	}
}
