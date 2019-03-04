package com.epam.java.training.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.java.training.entity.Product;
@Transactional
@Repository
public class ProductDAO implements IProductDAO {
	@PersistenceContext	
	private EntityManager entityManager;	
	@Override
	public Product getProductById(int id) {
		return entityManager.find(Product.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProducts() {
		String hql = "FROM Product as prod ORDER BY prod.id";
		return (List<Product>) entityManager.createQuery(hql).getResultList();
	}	
	@Override
	public void addProduct(Product product) {
		entityManager.persist(product);
	}
	@Override
	public void updateProduct(Product product) {
		Product prod = getProductById(product.getId());
		prod.setProduct(product.getProduct());
		prod.setQuantity(product.getQuantity());
		prod.setPrice(product.getPrice());
		entityManager.flush();
	}
	@Override
	public void deleteProduct(int ProductId) {
		entityManager.remove(getProductById(ProductId));
	}
	@Override
	public boolean productExists(String product) {
		String hql = "FROM Product as prod WHERE prod.product = ? ";
		int count = entityManager.createQuery(hql).setParameter(1, product)
		              .getResultList().size();
		return count > 0 ? true : false;
	}
}