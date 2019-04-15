package com.epam.java.training.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.java.training.entity.Product;
import com.epam.java.training.vo.Review;
public interface IProductService {
     List<Product> getAllProducts();
     Product getProductById(int ProductId);
     boolean addProduct(Product product);
     void updateProduct(Product product);
     void deleteProduct(Integer id);
     ResponseEntity<Void> addProductReview(Review review,Integer id,
 			UriComponentsBuilder builder);
     ResponseEntity<Void> updateProductReview(Review review, int productId,
 			int reviewId, UriComponentsBuilder builder);
     ResponseEntity<Void> deleteProductReview(int productId,
 			int reviewId, UriComponentsBuilder builder);
} 