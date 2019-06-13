package com.epam.java.training.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.java.training.entity.Product;
import com.epam.java.training.repository.ProductRepository;
import com.epam.java.training.vo.Review;

@Service
public class ProductService implements IProductService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductRepository<Product> productRepository;

	//@Autowired
	//RestTemplate restTemplate;
	
	@Autowired
	ProductReviewService productReviewService;
	
	@Override
	@Transactional
	public Product getProductById(int id) {
		Product obj = productRepository.findById(id);
		List<Review> productReviews = productReviewService.getProductReviews(obj.getId());
		if (obj != null) {
			obj.setReviews(productReviews);
		}
		return obj;
	}	
	@Transactional
	public List<Product> getAllProducts(){
		List<Product> allProducta = (List<Product>)productRepository.findAll();
		return productReviewService.getAllProductsReviews(allProducta);
	}
	@Transactional
	public boolean addProduct(Product product) {
		if (productRepository.findByName(product.getName())) {
			return false;
		} else {
			productRepository.save(product);
			return true;
		}
	}
	@Transactional
	public boolean updateProduct(Product product) {
		boolean isUpdated = false;
		if(product != null){
			Product productToUpdate = getProductById(product.getId());
			if(productToUpdate != null){
				productRepository.save(product);
				isUpdated = true;
			}
		}
		return isUpdated;
	}
	@Transactional
	public boolean deleteProduct(Integer id) {
		boolean isDeleted = false;
		Product productToDelete = getProductById(id);
		if(productToDelete != null){
			Product product = new Product();
			product.setId(id);
			productRepository.delete(product);
			isDeleted = true;
		}
		return isDeleted;
	}
	@Transactional
	public void addProductReview(Review review,Integer id) {
		productReviewService.addProductReview(id, review);
	}
	@Transactional
	public void updateProductReview(Review review, int productId, int reviewId) {
		productReviewService.updateProductReview(review, productId, reviewId);
	}
	@Transactional
	public void deleteProductReview(int productId, int reviewId) {
		productReviewService.deleteProductReview(productId, reviewId);
	}
	
} 