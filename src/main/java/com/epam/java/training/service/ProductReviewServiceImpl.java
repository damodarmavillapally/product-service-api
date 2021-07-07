package com.epam.java.training.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.epam.java.training.client.ProductReviewClient;
import com.epam.java.training.entity.Product;
import com.epam.java.training.vo.Review;

@Service
@Transactional
public class ProductReviewServiceImpl implements ProductReviewService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RestTemplate restTemplate;

	private final String REVIEW_SERVICE_NAME = "http://review-service-api";
	
	@Autowired
	ProductReviewClient productReviewClient;
	
	public List<Review> getProductReviews(int productId) {
		List<Review> productReviews = new ArrayList<Review>();
		productReviews = productReviewClient.getProductReviews(productId);
		return productReviews;
	}
	
	public List<Product> getAllProductsReviews(List<Product> allProducta){
		List<Product> allProductReviews = new ArrayList<Product>();
		Product product = null;
		if (allProducta != null && allProducta.size() > 0) {
			Iterator<Product> iteratorProduct = allProducta.iterator();
			while (iteratorProduct.hasNext()) {
				product = (Product) iteratorProduct.next();
				List<Review> reviews = getProductReviews(product.getId());
				if (product != null) {
					product.setReviews(reviews);
				}
				allProductReviews.add(product);
			}
		}
		return allProductReviews;
	}
	
	public void addProductReview(int productId, Review review){
		productReviewClient.createReviewProduct(productId, review);
	}
	
	public void updateProductReview(Review review, int productId,
			int reviewId){
		productReviewClient.updateProductReview(productId, reviewId, review);
	}
	
	public void deleteProductReview(int productId, int reviewId){
		productReviewClient.deleteProductReview(productId, reviewId);
	}

}
