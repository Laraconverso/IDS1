package com.is1g6.backend.service;

import com.is1g6.backend.dto.PedidoDTO;
import com.is1g6.backend.dto.ProductPedidoDTO;
import com.is1g6.backend.exceptions.BadRequestException;
import com.is1g6.backend.exceptions.NotFoundException;
import com.is1g6.backend.model.*;
import com.is1g6.backend.repository.OrderedProductRepository;
import com.is1g6.backend.repository.PedidoRepository;
import com.is1g6.backend.repository.ProductRepository;
import com.is1g6.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProductRepository productRepository, OrderedProductRepository orderedProductRepository, UserRepository userRepository, ValidatorService validatorService) {
        this.pedidoRepository = pedidoRepository;
        this.productRepository = productRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.userRepository = userRepository;
        this.validatorService = validatorService;
    }


    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = convertToEntity(pedidoDTO);

        validatorService.validate(pedido);

        for (OrderedProduct orderedProduct : pedido.getProducts()) {
            Product originalProduct = productRepository.findById(orderedProduct.getOriginalProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + orderedProduct.getOriginalProductId()));

            if (originalProduct.getCantidad() < orderedProduct.getCantidad()) {
                throw new RuntimeException("Insufficient stock for product: " + originalProduct.getName());
            }
        }
        pedidoRepository.save(pedido);

        pedido.getProducts().forEach(orderedProduct -> {
            orderedProduct.setPedido(pedido);
            orderedProductRepository.save(orderedProduct);
            Product originalProduct = productRepository.findById(orderedProduct.getOriginalProductId()).get();
            originalProduct.setCantidad(originalProduct.getCantidad() - orderedProduct.getCantidad());
            productRepository.save(originalProduct);
        });

        User user = userRepository.findByUsername(pedidoDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + pedidoDTO.getUsername()));
        pedido.setUser(user);

        return convertToDTO(pedido);
    }


    public Optional<PedidoDTO> getPedidoById(Long id) {
        return pedidoRepository.findById(id).map(this::convertToDTO);
    }

    public List<Pedido> findAllPedidos(String username){
        return pedidoRepository.findPedidosByUsername(username);
    }

    public Pedido updatePedido(Long id, Pedido pedidoDetails) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido not found"));
        pedido.setFecha(pedidoDetails.getFecha());
        pedido.setEstado(pedidoDetails.getEstado());
        if (pedidoDetails.getUser() != null) {
            pedido.setUser(pedidoDetails.getUser());
        }
        List<OrderedProduct> products = orderedProductRepository.findAllById(
                pedidoDetails.getProducts().stream()
                        .map(OrderedProduct::getId)
                        .collect(Collectors.toList())
        );
        pedido.setProducts(products);
        return pedidoRepository.save(pedido);
    }


    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }


    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }


    public PedidoDTO convertToDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setProducts(pedido.getProducts().stream().map(product -> new ProductPedidoDTO(product.getOriginalProductId(), product.getCantidad())).collect(Collectors.toList()));
        pedidoDTO.setEstado(pedido.getEstado());
        pedidoDTO.setFecha(pedido.getFecha());
        pedidoDTO.setUsername(pedido.getUser().getUsername());
        return pedidoDTO;
    }

    public Pedido convertToEntity(PedidoDTO pedidoDTO) {
        if (pedidoDTO.getProducts() == null || pedidoDTO.getProducts().isEmpty()) {
            throw new BadRequestException("Pedidos cant have an empty list of Products");
        }

        Pedido pedido = new Pedido();
        List<OrderedProduct> products = pedidoDTO.getProducts().stream().map(productPedidoDTO -> {
            Product product = productRepository.findById(productPedidoDTO.getId()).orElseThrow(() -> new NotFoundException("Product ID not found: " + productPedidoDTO.getId()));
            return new OrderedProduct(product.getId(), product.getName(), product.getDescription(), product.getBrand(), productPedidoDTO.getCantidad(), product.getPrice(), product.getAttributes());
        }).toList();

        pedido.setProducts(products);
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setFecha(pedidoDTO.getFecha());
        User user = userRepository.getByUsername(pedidoDTO.getUsername());
        pedido.setUser(user);
        return pedido;
    }

    public void cancelPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido not found"));

        if (pedido.getEstado().equals(PedidoState.CONFIRMADO)) {
            LocalDateTime orderTime = pedido.getFecha();
            if (orderTime.isAfter(LocalDateTime.now().minusHours(24))) {
                pedido.setEstado(PedidoState.CANCELADO);

                pedido.getProducts().forEach(orderedProduct -> {
                    Product originalProduct = productRepository.findById(orderedProduct.getOriginalProductId()).get();
                    originalProduct.setCantidad(originalProduct.getCantidad() + orderedProduct.getCantidad());
                    productRepository.save(originalProduct);
                });

                pedidoRepository.save(pedido);
                System.out.println("Pedido status changed to CANCELADO");
            } else {
                throw new BadRequestException("Pedido is older than 24 hours, cannot cancel");
            }
        } else {
            throw new BadRequestException("Pedido status is " + pedido.getEstado() + ", cannot cancel");
        }
    }



}
