package com.epam.java.training.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.java.training.entity.Product;
import com.epam.java.training.repository.ProductRepository;

public class ProductDAOTest {

	@Mock
	private ProductRepository<Product> productRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test 
	public void getProductById_ShouldReturnProductForProductId() {
		Integer id = new Integer(1);
		Product entity = getEntityStubData();
		when(productRepository.findById(id)).thenReturn(entity);
		assertEquals("Failure - Product Ids should be same", id.intValue(), entity.getId());
	}

	@Test
	public void getAllProducts_ShouldGetAllProducts() {
		Collection<Product> list = getEntityListStubData();
		String hql = "FROM Product as prod ORDER BY prod.id";
		Query mockedQuery = mock(Query.class);
		when(productRepository.findAll()).thenReturn((List<Product>) list);
		List<Product> products = (List<Product>)productRepository.findAll();
		assertEquals("Failure - Product size should not be less zero", list.size(), products.size());
	}

	@Test
	public void addProduct_ShouldAddNewProduct() {
		Product entity = getEntityStubData();
		productRepository.save(entity);
		verify(productRepository, times(1)).save(entity);
	}

	@Test
	public void deleteProduct_ShouldDeleteProduct() throws Exception {
		Integer id = new Integer(1);
		Product entity = getEntityStubData();
		productRepository.delete(entity);
		verify(productRepository, times(1)).delete(entity);

	}

	@Test
	public void updateProduct_ShouldUpdxateProduct() {
		Product entity = getEntityStubData();
		entity.setProduct(entity.getProduct() + " test");
		Long id = new Long(1);
		productRepository.save(entity);
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
