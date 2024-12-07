package com.is1g6.backend.validations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.is1g6.backend.model.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TypeCombinationValidation.class, name = "TypeCombinationValidation"),
        @JsonSubTypes.Type(value = QuantityValidation.class, name = "QuantityValidation"),
        @JsonSubTypes.Type(value = SumMaxValidation.class, name = "SumMaxValidation"),
        @JsonSubTypes.Type(value = CountMaxValidation.class, name = "CountMaxValidation")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract void validate(Pedido pedido);
}