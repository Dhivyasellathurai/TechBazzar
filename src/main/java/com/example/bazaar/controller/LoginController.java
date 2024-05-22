package com.example.bazaar.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bazaar.entity.Errors;
import com.example.bazaar.entity.LoginRequest;
import com.example.bazaar.entity.User;
import com.example.bazaar.repository.UserRepository;
import com.example.bazaar.security.JwtTokenUtil;
import com.example.bazaar.util.PasswordUtil;

@RestController
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping(value = "/login")
	public Map<String, Object> login(@RequestBody LoginRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		Errors err = null;
		if (request == null) {
			err = new Errors();
			err.setCode("400");
			err.setMessage("Invalid incredintials");
			response.put("error", err);
			return response;
		}

		Optional<User> user = userRepository.findByUserName(request.getUserName());
		if (!user.isPresent()) {
			err = new Errors();
			err.setCode("400");
			err.setMessage("Invalid Username");
			response.put("status", 0);
			response.put("message", err);
			return response;
		}
		User user1 = user.get();
		String password = PasswordUtil.getEncryptedPassword(request.getPassword());
		if (!user1.getPassword().equals(password)) {
			err = new Errors();
			err.setCode("400");
			err.setMessage("Password is wrong.!");
			response.put("status", 0);
			response.put("error", err);
			return response;
		}
		final String token = jwtTokenUtil.generateToken(user1);
		response.put("status", 1);
		response.put("message", "Logged in successfully.!");
		response.put("jwt", token);
		response.put("userId", user1.getId());
		return response;
	}
}