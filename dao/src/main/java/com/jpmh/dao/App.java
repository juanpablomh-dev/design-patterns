package com.jpmh.dao;

import java.util.stream.Stream;
import javax.sql.DataSource;

public class App {
	
    public static void main( String[] args ) throws Exception{
		exampleMemoryProductDao();
		exampleDataBaseProductDao();
    }
	
private static void exampleMemoryProductDao() throws Exception {
		final MemoryProductDao memoryDao = new MemoryProductDao();
		System.out.println("MemoryProductDao ----------------------------------------");
		exampleOperations(memoryDao);
		System.out.println("---------------------------------------------------------");
	}

	private static void exampleDataBaseProductDao() throws Exception {
		final DataSource dataSource = SchemaSql.createDataSource();
		SchemaSql.createSchema(dataSource);
		System.out.println("DataBaseProductDao --------------------------------------");
		exampleOperations(new DataBaseProductDao(dataSource));
		System.out.println("---------------------------------------------------------");
		SchemaSql.deleteSchema(dataSource);
	}

	private static void exampleOperations(final ProductDao productDao) throws Exception {
		System.out.println("--> Example addProducts and getAll(): ");
		addProducts(productDao);
		Stream<Product> productStream = productDao.getAll();
		productStream.forEach(product -> System.out.println(product.toString()));

		System.out.println("--> Example getById(3): ");
		System.out.println(productDao.getById(3));

		System.out.println("--> Example add() and getAll()");
		final Product product = new Product(4, "Notebook", 1000.0);
		productDao.add(product);
		productStream = productDao.getAll();
		productStream.forEach(prod -> System.out.println(prod.toString()));

		System.out.println("--> Example update() and getAll()");
		product.setName("Notebook ASUS");
		product.setPrice(1500.0);
		productDao.update(product);
		productStream = productDao.getAll();
		productStream.forEach(prod -> System.out.println(prod.toString()));

		System.out.println("--> Example delete() and getAll()");
		productDao.delete(product);
		productStream = productDao.getAll();
		productStream.forEach(prod -> System.out.println(prod.toString()));
	}

	private static void addProducts(ProductDao productDao) throws Exception {
		generateSampleProducts().forEach((p) -> {
			try {
				productDao.add(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static Stream<Product> generateSampleProducts() {
		final Product product1 = new Product(1, "Notebook", 1250.0);
		final Product product2 = new Product(2, "Mouse", 50.0);
		final Product product3 = new Product(3, "Keyboard", 65.0);
		return Stream.of(product1, product2, product3);
	}	
}
