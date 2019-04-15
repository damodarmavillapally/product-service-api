package com.epam.java.training.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.java.training.entity.Product;
import com.epam.java.training.repository.ProductRepository;
import com.epam.java.training.vo.Review;

@Service
public class ProductService implements IProductService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductRepository<Product> productRepository;
	
	
	RestTemplate restTemplate = new RestTemplate();
	
	//@Value("${product.review-service.url}")
	private String REVIEW_SERVICE_NAME = "http://review-service-api";
	
	@Override
	@Transactional
	public Product getProductById(int id) {
		Product obj = productRepository.findById(id);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//RestTemplate restTemplate = new RestTemplate();
		headers.set("API_KEY", "12345");
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Review[]> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Review[].class, id);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Review> productReviews = new ArrayList<Review>();
		if (responseEntity != null) {
			Review[] reviews = responseEntity.getBody();
			logger.info("Review response received...." + reviews);

			for (Review review : reviews) {
				logger.info("Review Description:" + review.getDescription() + ", Rating:" + review.getRating());
				productReviews.add(review);
			}
		}
		if (obj != null) {
			obj.setReviews(productReviews);
		}
		
		return obj;
	}	
	@Override
	@Transactional
	public List<Product> getAllProducts(){
		List<Product> allProducta = (List<Product>)productRepository.findAll();
		
		List<Product> allProductReviews = new ArrayList<Product>();
		Product product = null;
		if (allProducta != null && allProducta.size() > 0) {
			Iterator<Product> iteratorProduct = allProducta.iterator();
			while (iteratorProduct.hasNext()) {
				product = (Product) iteratorProduct.next();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				//RestTemplate restTemplate = new RestTemplate();
				headers.set("API_KEY", "12345");
				String url = REVIEW_SERVICE_NAME + "/{productId}/reviews";
				HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
				ResponseEntity<Review[]> responseEntity = null;
				try {
					responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Review[].class,
							product.getId());
				} catch (RestClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Review> productReviews = new ArrayList<Review>();
				if (responseEntity != null) {
					Review[] reviews = responseEntity.getBody();
					logger.info("Review response received...." + reviews);

					for (Review review : reviews) {
						logger.info("Review Description:" + review.getDescription() + ", Rating:" + review.getRating());
						productReviews.add(review);
					}
				}
				if (product != null) {
					product.setReviews(productReviews);
				}
				allProductReviews.add(product);
			}

		}
		return allProductReviews;
	}
	@Override
	@Transactional
	public boolean addProduct(Product product) {
		if (productRepository.findByProduct(product.getProduct())) {
			return false;
		} else {
			productRepository.save(product);
			return true;
		}
	}
	@Override
	@Transactional
	public void updateProduct(Product product) {
		productRepository.save(product);
	}
	@Override
	@Transactional
	public void deleteProduct(Integer id) {
		Product product = new Product();
		product.setId(id);
		productRepository.delete(product);
	}
	
	public ResponseEntity<Void> addProductReview(Review review,Integer id,
			UriComponentsBuilder builder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API_KEY", "12345");
		//RestTemplate restTemplate = new RestTemplate();
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity, id);
		headers.setLocation(builder.path("/product/{id}/reviews").buildAndExpand(id).toUri());
		logger.info("Path::" + uri.getPath());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Void> updateProductReview(Review review, int productId,
			int reviewId, UriComponentsBuilder builder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API_KEY", "12345");
		RestTemplate restTemplate = new RestTemplate();
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews/{reviewId}";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(review, headers);
		restTemplate.put(url, requestEntity, productId, reviewId);
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	public ResponseEntity<Void> deleteProductReview(int productId,
			int reviewId, UriComponentsBuilder builder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("API_KEY", "12345");
		//RestTemplate restTemplate = new RestTemplate();
		String url = REVIEW_SERVICE_NAME + "/{productId}/reviews/{reviewId}";
		HttpEntity<Review> requestEntity = new HttpEntity<Review>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, productId, reviewId);
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
} 