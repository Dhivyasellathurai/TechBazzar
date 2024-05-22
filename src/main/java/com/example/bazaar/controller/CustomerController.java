package com.example.bazaar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bazaar.dto.CustomerDto;
import com.example.bazaar.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/create")
	public void create(@RequestBody CustomerDto customerDtoRequest) {
		try {
			customerService.createCustomer(customerDtoRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/getById/{id}")
	public CustomerDto getById(@PathVariable("id") Integer id) {
		return customerService.getById(id);
	}

	@GetMapping("/getAll")
	public List<CustomerDto> getAll() {
		return customerService.getAll();
	}

	@PutMapping("/update")
	public void update(@RequestBody CustomerDto customerDto) {
		customerService.createCustomer(customerDto);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") Integer id) {
		customerService.deleteById(id);
	}

}
