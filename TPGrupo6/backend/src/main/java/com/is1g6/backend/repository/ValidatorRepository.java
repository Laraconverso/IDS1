package com.is1g6.backend.repository;

import com.is1g6.backend.validations.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorRepository extends JpaRepository<Validator, Long> {
}
