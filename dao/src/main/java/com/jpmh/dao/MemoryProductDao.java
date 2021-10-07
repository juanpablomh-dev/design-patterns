package com.jpmh.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class MemoryProductDao implements ProductDao {

	private final Map<Integer, Product> idToProduct = new HashMap<>();

	@Override
	public Stream<Product> getAll() {
		return idToProduct.values().stream();
	}

	@Override
	public Optional<Product> getById(final int id) {
		return Optional.ofNullable(idToProduct.get(id));
	}

	@Override
	public boolean add(final Product Product) {
		if (getById(Product.getId()).isPresent()) {
			return false;
		}
		idToProduct.put(Product.getId(), Product);
		return true;
	}

	@Override
	public boolean update(final Product Product) {
		return idToProduct.replace(Product.getId(), Product) != null;
	}

	@Override
	public boolean delete(final Product Product) {
		return idToProduct.remove(Product.getId()) != null;
	}
}
