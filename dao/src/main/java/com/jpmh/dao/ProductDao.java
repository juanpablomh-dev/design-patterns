package com.jpmh.dao;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductDao {

	/**
	 * Get all Products.
	 * 
	 * @return Stream<Product>
	 * @throws Exception if any error occurs.
	 */
	Stream<Product> getAll() throws Exception;

	/**
	 * Get Product as Optional by id.
	 * 
	 * @param id
	 * @return Optional<Product>
	 * @throws Exception if any error occurs.
	 */
	Optional<Product> getById(int id) throws Exception;

	/**
	 * Add a Product.
	 * 
	 * @param Product
	 * @return true if Product is successfully added, false if Product already
	 *         exists.
	 * @throws Exception if any error occurs.
	 */
	boolean add(Product Product) throws Exception;

	/**
	 * Update a Product.
	 * 
	 * @param Product
	 * @return true if Product exists and is successfully updated, false otherwise.
	 * @throws Exception if any error occurs.
	 */
	boolean update(Product Product) throws Exception;

	/**
	 * Delete a Product.
	 * 
	 * @param Product
	 * @return true if Product exists and is successfully deleted, false otherwise.
	 * @throws Exception if any error occurs.
	 */
	boolean delete(Product Product) throws Exception;
}
