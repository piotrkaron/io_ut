package io.repository;

import io.entity.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ProductRepositoryImpl implements ProductRepository {

    INSTANCE;

    private int idCounter = 0;

    private Map<Product, Integer> products = new HashMap<>();

    @Override
    public Map<Product, Integer> findAllProducts() {
        return products;
    }

    @Override
    public Product save(Product product, int quantity) {
        product.setId(idCounter++);
        products.put(product, quantity);
        return product;
    }

    @Override
    public Map<Product, Integer> saveAll(Map<Product, Integer> products) {
        products.forEach((product, quantity) -> product.setId(idCounter++));
        this.products.putAll(products);
        return products;
    }

    @Override
    public Map<Product, Integer> findAllAvailableProducts() {
        return products.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Optional<Map.Entry<Product, Integer>> findById(int productId) {
        return products.entrySet().stream()
                .filter(e -> e.getKey().getId() == productId)
                .findAny();
    }

    @Override
    public int quantityOf(Product product) {
        return products.entrySet().stream()
                .filter(entry -> entry.getKey().equals(product))
                .findAny()
                .map(Map.Entry::getValue)
                .orElse(0);
    }
}
