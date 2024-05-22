package com.example.sales;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.example.bazaar.entity.User;
import com.example.bazaar.repository.UserRepository;
import com.example.bazaar.service.UserService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = User.class)
public class UserTest {

	@Mock
	private UserRepository userRepo;

	@InjectMocks
	private UserService userService;

	@Test
	void getAllUser() {
		User user = new User(1, 1, "dhivya@gmail.com", "Dhivya", "dhivya123");

		User user1 = new User(2, 2, "banu@gmail.com", "Banu", "banu357");
		List<User> mockUsers = Arrays.asList(user, user1);

		when(userRepo.findAll()).thenReturn(mockUsers);
		List<User> userList = userService.getAll();

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

		User user = new User(1, 1, "dhivya@gmail.com", "Dhivya", "e6adae477c60f8bd9d3b5289b57f988cc78cd4cd");
		Optional<User> user1 = Optional.of(user);

		when(userRepo.findById(1)).thenReturn(user1);

		Optional<User> user2 = userService.getById(1);

		assertTrue(user2.isPresent());
		assertEquals(user2, user1);
	}

	@Test
	void userSaveTest() {

		User user = new User(1, 1, "dhivya@gmail.com", "Dhivya", "dhivya123");

		when(userRepo.save(user)).thenReturn(user);
		userService.create(user);
		verify(userRepo).save(user);
	}

	@Test
	void deleteUserTest() {
		Integer userId = 1;

		userService.delete(userId);

		verify(userRepo, times(1)).deleteById(userId);
		assertEquals(userRepo.findById(userId), Optional.empty());

	}

}
