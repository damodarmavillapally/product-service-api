package com.epam.java.training.dao;

import java.util.List;

import com.epam.java.training.entity.Product;
public interface IProductDAO {
    List<Product> getAllProducts();
    Product getProductById(int articleId);
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int articleId);
    boolean productExists(String product);
}