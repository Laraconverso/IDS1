package com.is1g6.backend.service;

import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.repository.ValidatorRepository;
import com.is1g6.backend.validations.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ValidatorService {

    private final ValidatorRepository repository;

    @Autowired
    public ValidatorService(ValidatorRepository repository) {
        this.repository = repository;
    }

    public List<Validator> getAll() {
        return repository.findAll();
    }

    public Validator create(Validator validator) {
        return repository.save(validator);
    }

    public void delete(Long validatorId) {
        repository.deleteById(validatorId);
    }

    public void validate(Pedido pedido) {
        getAll().forEach(validator -> validator.validate(pedido));
    }
}
