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
@DiscriminatorValue("QuantityValidation")
public class QuantityValidation extends Validator{
    private int maxCantidad;

    /**
     * This class validates that the quantity of a certain product does not exceed maxCantidad
     * Example: you cant buy more than 5 shirts
     * @param pedido the pedido to validate
     */
    @Override
    public void validate(Pedido pedido) {
        for (int i = 0; i < pedido.getProducts().size(); i++) {
            if (pedido.getProducts().get(i).getCantidad() > maxCantidad) {
                throw new ValidationFailedException("El producto " + pedido.getProducts().get(i).getName() + " supera el límite de " + maxCantidad);
            }
        }
    }
}
