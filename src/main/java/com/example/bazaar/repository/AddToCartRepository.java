package com.example.bazaar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bazaar.dto.AddToCartInterface;
import com.example.bazaar.entity.AddToCart;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCart, Integer> {

	@Query(value = "select ac.id as id, c.name as customerName, p.product_name as productName, ac.product_price as productPrice, "
			+ "ac.total_amount as totalAmount, ac.quantity as quantity \r\n"
			+ "from AddToCart ac inner join Customer c on ac.customer_id=c.id \r\n"
			+ "inner join product p on ac.product_id=p.id \r\n"
			+ "where ac.customer_id=:customerId", nativeQuery = true)
	public AddToCartInterface findByCustomerId(Integer customerId);

	@Query(value = "select * from addtocart ac where ac.product_id =:productId and ac.customer_id =:customerId", nativeQuery = true)
	Optional<AddToCart> findProduct(int productId, int customerId);

}
