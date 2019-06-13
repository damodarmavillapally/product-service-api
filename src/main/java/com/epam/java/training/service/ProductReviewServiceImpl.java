package com.epam.java.training.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
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
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API_KEY", "12345");
		//RestTemplate restTemplate = new RestTemplate();
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
		restTemplate.postForLocation(url, requestEntity, productId);
	}
	
	public void updateProductReview(Review review, int productId,
			int reviewId){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API_KEY", "12345");
		RestTemplate restTemplate = new RestTemplate();
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews/{reviewId}";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
		restTemplate.put(url, requestEntity, productId, reviewId);
	}
	
	public void deleteProductReview(int productId, int reviewId){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API_KEY", "12345");
		//RestTemplate restTemplate = new RestTemplate();
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews/{reviewId}";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, productId, reviewId);
	}

}
