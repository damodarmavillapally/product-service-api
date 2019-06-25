package com.epam.java.training.client;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.epam.java.training.entity.Product;
import com.epam.java.training.vo.Review;

public class RestClientUtil {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public void getProductByIdDemo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products/{id}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Product> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Product.class, 2);
		Product product = responseEntity.getBody();
		logger.info("Id........:" + product.getId() + ", Product:" + product.getName() + ", Quantity:"
				+ product.getQuantity() + "Price: " + product.getPrice() + "Reviews: " + product.getReviews().size());
	}

	public void getAllProductsDemo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Product[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Product[].class);
		Product[] products = responseEntity.getBody();
		for (Product product : products) {
			logger.info("Id:" + product.getId() + ", Product:" + product.getName() + ", Quantity:"
					+ product.getQuantity() + "Price: " + product.getPrice() + "product Reviews:"
					+ product.getReviews().size());
		}
	}

	public void addProductDemo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products";
		Product prod = new Product();
		prod.setName("SONY Smart TV Demo2");
		prod.setQuantity(10);
		prod.setPrice(150000);
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(prod, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity);
		logger.info(uri.getPath());
	}

	public void updateProductDemo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products";
		Product prod = new Product();
		prod.setId(1);
		prod.setName("Updated:SONY Smart TV");
		prod.setQuantity(20);
		prod.setPrice(100000);
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(prod, headers);
		restTemplate.put(url, requestEntity);
	}

	public void deleteProductDemo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products/{id}";
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);
	}

	public void addProductReview() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products/{id}/reviews";
		Review review = new Review();
		review.setDescription("Added roduct from Product Service from feign client");
		review.setRating(2);
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity, 2);
		logger.info("Completed adding product review...");
	}

	public void updateProductReview() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products/{id}/reviews/{reviewId}";
		Review review = new Review();
		review.setDescription("Updated product reviewed");
		review.setRating(4);
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
		restTemplate.put(url, requestEntity, 2, 5);
		logger.info("Completed updating product review...");
	}

	public void deleteProductReview() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8090/products/{id}/reviews/{reviewId}";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 2, 5);
	}

	public static void main(String args[]) {
		RestClientUtil util = new RestClientUtil();
		 util.getProductByIdDemo();
		//util.getAllProductsDemo();
		 //util.addProductDemo();
		 //util.updateProductDemo();
		 //util.deleteProductDemo();
		 //util.deleteProductReview();
		//util.addProductReview();
		//util.updateProductReview();
		//util.deleteProductReview();
	}
}