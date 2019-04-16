package com.epam.java.training.service;

import java.util.List;

import com.epam.java.training.entity.Product;
import com.epam.java.training.vo.Review;
public interface IProductService {
     List<Product> getAllProducts();
     Product getProductById(int ProductId);
     boolean addProduct(Product product);
     void updateProduct(Product product);
     void deleteProduct(Integer id);
     void addProductReview(Review review,Integer id);
     void updateProductReview(Review review, int productId,	int reviewId);
     void deleteProductReview(int productId, int reviewId);
} 