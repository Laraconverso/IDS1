package com.is1g6.backend.validations;

import com.is1g6.backend.exceptions.BadRequestException;
import com.is1g6.backend.exceptions.ValidationFailedException;
import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.Pedido;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("TypeCombinationValidation")
public class TypeCombinationValidation extends Validator {
    private String attributeType;
    private List<String> attributeValues;

    /**
     * This class validates that the pedido does not contain products with attribute values inside the attributeValues list
     * Example: you cant buy products with attribute type liquid and gaseous.
     * @param pedido the pedido to validate
     */
    @Override
    public void validate(Pedido pedido) {
        Set<String> values = pedido.getProducts().stream()
                .flatMap(product -> product.getAttributes().stream())
                .filter(attribute -> attribute.getType().equals(attributeType))
                .map(Attribute::getItsValue)
                .collect(Collectors.toSet());

        if (values.containsAll(attributeValues)) {
            StringBuilder errorMessage = new StringBuilder("No podes combinar productos que tengan el atributo " + attributeType + " " + attributeValues.get(0));
            for (int i = 1; i < attributeValues.size() - 1; i++) {
                errorMessage.append(", ").append(attributeValues.get(i));
            }
            errorMessage.append(" y ").append(attributeValues.get(attributeValues.size()-1));
            throw new ValidationFailedException(errorMessage.toString());
        }
    }
}
