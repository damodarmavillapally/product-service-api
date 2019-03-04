package com.epam.java.training.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.java.training.dao.ProductDAO;
import com.epam.java.training.entity.Product;

public class ProductDAOTest {

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private ProductDAO productDAO;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test 
	public void getProductById_ShouldReturnProductForProductId() {
		Integer id = new Integer(1);
		Product entity = getEntityStubData();
		when(entityManager.find(Product.class, entity.getId())).thenReturn(entity);
		Product product = productDAO.getProductById(id);
		assertEquals("Failure - Product Ids should be same", product.getId(), entity.getId());
	}

	@Test
	public void getAllProducts_ShouldGetAllProducts() {
		Collection<Product> list = getEntityListStubData();
		String hql = "FROM Product as prod ORDER BY prod.id";
		Query mockedQuery = mock(Query.class);
		when(mockedQuery.getResultList()).thenReturn((List<Product>) list);
		when(entityManager.createQuery(hql)).thenReturn(mockedQuery);
		List<Product> products = productDAO.getAllProducts();
		assertEquals("Failure - Product size should not be less zero", list.size(), products.size());
	}

	@Test
	public void addProduct_ShouldAddNewProduct() {
		Product entity = getEntityStubData();
		doNothing().when(entityManager).persist(Mockito.any(Product.class));
		productDAO.addProduct(entity);
		verify(entityManager, times(1)).persist(entity);
	}

	@Test
	public void deleteProduct_ShouldDeleteProduct() throws Exception {
		Integer id = new Integer(1);
		Product entity = getEntityStubData();
		when(entityManager.find(Product.class, entity.getId())).thenReturn(entity);
		doNothing().when(entityManager).remove(entity.getId());
		productDAO.deleteProduct(entity.getId());
		verify(entityManager, times(1)).remove(entity);

	}

	@Test
	public void updateProduct_ShouldUpdxateProduct() {
		Product entity = getEntityStubData();
		entity.setProduct(entity.getProduct() + " test");
		Long id = new Long(1);
		when(entityManager.find(Product.class, entity.getId())).thenReturn(entity);
		doNothing().when(entityManager).flush();
		productDAO.updateProduct(entity);
		verify(entityManager, times(1)).flush();

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
