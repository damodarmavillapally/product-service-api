package com.epam.java.training.client;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.java.training.configuration.ProductReviewClientConfig;
import com.epam.java.training.vo.Review;

@FeignClient(name = "REVIEW-SERVICE-API", configuration = ProductReviewClientConfig.class, fallback =  FallBackProductReviews.class) // 'product-service-api' is the eureka registered name.
public interface ProductReviewClient {
	
	/*
  @RequestMapping(value = "/{productId}/reviews", method = RequestMethod.GET)
  public List<Review> getProductReviews(@PathVariable int productId);
  
  @RequestMapping(value = "/{productId}/reviews", method = RequestMethod.POST)
  public void createReviewProduct(@PathVariable int productId, @RequestBody Review review);
  
  @RequestMapping(value = "/{productId}/reviews/{reviewId}", method = RequestMethod.PUT)
  public Review updateProductReview(@PathVariable int productId, @PathVariable int reviewId, @RequestBody Review review);
  
  @RequestMapping(value = "/{productId}/reviews/{reviewId}", method = RequestMethod.DELETE)
  public void deleteProductReview(@PathVariable int productId, @PathVariable int reviewId);
  */
	
	@GetMapping(value = "/{productId}/reviews")
	public List<Review> getProductReviews(@PathVariable int productId);

	@PostMapping(path = "/{productId}/reviews")
	public void createReviewProduct(@PathVariable int productId, @RequestBody Review review);

	@PutMapping(value = "/{productId}/reviews/{reviewId}")
	public Review updateProductReview(@PathVariable int productId, @PathVariable int reviewId,
			@RequestBody Review review);

	@DeleteMapping(value = "/{productId}/reviews/{reviewId}")
	public void deleteProductReview(@PathVariable int productId, @PathVariable int reviewId);
	
}
  @Component
  class FallBackProductReviews implements ProductReviewClient {

	  @Override
	  public List<Review> getProductReviews(int productId){
	    return Collections.emptyList();
	  }

	@Override
	public void createReviewProduct(int productId, Review review) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Review updateProductReview(int productId, int reviewId, Review review) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductReview(int productId, int reviewId) {
		// TODO Auto-generated method stub
		
	}
	}
