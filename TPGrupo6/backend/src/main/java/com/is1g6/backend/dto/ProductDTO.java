package com.is1g6.backend.dto;


import com.is1g6.backend.model.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private String brand;
    private int cantidad;
    private float price;
//    private Long stockId;
//    private Long pedidoId;
    private List<Attribute> attributes;
}
