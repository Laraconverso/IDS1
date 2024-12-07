package com.is1g6.backend.controller;

import com.is1g6.backend.service.ValidatorService;
import com.is1g6.backend.validations.TypeCombinationValidation;
import com.is1g6.backend.validations.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ValidatorControllerTest {

    @Mock
    private ValidatorService validatorService;

    private ValidatorController validatorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validatorController = new ValidatorController(validatorService);
    }

    @Test
    void testGetAllValidators() {
        Validator validator = new TypeCombinationValidation("type", List.of("liquid", "gaseous"));
        when(validatorService.getAll()).thenReturn(List.of(validator));

        ResponseEntity<List<Validator>> response = validatorController.getAllValidators();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(validatorService, times(1)).getAll();
    }

    @Test
    void testCreateValidator() {
        Validator validator = new TypeCombinationValidation("type", List.of("liquid", "gaseous"));
        when(validatorService.create(any(Validator.class))).thenReturn(validator);

        ResponseEntity<Validator> response = validatorController.createValidator(validator);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("type", ((TypeCombinationValidation) response.getBody()).getAttributeType());
        verify(validatorService, times(1)).create(any(Validator.class));
    }

    @Test
    void testDeleteValidator() {
        Long validatorId = 1L;

        ResponseEntity<Void> response = validatorController.deleteValidator(validatorId);

        assertEquals(200, response.getStatusCodeValue());
        verify(validatorService, times(1)).delete(validatorId);
    }
}
