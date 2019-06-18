package com.epam.java.training.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.java.training.configuration.ProductReviewClientConfig;
import com.epam.java.training.vo.Review;

@FeignClient(name = "REVIEW-SERVICE-API", configuration = ProductReviewClientConfig.class) // 'product-service-api' is the eureka registered name.
public interface ProductReviewClient {
	
  @RequestMapping(value = "/{productId}/reviews", method = RequestMethod.GET)
  List<Review> getProductReviews(@PathVariable int productId);
  
  @RequestMapping(value = "/{productId}/reviews", method = RequestMethod.POST)
  void createReviewProduct(@PathVariable int productId, @RequestBody Review review);
  
  @RequestMapping(value = "/{productId}/reviews/{reviewId}", method = RequestMethod.PUT)
  Review updateProductReview(@PathVariable int productId, @PathVariable int reviewId, @RequestBody Review review);
  
  @RequestMapping(value = "/{productId}/reviews/{reviewId}", method = RequestMethod.DELETE)
  void deleteProductReview(@PathVariable int productId, @PathVariable int reviewId);
  
}