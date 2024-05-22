package com.example.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.bazaar.dto.AddToCartInterface;
import com.example.bazaar.entity.AddToCart;
import com.example.bazaar.entity.Products;
import com.example.bazaar.repository.AddToCartRepository;
import com.example.bazaar.repository.ProductRepository;
import com.example.bazaar.service.AddToCartService;
import com.example.bazaar.service.ProductService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AddToCart.class)
public class CartTest {

	@Mock
	AddToCartRepository cartRepository;

	@InjectMocks
	AddToCartService cartService;

	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductService productService;

	@Test
	void createCart() {

		AddToCart request = new AddToCart(1, 1, 1, 2, 500, 1000);

		Products product = new Products(1, "mobile", "redmi", 500);

		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		when(cartRepository.findProduct(1, 1)).thenReturn(Optional.empty());

		cartService.createCart(request);

		verify(cartRepository, times(1)).saveAndFlush(request);

		AddToCart existingCart = new AddToCart(1, 1, 1, 3, 500, 1500);

		when(cartRepository.findProduct(1, 1)).thenReturn(Optional.of(existingCart));

		cartService.createCart(request);

		verify(cartRepository, times(1)).saveAndFlush(existingCart);
		assertEquals(5, existingCart.getQuantity());
		assertEquals(2500, existingCart.getTotalAmount());
	}

	@Test
	void getCartTest() {

		int customerId = 1;
		AddToCartInterface addToCartInterface = new AddToCartInterface() {
			public Double getTotalAmount() {
				return 1000d;
			}

			public Integer getQuantity() {
				return 2;
			}

			public Double getProductPrice() {
				return 500d;
			}

			public String getProductName() {
				return "mobile";
			}

			public Integer getId() {
				return 1;
			}

			public String getCustomerName() {
				return "Dhivya";
			}
		};
		when(cartRepository.findByCustomerId(customerId)).thenReturn(addToCartInterface);
		AddToCartInterface addToCartInterface2 = cartService.getByCustomerId(1);
		assertEquals(addToCartInterface, addToCartInterface2);
		verify(cartRepository).findByCustomerId(customerId);

	}

	@Test
	void getAllCartTest() {
		AddToCart request = new AddToCart(1, 1, 1, 2, 500, 1000);
		AddToCart request1 = new AddToCart(2, 2, 1, 2, 500, 1000);
		List<AddToCart> addToCarts = Arrays.asList(request, request1);
		when(cartRepository.findAll()).thenReturn(addToCarts);
		List<AddToCart> addToCarts2 = cartService.getAll();
		verify(cartRepository).findAll();
		assertEquals(addToCarts, addToCarts2);
	}

	@Test
	void deleteCartTest() {
		int cartId = 1;
		cartService.deleteById(cartId);
		verify(cartRepository).deleteById(cartId);
		assertEquals(cartRepository.findById(cartId), Optional.empty());
	}
}
