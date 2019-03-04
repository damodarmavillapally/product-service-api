package com.epam.java.training.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.java.training.dao.IProductDAO;
import com.epam.java.training.entity.Product;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductDAO productDAO;
	@Override
	public Product getProductById(int id) {
		Product obj = productDAO.getProductById(id);
		return obj;
	}	
	@Override
	public List<Product> getAllProducts(){
		return productDAO.getAllProducts();
	}
	@Override
	public synchronized boolean addProduct(Product product){
                if (productDAO.productExists(product.getProduct())) {
    	            return false;
                } else {
    	            productDAO.addProduct(product);
    	            return true;
                }
	}
	@Override
	public void updateProduct(Product product) {
		productDAO.updateProduct(product);
	}
	@Override
	public void deleteProduct(int ProductId) {
		productDAO.deleteProduct(ProductId);
	}
} 