package com.epam.java.training.client;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.epam.java.training.entity.Product;
public class RestClientUtil {
    public void getProductByIdDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	String url = "http://localhost:8080/user/product/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Product> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Product.class, 1);
        Product product = responseEntity.getBody();
        System.out.println("Id:"+product.getId()+", Product:"+product.getProduct()
                 +", Quantity:"+product.getQuantity() +"Price: "+product.getPrice());      
    }
    public void getAllProductsDemo() {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	String url = "http://localhost:8080/user/products";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Product[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Product[].class);
        Product[] products = responseEntity.getBody();
        for(Product product : products) {
        	System.out.println("Id:"+product.getId()+", Product:"+product.getProduct()
            +", Quantity:"+product.getQuantity() +"Price: "+product.getPrice());   
        }
    }
    public void addProductDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	String url = "http://localhost:8080/user/product";
	Product prod = new Product();
	prod.setProduct("SONY Smart TV");
	prod.setQuantity(10);
	prod.setPrice(150000);
        HttpEntity<Product> requestEntity = new HttpEntity<Product>(prod, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());    	
    }
    public void updateProductDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/product";
	Product prod = new Product();
	prod.setId(1);
	prod.setProduct("Update:SONY Smart TV");
	prod.setQuantity(20);
	prod.setPrice(100000);
        HttpEntity<Product> requestEntity = new HttpEntity<Product>(prod, headers);
        restTemplate.put(url, requestEntity);
    }
    public void deleteProductDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	String url = "http://localhost:8080/user/product/{id}";
        HttpEntity<Product> requestEntity = new HttpEntity<Product>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);        
    }
    public static void main(String args[]) {
    	RestClientUtil util = new RestClientUtil();
        //util.getProductByIdDemo();
    	util.getAllProductsDemo();
    	//util.addProductDemo();
    	//util.updateProductDemo();
    	//util.deletProductDemo();
    }    
} 