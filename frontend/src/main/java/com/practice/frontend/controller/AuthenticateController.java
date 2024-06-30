package com.practice.frontend.controller;


import com.practice.frontend.client.PracticeUserRestClient;
import com.practice.frontend.dto.JwtRequest;
import com.practice.frontend.dto.JwtResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller()
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;

    private final PracticeUserRestClient practiceUserRestClient;

    public AuthenticateController(AuthenticationManager authenticationManager,
                                  PracticeUserRestClient practiceUserRestClient) {
        this.authenticationManager = authenticationManager;
        this.practiceUserRestClient = practiceUserRestClient;
    }

    @GetMapping("/login")
    public String login() {return "login";}

    @PostMapping("/login")
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("pincode") String pincode,
                               Model model) throws Exception {
        try {
            JwtResponse jwtResponse = this.practiceUserRestClient.authenticate(new JwtRequest(username, pincode));
            return "redirect:/home";
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "exception";
        }


    }

}
