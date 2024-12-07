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
@DiscriminatorValue("SumMaxValidation")
public class SumMaxValidation extends Validator{
    private String attributeType;
    private int attributeMaxValue;

    /**
     * This class validates that the sum of the value of attributes with type attributeType does not exceed attributeMaxValue
     * Example: the sum of attribute weight does not exceed attributeMaxValue
     * @param pedido the pedido to validate
     */
    @Override
    public void validate(Pedido pedido) {
        Double sum = pedido.getProducts().stream()
                .flatMap(product -> product.getAttributes().stream())
                .filter(attribute -> attribute.getType().equals(attributeType))
                .map(attribute -> Double.parseDouble(attribute.getItsValue()))
                .reduce(Double::sum).orElse(0.0);
        if (sum > attributeMaxValue) {
            throw new ValidationFailedException("El pedido se excede en " + attributeType + " total");
        }
    }
}
