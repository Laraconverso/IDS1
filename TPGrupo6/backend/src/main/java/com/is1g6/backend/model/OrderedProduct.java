package com.is1g6.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderedProduct {

    @Id
    @GeneratedValue
    private Long id;

    private Long originalProductId;

    private String name;

    private String description;

    private String brand;

    private int cantidad;

    private float price;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ordered_product_id")
    private List<Attribute> attributes = new ArrayList<>();

    public OrderedProduct(Long originalProductId, String name, String description, String brand, int cantidad, float price, List<Attribute> attributes) {
        this.originalProductId = originalProductId;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.cantidad = cantidad;
        this.price = price;
        this.attributes = new ArrayList<>(attributes);
    }
}
