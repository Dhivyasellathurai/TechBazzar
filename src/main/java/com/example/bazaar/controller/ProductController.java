package com.example.bazaar.controller;

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

import com.example.bazaar.entity.Products;
import com.example.bazaar.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/create")
	public void createProduct(@RequestBody Products request) {
		productService.createProduct(request);
	}

	@GetMapping("/getById/{id}")
	public Optional<Products> getById(@PathVariable("id") Integer id) {
		return productService.getById(id);
	}

	@GetMapping("/getAll")
	public List<Products> getAll() {
		return productService.getAll();
	}

	@PutMapping("/update")
	public void update(@RequestBody Products request) {
		productService.createProduct(request);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") Integer id) {
		productService.deleteById(id);
	}
}
