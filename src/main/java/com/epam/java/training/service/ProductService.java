package com.epam.java.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.java.training.entity.Product;
import com.epam.java.training.repository.ProductRepository;

@Service
public class ProductService implements IProductService {
	@Autowired
	private ProductRepository<Product> productRepository;
	
	@Override
	@Transactional
	public Product getProductById(int id) {
		Product obj = productRepository.findById(id);
		return obj;
	}	
	@Override
	@Transactional
	public List<Product> getAllProducts(){
		return (List<Product>)productRepository.findAll();
	}
	@Override
	@Transactional
	public boolean addProduct(Product product) {
		if (productRepository.findByProduct(product.getProduct())) {
			return false;
		} else {
			productRepository.save(product);
			return true;
		}
	}
	@Override
	@Transactional
	public void updateProduct(Product product) {
		productRepository.save(product);
	}
	@Override
	@Transactional
	public void deleteProduct(Integer id) {
		Product product = new Product();
		product.setId(id);
		productRepository.delete(product);
	}
} 