package com.epam.java.training.service;

import java.util.List;

import com.epam.java.training.entity.Product;
import com.epam.java.training.vo.Review;

public interface ProductReviewService {

	 public List<Review> getProductReviews(int productId);
	 public List<Product> getAllProductsReviews(List<Product> allProducta);
	 public void addProductReview(int productId, Review review);
	 public void updateProductReview(Review review, int productId, int reviewId);
	 public void deleteProductReview(int productId, int reviewId);
}
