package com.is1g6.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.is1g6.backend.model.PedidoState;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private List<ProductPedidoDTO> products;
    private PedidoState estado;
    private LocalDateTime fecha;
    private String username;
}
