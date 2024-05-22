package com.example.bazaar.dto;

public interface AddToCartInterface {

	Integer getId();

	String getProductName();

	String getCustomerName();

	Integer getQuantity();

	Double getProductPrice();

	Double getTotalAmount();

}