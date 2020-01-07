package io.repository;

import io.entity.Product;

import java.util.Map;
import java.util.Optional;

public interface ProductRepository {

    Map<Product, Integer> findAllProducts();

    Product save(Product product, int quantity);

    Map<Product, Integer> saveAll(Map<Product, Integer> products);

    Map<Product, Integer> findAllAvailableProducts();

    Optional<Map.Entry<Product, Integer>> findById(int productId);

    int quantityOf(Product product);
}
