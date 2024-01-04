package com.example.sales.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales.entity.User;
import com.example.sales.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void create(User request) {
		userRepository.save(request);
	}

	public Optional<User> getById(UUID id) {
		return userRepository.findById(id);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public void update(User request) {
		userRepository.saveAndFlush(request);
	}

	public void delete(UUID id) {
		userRepository.deleteById(id);

	}
}