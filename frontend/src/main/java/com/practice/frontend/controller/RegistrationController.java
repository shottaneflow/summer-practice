package com.practice.frontend.controller;

import com.practice.frontend.client.AuthRegistrationRestClient;
import com.practice.frontend.exceptions.InsufficientFunds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.practice.frontend.entity.Authority;
import com.practice.frontend.entity.PracticeUser;


@Controller
@RequestMapping("/registration")
public class RegistrationController {


	private final AuthRegistrationRestClient authRegistrationRestClient;
	
	
	
	public RegistrationController(AuthRegistrationRestClient authRegistrationRestClient) {
		this.authRegistrationRestClient=authRegistrationRestClient;
	}
	
	@GetMapping
	public String getRegistrationPage() {
		return "registration";
	}
	@PostMapping
	public String registration(@RequestParam(name="username") String username,
							   @RequestParam(name="pincode") String pincode,		
							   Model model) throws Exception {
		if(pincode.length()!=4) {
			throw new InsufficientFunds("Длина пинкода должна быть 4 символа");
		}
		try {


			if (authRegistrationRestClient.findByName(username)!=null) {
				model.addAttribute("message", "Пользователь уже существует!");
				return "registration";
			}
		}
		catch (Exception e) {
			model.addAttribute("message", e.getMessage());

		}

		PracticeUser user=new PracticeUser();
		user.setUsername(username);
		user.setPincode(new BCryptPasswordEncoder().encode(pincode));
		Authority auth=new Authority();
		auth.setId(2);
		auth.setAuthority("ROLE_USER");
		user.addAuthorities(auth);

		this.authRegistrationRestClient.save(user);





		return "redirect:/login";

	}
	@ExceptionHandler(InsufficientFunds.class)
	public String handleInsufficientFundsException(InsufficientFunds exception, Model model) {
		model.addAttribute("errorMessage", exception.getMessage());
		return "exception";
	}
}
