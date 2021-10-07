package com.jpmh.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public final class SchemaSql {

	private SchemaSql() {
	}

	private static final String DB_URL = "jdbc:h2:~/dao";

	public static final String CREATE_SCHEMA_SQL_PRODUCTS = "CREATE TABLE IF NOT EXISTS PRODUCTS (ID NUMBER, NAME VARCHAR(100), PRICE DOUBLE PRECISION)";
	public static final String DELETE_SCHEMA_SQL_PRODUCTS = "DROP TABLE PRODUCTS";

	public static DataSource createDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(DB_URL);
		return dataSource;
	}

	public static void createSchema(DataSource dataSource) throws SQLException {
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
			statement.execute(CREATE_SCHEMA_SQL_PRODUCTS);
		}
	}

	public static void deleteSchema(DataSource dataSource) throws SQLException {
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
			statement.execute(DELETE_SCHEMA_SQL_PRODUCTS);
		}
	}
}
