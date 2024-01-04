package com.example.sales.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.sales.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	@Query(value="select * from sales where id =:id" , nativeQuery = true)
	Optional<User> findById(UUID id);
}
