package com.is1g6.backend.controller;

import com.is1g6.backend.service.ValidatorService;
import com.is1g6.backend.validations.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/validators")
public class ValidatorController {

    private final ValidatorService service;

    @Autowired
    public ValidatorController(ValidatorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Validator>> getAllValidators() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Validator> createValidator(@RequestBody Validator validator) {
        return ResponseEntity.ok(service.create(validator));
    }

    @DeleteMapping("/{validatorId}")
    public ResponseEntity<Void> deleteValidator(@PathVariable Long validatorId) {
        service.delete(validatorId);
        return ResponseEntity.ok().build();
    }
}
