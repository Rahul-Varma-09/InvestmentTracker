package com.investment.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment.app.entities.User;
import com.investment.app.http.GenericResponse;
import com.investment.app.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*")
public class UserController {
	
	@Autowired private UserService userService;
	
	@PostMapping("/login/req")
	public GenericResponse<?> loginRequest(@RequestBody User user) {
		return userService.login(user);
	}
	
	@PostMapping("/login/otp/verify")
	public GenericResponse<?> verifyOtp(@RequestBody User user) {
		return userService.verifyOtp(user);
	}
	
	
	

}
