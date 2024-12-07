package com.is1g6.backend.Validation;

import com.is1g6.backend.exceptions.ValidationFailedException;
import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.OrderedProduct;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.validations.TypeCombinationValidation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypeCombinationValidationTest {

    @Test
    public void testValidationSuccess() {
        Attribute attribute1 = new Attribute("estado", "líquido");

        OrderedProduct orderedProduct1 = new OrderedProduct();
        orderedProduct1.setAttributes(Arrays.asList(attribute1));

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct1), null, LocalDateTime.now());

        TypeCombinationValidation validator = new TypeCombinationValidation("estado", Arrays.asList("gaseoso"));

        assertDoesNotThrow(() -> validator.validate(pedido));
    }

    @Test
    public void testValidationFail() {
        Attribute attribute1 = new Attribute("estado", "líquido");
        Attribute attribute2 = new Attribute("estado", "gaseoso");

        OrderedProduct orderedProduct1 = new OrderedProduct();
        orderedProduct1.setAttributes(Arrays.asList(attribute1));

        OrderedProduct orderedProduct2 = new OrderedProduct();
        orderedProduct2.setAttributes(Arrays.asList(attribute2));

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct1, orderedProduct2), null, LocalDateTime.now());

        TypeCombinationValidation validator = new TypeCombinationValidation("estado", Arrays.asList("líquido", "gaseoso"));

        assertThrows(ValidationFailedException.class, () -> validator.validate(pedido));
    }
}
