package com.epam.java.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.java.training.entity.Product;

public interface ProductRepository<P> extends CrudRepository<Product, Long> {

	Product findById(int productId);

	boolean findByProduct(String product);
}
