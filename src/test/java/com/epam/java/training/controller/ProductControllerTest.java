package com.epam.java.training.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.epam.java.training.AbstractControllerTest;
import com.epam.java.training.controller.ProductController;
import com.epam.java.training.entity.Product;
import com.epam.java.training.service.IProductService;

@Transactional
public class ProductControllerTest extends AbstractControllerTest {

	@Mock
	IProductService iProductService;

	@InjectMocks
	ProductController productController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		setUp(productController);
	}

	@Test
	public void getProductById_ShouldResturnFoundProduct() throws Exception {

		Integer id = new Integer(1);
		Product entity = getEntityStubData();

		String uri = "/user/product/{id}";

		when(iProductService.getProductById(id)).thenReturn(entity);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();

		verify(iProductService, times(1)).getProductById(id);

		assertEquals("Failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void getAllProducts_ShouldReturnAllFounfProducts() throws Exception {
		Collection<Product> list = getEntityListStubData();

		String uri = "/user/products";

		when(iProductService.getAllProducts()).thenReturn((List<Product>) list);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		verify(iProductService, times(1)).getAllProducts();

		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

	}

	@Test
	public void getProduct_GetProductNotFound() throws Exception {

		Integer id = new Integer(1);
		//Product entity = getEntityStubData();

		String uri = "/user/product/{id}";

		when(iProductService.getProductById(id)).thenReturn(null);

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();

		verify(iProductService, times(1)).getProductById(id);

		assertEquals("Failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() == 0);
	}

	@Test
	public void addProduct_ShouldCreateNewProduct() throws Exception {

		String exampleCourseJson = "{\"id\":1,\"product\":\"SONY SMART TV\",\"quantity\":20, \"price\":20000}";

		//Product entity = getEntityStubData();
		// studentService.addCourse to respond back with mockCourse
		Mockito.when(iProductService.addProduct(Mockito.any(Product.class))).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/product").accept(MediaType.APPLICATION_JSON)
				.content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/product/1", response.getHeader(HttpHeaders.LOCATION));

	}

	@Test
	public void updateProduct_ShouldUpdateProduct() throws Exception {

		Product entity = getEntityStubData();
		entity.setProduct(entity.getProduct() + " test");
		Long id = new Long(1);

		// String exampleCourseJson = "{\"productId\":1,\"title\":\"Java Java
		// Concurrency\",\"category\":\"Java\"}";

		doNothing().when(iProductService).updateProduct(Mockito.any(Product.class));

		String uri = "/user/product";
		String inputJson = super.mapToJson(entity);

		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		System.out.println("content::" + content);

		verify(iProductService, times(1)).updateProduct(Mockito.any(Product.class));

		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Product updatedEntity = super.mapFromJson(content, Product.class);

		Assert.assertNotNull("failure - expected entity not null", updatedEntity);
		Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(),
				updatedEntity.getId());
		Assert.assertEquals("failure - expected text attribute match", entity.getId(), updatedEntity.getQuantity(), updatedEntity.getPrice());

	}

	@Test
	public void deleteProduct_ShouldDeleteProduct() throws Exception {

		Product entity = getEntityStubData();

		doNothing().when(iProductService).deleteProduct(entity.getId());

		Integer id = new Integer(1);

		String uri = "/user/product/{id}";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		System.out.println("content::" + content);
		verify(iProductService, times(1)).deleteProduct(entity.getId());

		Assert.assertEquals("failure - expected HTTP status 204", 204, status);
		Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

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
