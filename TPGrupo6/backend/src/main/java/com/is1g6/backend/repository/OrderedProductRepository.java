package com.is1g6.backend.repository;

import com.is1g6.backend.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
