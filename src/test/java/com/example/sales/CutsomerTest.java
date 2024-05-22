package com.example.sales;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
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

import com.example.bazaar.dto.CustomerDto;
import com.example.bazaar.entity.Customer;
import com.example.bazaar.entity.User;
import com.example.bazaar.repository.CustomerRepository;
import com.example.bazaar.repository.UserRepository;
import com.example.bazaar.service.CustomerService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Customer.class)
public class CutsomerTest {

	@Mock
	CustomerRepository customerRepo;

	@InjectMocks
	CustomerService customerService;

	@Mock
	UserRepository userRepo;

	@Test
	void createCustomerTest() {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(1);
		customerDto.setName("Dhivya");
		customerDto.setEmail("dhivya@gmail.com");
		customerDto.setPassword("dhivya123");
		customerDto.setPhoneNo(9080706050l);

		Customer customer = new Customer();
		customer.setId(1);
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		customer.setPassword("e6adae477c60f8bd9d3b5289b57f988cc78cd4cd");
		customer.setPhoneNo(customerDto.getPhoneNo());

		User user = new User(1, 1, "Dhivya", "dhivya@gmail.com", "e6adae477c60f8bd9d3b5289b57f988cc78cd4cd");

		when(customerRepo.saveAndFlush(any(Customer.class))).thenReturn(customer);
		when(userRepo.findByCustomerId(1)).thenReturn(Optional.empty());
		when(userRepo.save(any(User.class))).thenReturn(user);
		customerService.createCustomer(customerDto);

		verify(customerRepo, times(1)).saveAndFlush(argThat(argument -> {
			return customer.getName().equals(argument.getName()) && customer.getEmail().equals(argument.getEmail())
					&& customer.getPassword().equals(argument.getPassword())
					&& customer.getPhoneNo() == (argument.getPhoneNo());
		}));

		verify(userRepo).save(argThat(argument -> {
			return customer.getName().equals(argument.getUserName()) && customer.getEmail().equals(argument.getEmail())
					&& customer.getPassword().equals(argument.getPassword());
		}));
	}

	@Test
	void getByIdTest() {

		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Dhivya");
		customer.setEmail("dhivya@gmail.com");
		customer.setPassword("e6adae477c60f8bd9d3b5289b57f988cc78cd4cd");
		customer.setPhoneNo(9080706050l);

		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		customerDto.setPassword(customer.getPassword());
		customerDto.setPhoneNo(customer.getPhoneNo());

		when(customerRepo.findById(1)).thenReturn(Optional.of(customer));

		CustomerDto customer1 = customerService.getById(1);

		assertEquals(customerDto, customer1);

	}

	@Test
	void getAllCustomerTest() {

		Customer customer = new Customer(1, "Dhivya", "dhivya@gmail.com", 9080706050l,
				"e6adae477c60f8bd9d3b5289b57f988cc78cd4cd");
		Customer customer1 = new Customer(2, "Banu", "banu@gmail.com", 9080706051l,
				"e6adae477c60f8bd9d3b5289b57f988cc78cd4cd");

		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setEmail(customer.getEmail());
		customerDto.setPassword(customer.getPassword());
		customerDto.setPhoneNo(customer.getPhoneNo());

		CustomerDto customerDto1 = new CustomerDto();
		customerDto1.setId(customer1.getId());
		customerDto1.setName(customer1.getName());
		customerDto1.setEmail(customer1.getEmail());
		customerDto1.setPassword(customer1.getPassword());
		customerDto1.setPhoneNo(customer1.getPhoneNo());

		List<CustomerDto> customers = Arrays.asList(customerDto, customerDto1);

		when(customerRepo.findAll()).thenReturn(Arrays.asList(customer, customer1));

		List<CustomerDto> customers2 = customerService.getAll();

		assertEquals(customers, customers2);

		verify(customerRepo, times(1)).findAll();

	}

	@Test
	void deleteCustomerTest() {
		int customerId = 1;
		customerRepo.deleteById(customerId);

		verify(customerRepo, times(1)).deleteById(customerId);
		assertEquals(userRepo.findById(customerId), Optional.empty());
	}

}
