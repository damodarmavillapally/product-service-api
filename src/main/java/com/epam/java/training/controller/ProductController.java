package com.epam.java.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.java.training.entity.Product;
import com.epam.java.training.service.IProductService;
@Controller
@RequestMapping("user")
public class ProductController extends BaseController{
	@Autowired
	private IProductService productService;
	@GetMapping("product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) {
		Product product = productService.getProductById(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	@GetMapping("products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> list = productService.getAllProducts();
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	@PostMapping("product")
	public ResponseEntity<Void> addProduct(@RequestBody Product product, UriComponentsBuilder builder) {
                boolean flag = productService.addProduct(product);
                if (flag == false) {
        	    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/product/{id}").buildAndExpand(product.getId()).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	@PutMapping("product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		productService.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	@DeleteMapping("product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) {
		productService.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
} 