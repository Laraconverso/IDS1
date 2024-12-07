package com.is1g6.backend.Validation;

import com.is1g6.backend.model.OrderedProduct;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.validations.QuantityValidation;
import com.is1g6.backend.exceptions.ValidationFailedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuantityValidationTest {

    @Test
    public void testValidationSuccess() {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setName("Shirt");
        orderedProduct.setCantidad(3);

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct), null, LocalDateTime.now());

        QuantityValidation validator = new QuantityValidation(5);

        assertDoesNotThrow(() -> validator.validate(pedido));
    }

    @Test
    public void testValidationFail() {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setName("Shirt");
        orderedProduct.setCantidad(6);

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct), null, LocalDateTime.now());

        QuantityValidation validator = new QuantityValidation(5);

        assertThrows(ValidationFailedException.class, () -> validator.validate(pedido));
    }
}
