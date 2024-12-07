package com.is1g6.backend.repository;

import com.is1g6.backend.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findByTypeAndItsValue(String type, String aValue);

}
