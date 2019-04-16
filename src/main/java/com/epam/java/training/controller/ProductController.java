package com.epam.java.training.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.java.training.entity.Product;
import com.epam.java.training.service.IProductService;
import com.epam.java.training.vo.Review;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags={"Product Service API"})
public class ProductController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IProductService productService;
	
	@GetMapping("products/{id}")
	@ApiOperation(value="Product Information", notes="Provides the Product Information for a given productId", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) {
		logger.info("Before Review response received====....");
		Product product = productService.getProductById(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("products")
	@ApiOperation(value="Products Information", notes="Provides all Products Information.", response=ResponseEntity.class)
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> list = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}

	@PostMapping("products")
	@ApiOperation(value="Add the Product Information", notes="Adds the Product Information.", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "product", value = "product", required=true, dataType="Product", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Void> addProduct(@RequestBody Product product, UriComponentsBuilder builder) {
		boolean flag = productService.addProduct(product);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/product/{id}").buildAndExpand(product.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("products")
	@ApiOperation(value="Update Product Information", notes="Updates the Product Information for a given product.", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "product", required=true, dataType="Product", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		productService.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("products/{id}")
	@ApiOperation(value="Delete Product Information", notes="Deletes the Product Information for a given product.", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) {
		productService.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/products/{id}/reviews")
	@ApiOperation(value="Adds Product Review Information", notes="Addes the Product Review Information for a given product.", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "review", value = "Review", required=true, dataType="Review", paramType="body"),
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Void> addProductReview(@RequestBody Review review, @PathVariable("id") Integer id,
			UriComponentsBuilder builder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		productService.addProductReview(review, id);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/products/{id}/reviews/{reviewId}")
	@ApiOperation(value="Updates Product Review Information", notes="Updates the Product Review Information for a given product and review.", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "review", value = "Review", required=true, dataType="Review", paramType="body"),
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "reviewId", value = "Review Id", required=true, dataType="Integer", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Void> updateProductReview(@RequestBody Review review, @PathVariable("id") int productId,
			@PathVariable("reviewId") int reviewId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		productService.updateProductReview(review, productId, reviewId);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@DeleteMapping("/products/{id}/reviews/{reviewId}")
	@ApiOperation(value="Delete Review Information", notes="Deletes the Review Information for a given product.", response=ResponseEntity.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Product Id", required=true, dataType="Integer", paramType="path"),
        @ApiImplicitParam(name = "reviewId", value = "Review Id", required=true, dataType="Integer", paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Bad Request"),
			@ApiResponse(code=402, message="Unauthorised"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=404, message="No Product is found"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public ResponseEntity<Void> deleteProductReview(@PathVariable("id") int productId,
			@PathVariable("reviewId") int reviewId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		productService.deleteProductReview(productId, reviewId);
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}