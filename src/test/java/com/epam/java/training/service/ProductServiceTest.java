package com.epam.java.training.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.java.training.entity.Product;
import com.epam.java.training.repository.ProductRepository;
import com.epam.java.training.vo.Review;

public class ProductServiceTest {

	@Mock
	private ProductRepository<Review> productRepository;

	@InjectMocks
	private ProductService productService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductById_ShouldReturnProductForProductId() {
		Integer id = new Integer(1);
		Product entity = getEntityStubData();
		when(productRepository.findById(1)).thenReturn(entity);

		Product product = productService.getProductById(id);
		assertEquals("Failure - Product Ids should be same", product.getId(), entity.getId());

	}

	@Test
	public void getAllProducts_ShouldGetAllProducts() {
		Collection<Product> list = getEntityListStubData();

		when(productRepository.findAll()).thenReturn((List<Product>) list);
		List<Product> products = productService.getAllProducts();
		assertEquals("Failure - Product size should not be less zero", list.size(), products.size());
	}

	@Test
	public void addProduct_ShouldAddNewProduct() {
		Product entity = getEntityStubData();
		when(productRepository.save(entity)).thenReturn(entity);
		boolean isCreated = productService.addProduct(entity);
		assertTrue(isCreated);
	}

	@Test
	public void deleteProduct_ShouldDeleteProduct() throws Exception {

		Product entity = getEntityStubData();

		doNothing().when(productRepository).delete(entity);
		productService.deleteProduct(entity.getId());
		verify(productRepository, times(1)).delete(Mockito.any(Product.class));

	}

	@Test
	public void updateProduct_ShouldUpdxateProduct() {

		Product entity = getEntityStubData();
		entity.setProduct(entity.getProduct() + " test");
		Long id = new Long(1);
		when(productRepository.save(Mockito.any(Product.class))).thenReturn(entity);
		productService.updateProduct(entity);
		verify(productRepository, times(1)).save(entity);

	}

	private Collection<Product> getEntityListStubData() {
		Collection<Product> list = new ArrayList<Product>();
		list.add(getEntityStubData());
		return list;
	}

	private Product getEntityStubData() {
		Product entity = new Product();
		entity.setId(1);
		entity.setProduct("SONY ANDROID");
		entity.setQuantity(10);
		entity.setPrice(50000);
		return entity;
	}

}
