package com.epam.java.training.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.java.training.configuration.ProductReviewClientConfig;
import com.epam.java.training.vo.Review;

@FeignClient(name = "product-service-api",
configuration = ProductReviewClientConfig.class) // 'product-service-api' is the eureka registered name.
public interface ProductReviewClient {
 
  @GetMapping(value = "/{productId}/reviews")
  List<Review> getProductReviews(@PathVariable int productId);
}