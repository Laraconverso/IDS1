package com.is1g6.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.is1g6.backend.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
