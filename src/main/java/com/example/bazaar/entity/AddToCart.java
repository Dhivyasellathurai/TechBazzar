package com.example.bazaar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "addtocart")
public class AddToCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "product_price")
	private double productPrice;

	@Column(name = "total_amount")
	private double totalAmount;

}