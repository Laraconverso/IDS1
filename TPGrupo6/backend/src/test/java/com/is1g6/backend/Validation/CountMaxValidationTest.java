package com.is1g6.backend.Validation;

import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.OrderedProduct;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.validations.CountMaxValidation;
import com.is1g6.backend.exceptions.ValidationFailedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CountMaxValidationTest {

    @Test
    public void testValidationSuccess() {
        Attribute attribute1 = new Attribute("color", "rojo");
        Attribute attribute2 = new Attribute("color", "rojo");

        OrderedProduct orderedProduct1 = new OrderedProduct();
        orderedProduct1.setAttributes(Arrays.asList(attribute1));

        OrderedProduct orderedProduct2 = new OrderedProduct();
        orderedProduct2.setAttributes(Arrays.asList(attribute2));

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct1, orderedProduct2), null, LocalDateTime.now());

        CountMaxValidation validator = new CountMaxValidation("color", "rojo", 2);

        assertDoesNotThrow(() -> validator.validate(pedido));
    }

    @Test
    public void testValidationFail() {
        Attribute attribute1 = new Attribute("color", "rojo");
        Attribute attribute2 = new Attribute("color", "rojo");
        Attribute attribute3 = new Attribute("color", "rojo");

        OrderedProduct orderedProduct1 = new OrderedProduct();
        orderedProduct1.setAttributes(Arrays.asList(attribute1));

        OrderedProduct orderedProduct2 = new OrderedProduct();
        orderedProduct2.setAttributes(Arrays.asList(attribute2));

        OrderedProduct orderedProduct3 = new OrderedProduct();
        orderedProduct3.setAttributes(Arrays.asList(attribute3));

        Pedido pedido = new Pedido(Arrays.asList(orderedProduct1, orderedProduct2, orderedProduct3), null, LocalDateTime.now());

        CountMaxValidation validator = new CountMaxValidation("color", "rojo", 2);

        assertThrows(ValidationFailedException.class, () -> validator.validate(pedido));
    }
}
