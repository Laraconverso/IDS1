package com.is1g6.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPedidoDTO {
    private Long id;
    private Integer cantidad;
}
