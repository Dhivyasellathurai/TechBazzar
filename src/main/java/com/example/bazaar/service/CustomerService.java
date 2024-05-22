package com.example.bazaar.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bazaar.dto.CustomerDto;
import com.example.bazaar.entity.Customer;
import com.example.bazaar.entity.User;
import com.example.bazaar.repository.CustomerRepository;
import com.example.bazaar.repository.UserRepository;
import com.example.bazaar.util.PasswordUtil;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;

	public void createCustomer(CustomerDto customerDto) {

		String enpassword = PasswordUtil.getEncryptedPassword(customerDto.getPassword());
		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		customer.setPassword(enpassword);
		customer.setPhoneNo(customerDto.getPhoneNo());
		customerRepository.saveAndFlush(customer);
		User user = new User();
		Optional<User> obj = userRepository.findByCustomerId(customerDto.getId());
		if (!obj.isPresent()) {
			user.setCustomerId(customer.getId());
			user.setEmail(customerDto.getEmail());
			user.setUserName(customerDto.getName());
			user.setPassword(enpassword);
			userRepository.save(user);
		}
	}

	public CustomerDto getById(Integer id) {

		Optional<Customer> obj = customerRepository.findById(id);
		if (obj.isPresent()) {
			Customer obj2 = obj.get();
			CustomerDto obj3 = new CustomerDto();
			obj3.setId(obj2.getId());
			obj3.setName(obj2.getName());
			obj3.setEmail(obj2.getEmail());
			obj3.setPhoneNo(obj2.getPhoneNo());
			obj3.setPassword(obj2.getPassword());
			return obj3;
		} else {
			return null;
		}
	}

	public List<CustomerDto> getAll() {
		List<Customer> customerList = customerRepository.findAll();
		List<CustomerDto> customerDtoList = customerList.stream().map(customer -> {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setId(customer.getId());
			customerDto.setName(customer.getName());
			customerDto.setEmail(customer.getEmail());
			customerDto.setPhoneNo(customer.getPhoneNo());
			customerDto.setPassword(customer.getPassword());
			return customerDto;
		}).collect(Collectors.toList());
		return customerDtoList;
	}

	public void deleteById(Integer id) {
		customerRepository.deleteById(id);
	}
}