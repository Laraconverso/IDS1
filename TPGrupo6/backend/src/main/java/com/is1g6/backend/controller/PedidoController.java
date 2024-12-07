package com.is1g6.backend.controller;

import com.is1g6.backend.dto.PedidoDTO;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        Optional<PedidoDTO> pedidoDTO = pedidoService.getPedidoById(id);
        return pedidoDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO createPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.createPedido(pedidoDTO);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
//
//        try {
//            Pedido updatedPedido = pedidoService.updatePedido(id, pedidoService.convertToEntity(pedidoDTO));
//            return ResponseEntity.ok(pedidoService.convertToDTO(updatedPedido));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        try {
            Pedido updatedPedido = pedidoService.updatePedido(id, pedidoService.convertToEntity(pedidoDTO));
            return ResponseEntity.ok(pedidoService.convertToDTO(updatedPedido));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.ok("Pedido with id " + id + " successfully deleted.");
    }

    @GetMapping("/user/{username}")
    public List<Pedido> findAllPedidosByUsername(@PathVariable String username){
        return pedidoService.findAllPedidos(username);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelPedido(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Pedido successfully canceled");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }




}
