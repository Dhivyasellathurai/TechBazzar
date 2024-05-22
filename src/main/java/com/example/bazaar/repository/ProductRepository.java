package com.example.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bazaar.entity.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer>{

}
