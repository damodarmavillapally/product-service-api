package com.epam.java.training.service;

import java.util.List;

import com.epam.java.training.entity.Product;
public interface IProductService {
     List<Product> getAllProducts();
     Product getProductById(int ProductId);
     boolean addProduct(Product product);
     void updateProduct(Product product);
     void deleteProduct(Integer id);
} 