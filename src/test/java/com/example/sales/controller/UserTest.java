package com.example.sales.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.example.bazaar.entity.User;
import com.example.bazaar.repository.UserRepository;
import com.example.bazaar.service.UserService;

import lombok.var;

@ExtendWith(MockitoExtension.class)
public class UserTest {

	@Mock
	private UserRepository userRepo;

	@InjectMocks
	private UserService userService;

	@Test
	void getAllUser() {
		User user = new User(1, "Dhivya", "dhivya@gmail.com","dhivya","dhivya123");
		
		User user1 = new User(2, "Banu", "banu@gmail.com","banu","banu357");
		List<User> mockUsers = Arrays.asList(user, user1);

		when(userRepo.findAll()).thenReturn(mockUsers);
		var userList = userService.getAll();

		assertThat(userList).isNotNull();
		assertThat(userList.size()).isEqualTo(2);

	}

	@Test
	void testGetUserByIdNotFound() {

		when(userRepo.findById(2)).thenReturn(Optional.empty());
		Optional<User> result = userService.getById(2);
		assertFalse(result.isPresent());
	}
	
	@Test
	void getByIdTest() {
		
		User user = new User(1, "Dhivya", "dhivya@gmail.com","dhivya","dhivya123");
		Optional<User> user1 = Optional.of(user);
		
		when (userRepo.findById(1)).thenReturn(user1);
		
		Optional<User> user2 = userService.getById(1);
		
		assertTrue(user2.isPresent());
		assertEquals(user2, user);
	}
	
	@Test
	void userSaveTest() {
		
		User user = new User(1, "Dhivya", "dhivya@gmail.com","dhivya","dhivya123");
		
		when(userRepo.save(user)).thenReturn(user);
        userService.create(user);
        verify(userRepo).save(user);
	}

}
