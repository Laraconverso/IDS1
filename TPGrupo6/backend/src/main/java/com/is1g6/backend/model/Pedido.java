package com.is1g6.backend.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<OrderedProduct> products;

    private PedidoState estado;

    private LocalDateTime fecha;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Pedido(List<OrderedProduct> products, PedidoState estado, LocalDateTime fecha) {
        this.products = products;
        this.estado = estado;
        this.fecha = fecha;
    }
}
