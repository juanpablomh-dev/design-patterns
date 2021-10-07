package com.jpmh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.sql.DataSource;

public class DataBaseProductDao implements ProductDao {

	private final DataSource dataSource;
	private static final String TABLE_PRODUCTS = "PRODUCTS";
	
	public DataBaseProductDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Stream<Product> getAll() throws Exception {
		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TABLE_PRODUCTS);
			ResultSet resultSet = statement.executeQuery();
			return StreamSupport
					.stream(new Spliterators.AbstractSpliterator<Product>(Long.MAX_VALUE, Spliterator.ORDERED) {
						@Override
						public boolean tryAdvance(Consumer<? super Product> action) {
							try {
								if (!resultSet.next()) {
									return false;
								}
								action.accept(createProduct(resultSet));
								return true;
							} catch (SQLException e) {
								throw new RuntimeException(e);
							}
						}
					}, false).onClose(() -> close(connection, statement, resultSet));
		} catch (SQLException e) {
			throw new CustomException(e.getMessage(), e);
		}
	}

	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	private void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
		try {
		    resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
  			System.out.println("Exception thrown " + e.getMessage());
		}
	}

	private Product createProduct(ResultSet resultSet) throws SQLException {
		return new Product(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getDouble("PRICE"));
	}

	@Override
	public Optional<Product> getById(int id) throws Exception {
		ResultSet resultSet = null;
		try {
			Connection connection = getConnection();
		    PreparedStatement statement = 
		    		connection.prepareStatement("SELECT * FROM " + TABLE_PRODUCTS + " WHERE ID = ?");

			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(createProduct(resultSet));
			} else {
				return Optional.empty();
			}
		} catch (SQLException ex) {
			throw new CustomException(ex.getMessage(), ex);
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
	}

	@Override
	public boolean add(Product Product) throws Exception {
		if (getById(Product.getId()).isPresent()) {
			return false;
		}

		try {
			Connection connection = getConnection();
		    PreparedStatement statement = connection.prepareStatement("INSERT INTO " + TABLE_PRODUCTS + " VALUES (?,?,?)");
			statement.setInt(1, Product.getId());
			statement.setString(2, Product.getName());
			statement.setDouble(3, Product.getPrice());
			statement.execute();
			return true;
		} catch (SQLException ex) {
			throw new CustomException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean update(Product Product) throws Exception {
		try {
			Connection connection = getConnection();
		    PreparedStatement statement = 
		    		connection.prepareStatement("UPDATE " + TABLE_PRODUCTS + " SET NAME = ?, PRICE = ? WHERE ID = ?");
			statement.setString(1, Product.getName());
			statement.setDouble(2, Product.getPrice());
			statement.setInt(3, Product.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException ex) {
			throw new CustomException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean delete(Product Product) throws Exception {
		try {
			Connection connection = getConnection();
		    PreparedStatement statement = connection.prepareStatement("DELETE FROM " + TABLE_PRODUCTS + " WHERE ID = ?");
			statement.setInt(1, Product.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException ex) {
			throw new CustomException(ex.getMessage(), ex);
		}
	}
}
