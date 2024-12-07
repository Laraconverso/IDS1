package com.is1g6.backend.validations;

import com.is1g6.backend.exceptions.BadRequestException;
import com.is1g6.backend.exceptions.ValidationFailedException;
import com.is1g6.backend.model.Pedido;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("CountMaxValidation")
public class CountMaxValidation extends Validator {
    private String attributeType;
    private String attributeValue;
    private int attributeMaxValueCount;

    /**
     * This class validates that the amount of attributes with type == attributeType and value == attributeValue do
     * not exceed attributeMaxValueCount
     * Example: you cant buy more than 5 products with attributeType color and attributeValue red
     * @param pedido the pedido to validate
     */
    @Override
    public void validate(Pedido pedido) {
        int count = pedido.getProducts().stream()
                .flatMap(product -> product.getAttributes().stream())
                .filter(attribute -> attribute.getType().equals(attributeType) && attribute.getItsValue().equals(attributeValue))
                .toList().size();
        if (count > attributeMaxValueCount) {
            throw new ValidationFailedException("El pedido se excede en " + attributeType + " total");
        }
    }
}
