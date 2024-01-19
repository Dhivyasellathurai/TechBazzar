package com.example.bazaar.controller;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bazaar.entity.User;
import com.example.bazaar.service.UserService;

import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.JRException;

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
	public Optional<User> getById(@PathVariable("id") Integer id) {
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

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") Integer id) {
		userService.delete(id);
	}

	@GetMapping("/report/{format}")
	public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
		return userService.exportReport(format);
	}

}
