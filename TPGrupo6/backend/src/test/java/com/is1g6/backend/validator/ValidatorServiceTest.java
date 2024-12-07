package com.is1g6.backend.service;

import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.repository.ValidatorRepository;
import com.is1g6.backend.validations.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidatorServiceTest {

    @Mock
    private ValidatorRepository validatorRepository;

    @InjectMocks
    private ValidatorService validatorService;

    private Validator mockValidator1;
    private Validator mockValidator2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockValidator1 = mock(Validator.class);
        mockValidator2 = mock(Validator.class);
    }

    @Test
    public void testGetAll() {
        List<Validator> mockValidators = Arrays.asList(mockValidator1, mockValidator2);
        when(validatorRepository.findAll()).thenReturn(mockValidators);

        List<Validator> validators = validatorService.getAll();


        assertNotNull(validators);
        assertEquals(2, validators.size());
        verify(validatorRepository, times(1)).findAll();
    }

    @Test
    public void testCreateValidator() {
        when(validatorRepository.save(any(Validator.class))).thenReturn(mockValidator1);

        Validator createdValidator = validatorService.create(mockValidator1);

        assertNotNull(createdValidator);
        verify(validatorRepository, times(1)).save(mockValidator1);
    }

    @Test
    public void testDeleteValidator() {
        Long validatorId = 1L;
        when(validatorRepository.existsById(validatorId)).thenReturn(true);

        validatorService.delete(validatorId);

        verify(validatorRepository, times(1)).deleteById(validatorId);
    }


    @Test
    public void testValidatePedido() {
        Pedido mockPedido = mock(Pedido.class);
        when(validatorRepository.findAll()).thenReturn(Arrays.asList(mockValidator1, mockValidator2));

        validatorService.validate(mockPedido);

        verify(mockValidator1, times(1)).validate(mockPedido);
        verify(mockValidator2, times(1)).validate(mockPedido);
    }

}
