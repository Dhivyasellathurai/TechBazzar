package com.example.bazaar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bazaar.dto.AddToCartInterface;
import com.example.bazaar.entity.AddToCart;
import com.example.bazaar.service.AddToCartService;

@RestController
@RequestMapping("/api/cart")
public class AddToCartController {

	@Autowired
	private AddToCartService addToCartService;

	

	@PostMapping("/create")
	public void createCart(@RequestBody AddToCart addToCart) {
		addToCartService.createCart(addToCart);
	}

	@GetMapping("/getById/{customerId}")
	public AddToCartInterface getById(@PathVariable("customerId") Integer customerId) {
		AddToCartInterface response = addToCartService.getByCustomerId(customerId);
		return response;
	}

	@GetMapping("/getAll")
	public List<AddToCart> getAll(@RequestHeader HttpHeaders httpHeader) {
		List<AddToCart> list = addToCartService.getAll();
		return list;
	}

	@PutMapping("/update")
	public void update(@RequestBody AddToCart addToCart) {
		addToCartService.createCart(addToCart);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		addToCartService.deleteById(id);
	}

}
