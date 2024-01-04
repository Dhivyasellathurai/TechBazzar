package com.example.sales.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sales.entity.User;
import com.example.sales.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app/sales")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/create")
	@ApiOperation(value = "Allows to create Herbal product.")
	public void create(@RequestBody User request) {
		userService.create(request);
	}

	@GetMapping("/getById/{id}")
	public Optional<User> getById(@PathVariable("id") UUID id) {
		return userService.getById(id);
	}

	@GetMapping("/getAll")
	public List<User> getAll() {
		return userService.getAll();

	}

	@PutMapping("/update")
	public void update(@RequestBody User request) {
		userService.update(request);
	}

	@DeleteMapping("/delete")
	public void deleteById(@PathVariable("id") UUID id) {
		userService.delete(id);
	}

}
