package com.is1g6.backend.Validation;

import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.OrderedProduct;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.validations.SumMaxValidation;
import com.is1g6.backend.exceptions.ValidationFailedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SumMaxValidationTest {

    @Test
    public void testValidationSuccess() {
        Attribute attribute1 = new Attribute("weight", "2");
        Attribute attribute2 = new Attribute("weight", "3");

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setName("Product A");
        orderedProduct.setAttributes(Arrays.asList(attribute1, attribute2));

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct), null, LocalDateTime.now());

        SumMaxValidation validator = new SumMaxValidation("weight", 10);

        assertDoesNotThrow(() -> validator.validate(pedido));
    }

    @Test
    public void testValidationFail() {
        Attribute attribute1 = new Attribute("weight", "7");
        Attribute attribute2 = new Attribute("weight", "5");

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setName("Product A");
        orderedProduct.setAttributes(Arrays.asList(attribute1, attribute2));

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct), null, LocalDateTime.now());

        SumMaxValidation validator = new SumMaxValidation("weight", 10);

        assertThrows(ValidationFailedException.class, () -> validator.validate(pedido));
    }
}
