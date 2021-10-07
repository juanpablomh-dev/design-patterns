package com.jpmh.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MemoryProductDaoTest {

	private MemoryProductDao dao;
	private static final Product PRODUCT = new Product(1, "Notebook HP", 2000.0);

	@BeforeEach
	void setUp() {
		dao = new MemoryProductDao();
		assertTrue(dao.add(PRODUCT));
	}

	/**
	 * Represents the scenario when the DAO operations are being performed on a non
	 * existent Product.
	 */
	@Nested
	class NonExistingProduct {

		@Test
		void addingShouldResultInSuccess() throws Exception {
			try (Stream<Product> allProducts = dao.getAll()) {
				assumeTrue(allProducts.count() == 1);
			}

			final Product nonExistingProduct = new Product(2, "Pendrive", 12.5);
			Boolean result = dao.add(nonExistingProduct);
			assertTrue(result);

			assertProductCountIs(2);
			assertEquals(nonExistingProduct, dao.getById(nonExistingProduct.getId()).get());
		}

		@Test
		void deletionShouldBeFailureAndNotAffectExistingProducts() throws Exception {
			final Product nonExistingProduct = new Product(2, "Pendrive", 999.9);
			Boolean result = dao.delete(nonExistingProduct);

			assertFalse(result);
			assertProductCountIs(1);
		}

		@Test
		void updationShouldBeFailureAndNotAffectExistingProducts() throws Exception {
			final int nonExistingId = getNonExistingProductId();
			final String newName = "Mouse";
			final Double newPrice = 33.0;
			final Product product = new Product(nonExistingId, newName, newPrice);
			Boolean result = dao.update(product);

			assertFalse(result);
			assertFalse(dao.getById(nonExistingId).isPresent());
		}

		@Test
		void retrieveShouldReturnNoProduct() throws Exception {
			assertFalse(dao.getById(getNonExistingProductId()).isPresent());
		}
	}

	/**
	 * Represents the scenario when the DAO operations are being performed on an
	 * already existing Product.
	 */
	@Nested
	class ExistingProduct {

		@Test
		void addingShouldResultInFailureAndNotAffectExistingProducts() throws Exception {
			Boolean result = dao.add(PRODUCT);

			assertFalse(result);
			assertProductCountIs(1);
			assertEquals(PRODUCT, dao.getById(PRODUCT.getId()).get());
		}

		@Test
		void deletionShouldBeSuccessAndProductShouldBeNonAccessible() throws Exception {
			Boolean result = dao.delete(PRODUCT);

			assertTrue(result);
			assertProductCountIs(0);
			assertFalse(dao.getById(PRODUCT.getId()).isPresent());
		}

		@Test
		void updationShouldBeSuccessAndAccessingTheSameProductShouldReturnUpdatedInformation() throws Exception {
			final String newName = "Keyboard";
			final Double newPrice = 66.5;
			final Product product = new Product(PRODUCT.getId(), newName, newPrice);
			Boolean result = dao.update(product);

			assertTrue(result);

			final Product prod = dao.getById(PRODUCT.getId()).get();
			assertEquals(newName, prod.getName());
			assertEquals(newPrice, prod.getPrice());
		}

		@Test
		void retriveShouldReturnTheProduct() {
			Optional<Product> optionalProduct = dao.getById(PRODUCT.getId());

			assertTrue(optionalProduct.isPresent());
			assertEquals(PRODUCT, optionalProduct.get());
		}
	}

	/**
	 * An arbitrary number which does not correspond to an active Product id.
	 *
	 * @return an int of a Product id which doesn't exist
	 */
	private int getNonExistingProductId() {
		return 100;
	}

	private void assertProductCountIs(int count) throws Exception {
		try (Stream<Product> allProducts = dao.getAll()) {
			assertEquals(count, allProducts.count());
		}
	}
}
