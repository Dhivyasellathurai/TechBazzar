package com.example.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.bazaar.entity.Products;
import com.example.bazaar.repository.ProductRepository;
import com.example.bazaar.service.ProductService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes =Products.class)
public class ProductTest {

	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductService productService;

	@Test
	void createProductTest() {
		Products products = new Products(1, "IPhone", "Apple", 100000);
		when(productRepository.saveAndFlush(products)).thenReturn(products);
		productService.createProduct(products);
		verify(productRepository).saveAndFlush(products);
	}

	@Test
	void getProductTest() {
		Products products = new Products(1, "IPhone", "Apple", 100000);
		Optional<Products> optional = Optional.of(products);
		when(productRepository.findById(1)).thenReturn(optional);
		Optional<Products> optional2 = productService.getById(1);
		assertEquals(optional, optional2);
	}

	@Test
	void getAllProductTest() {
		Products products = new Products(1, "mobile", "Apple", 100000);
		Products products1 = new Products(1, "Laptop", "HP", 100000);
		List<Products> list = Arrays.asList(products, products1);
		when(productRepository.findAll()).thenReturn(list);
		List<Products> list2 = productService.getAll();
		assertEquals(list, list2);

	}

	@Test
	void deleteProduct() {
		int productId = 1;
		productService.deleteById(productId);
		verify(productRepository).deleteById(productId);
		assertEquals(productRepository.findById(productId), Optional.empty());
	}

}
