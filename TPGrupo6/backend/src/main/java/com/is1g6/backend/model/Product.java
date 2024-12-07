package com.is1g6.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String brand;

    private int cantidad;

    private float price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Attribute> attributes = new ArrayList<>();

    public Product(String name, String description, String brand, int cantidad, float price, List<Attribute> attributes) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.cantidad = cantidad;
        this.price = price;
        this.attributes = new ArrayList<>(attributes);
    }
}
