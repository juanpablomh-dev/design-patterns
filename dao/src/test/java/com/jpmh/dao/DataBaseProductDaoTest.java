package com.jpmh.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DataBaseProductDaoTest {

	private DataSource dataSource;
	private DataBaseProductDao dao;
	private final Product existingProduct = new Product(1, "Pendrive", 15.5);

	/**
	 * Creates schema.
	 *
	 * @throws SQLException if there is any error while creating schema.
	 */
	@BeforeEach
	void createSchema() throws SQLException {
		dataSource = SchemaSql.createDataSource();
		SchemaSql.createSchema(dataSource);
	}

	/**
	 * Represents the scenario where DB connectivity is present.
	 */
	@Nested
	public class ConnectionSuccess {

		/**
		 * Setup for connection success scenario.
		 *
		 * @throws Exception if any error occurs.
		 */
		@BeforeEach
		public void setUp() throws Exception {
			final DataSource dataSource = SchemaSql.createDataSource();		
			dao = new DataBaseProductDao(dataSource);
			Boolean result = dao.add(existingProduct);
			assertTrue(result);
		}

		/**
		 * Represents the scenario when DAO operations are being performed on a non
		 * existing product.
		 */
		@Nested
		class NonExistingProducts {

			@Test
			void addingShouldResultInSuccess() throws Exception {
				try (Stream<Product> allProducts = dao.getAll()) {
					assumeTrue(allProducts.count() == 1);
				}

				final Product nonExistingProduct = new Product(2, "Keyboard", 55.5);
				Boolean result = dao.add(nonExistingProduct);
				assertTrue(result);

				assertProductCountIs(2);
				assertEquals(nonExistingProduct.toString(), dao.getById(nonExistingProduct.getId()).get().toString());
			}

			@Test
			void deletionShouldBeFailureAndNotAffectExistingProducts() throws Exception {
				Product nonExistingProduct = new Product(2, "Notebook", 1666.0);
				Boolean result = dao.delete(nonExistingProduct);

				assertFalse(result);
				assertProductCountIs(1);
			}

			@Test
			void updationShouldBeFailureAndNotAffectExistingProducts() throws Exception {
				final int nonExistingId = getNonExistingProductId();
				final String newName = "Mouse";
				final Double newPrice = 16.3;
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
		 * Represents a scenario where DAO operations are being performed on an already
		 * existing product.
		 */
		@Nested
		class ExistingProduct {

			@Test
			void deletionShouldBeSuccessAndProductShouldBeNonAccessible() throws Exception {
				Boolean result = dao.delete(existingProduct);

				assertTrue(result);
				assertProductCountIs(0);
				assertFalse(dao.getById(existingProduct.getId()).isPresent());
			}
			
			@Test
			void addingShouldResultInFailureAndNotAffectExistingProducts() throws Exception {
				Product existingNewProduct = new Product(1, "Notebook", 650.0);
				Boolean result = dao.add(existingNewProduct);

				assertFalse(result);
				assertProductCountIs(1);
				assertEquals(existingProduct.toString(), dao.getById(existingNewProduct.getId()).get().toString());
			}

			@Test
			void updationShouldBeSuccessAndAccessingTheSameProductShouldReturnUpdatedInformation() throws Exception {
				final String newName = "Keyboard";
				final Double newPrice = 18.0;
				final Product product = new Product(existingProduct.getId(), newName, newPrice);
				Boolean result = dao.update(product);

				assertTrue(result);

				final Product prod = dao.getById(existingProduct.getId()).get();
				assertEquals(newName, prod.getName());
				assertEquals(newPrice, prod.getPrice());
			}

		}

	}

	/**
	 * Represents a scenario where DB connectivity is not present due to network
	 * issue, or DB service unavailable.
	 */
	@Nested
	class ConnectivityIssue {

		private static final String EXCEPTION_CAUSE = "Connection not available";

		/**
		 * setup a connection failure scenario.
		 *
		 * @throws SQLException if any error occurs.
		 */
		@BeforeEach
		public void setUp() throws SQLException {
			dao = new DataBaseProductDao(mockedDatasource());
		}

		private DataSource mockedDatasource() throws SQLException {
			DataSource mockedDataSource = mock(DataSource.class);
			Connection mockedConnection = mock(Connection.class);
			SQLException exception = new SQLException(EXCEPTION_CAUSE);
			doThrow(exception).when(mockedConnection).prepareStatement(Mockito.anyString());
			doReturn(mockedConnection).when(mockedDataSource).getConnection();
			return mockedDataSource;
		}

		@Test
		void addingAProductFailsWithExceptionAsFeedbackToClient() {
			assertThrows(Exception.class, () -> {
				dao.add(new Product(2, "LED Monitor", 150.0));
			});
		}

		@Test
		void deletingAProductFailsWithExceptionAsFeedbackToTheClient() {
			assertThrows(Exception.class, () -> {
				dao.delete(existingProduct);
			});
		}

		@Test
		void updatingAProductFailsWithFeedbackToTheClient() {
			final String newName = "4k Monitor";
			final Double newPrice = 200.0;
			assertThrows(Exception.class, () -> {
				dao.update(new Product(existingProduct.getId(), newName, newPrice));
			});
		}

		@Test
		void retrievingAProductByIdFailsWithExceptionAsFeedbackToClient() {
			assertThrows(Exception.class, () -> {
				dao.getById(existingProduct.getId());
			});
		}

		@Test
		void retrievingAllProductsFailsWithExceptionAsFeedbackToClient() {
			assertThrows(Exception.class, () -> {
				dao.getAll();
			});
		}
	}

	/**
	 * Delete product schema for fresh setup per test.
	 *
	 * @throws SQLException if any error occurs.
	 */
	@AfterEach
	void deleteSchema() throws SQLException {
		SchemaSql.deleteSchema(dataSource);
	}

	private void assertProductCountIs(int count) throws Exception {
		try (Stream<Product> allProducts = dao.getAll()) {
			assertEquals(count, allProducts.count());
		}
	}

	private int getNonExistingProductId() {
		return 100;
	}
}