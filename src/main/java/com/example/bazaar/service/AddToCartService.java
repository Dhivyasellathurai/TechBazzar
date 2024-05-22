package com.example.bazaar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bazaar.dto.AddToCartInterface;
import com.example.bazaar.entity.AddToCart;
import com.example.bazaar.entity.Products;
import com.example.bazaar.repository.AddToCartRepository;
import com.example.bazaar.repository.ProductRepository;

@Service
public class AddToCartService {

	@Autowired
	private AddToCartRepository addToCartRepository;
	@Autowired
	private ProductRepository productRepository;

	public void createCart(AddToCart request) {
		Optional<Products> product = productRepository.findById(request.getProductId());
		Optional<AddToCart> addOptional = addToCartRepository.findProduct(request.getProductId(),
				request.getCustomerId());
		Products productDetails = product.get();
		if (addOptional.isPresent()) {
			AddToCart cart = addOptional.get();
			int quantity = cart.getQuantity() + request.getQuantity();
			cart.setQuantity(quantity);
			cart.setTotalAmount(quantity * productDetails.getPrice());
			addToCartRepository.saveAndFlush(cart);

		} else {
			request.setProductPrice(productDetails.getPrice());
			request.setTotalAmount(request.getQuantity() * request.getProductPrice());
			addToCartRepository.saveAndFlush(request);
		}
	}

	public AddToCartInterface getByCustomerId(Integer customerId) {
		return addToCartRepository.findByCustomerId(customerId);
	}

	public List<AddToCart> getAll() {
		return addToCartRepository.findAll();
	}

	public void deleteById(Integer id) {
		addToCartRepository.deleteById(id);

	}
}
