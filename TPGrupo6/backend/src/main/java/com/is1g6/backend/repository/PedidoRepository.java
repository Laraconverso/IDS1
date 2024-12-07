package com.is1g6.backend.repository;

import com.is1g6.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.user.username = :username")
    List<Pedido> findPedidosByUsername(@Param("username") String username);

}