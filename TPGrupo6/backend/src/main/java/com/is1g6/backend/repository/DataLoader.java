package com.is1g6.backend.repository;

import com.is1g6.backend.model.*;
import com.is1g6.backend.validations.QuantityValidation;
import com.is1g6.backend.validations.SumMaxValidation;
import com.is1g6.backend.validations.TypeCombinationValidation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final PedidoRepository pedidoRepository;
    private final ValidatorRepository validatorRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AttributeRepository attributeRepository;

    public DataLoader(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder, PedidoRepository pedidoRepository, ValidatorRepository validatorRepository, OrderedProductRepository orderedProductRepository, AttributeRepository attributeRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.pedidoRepository = pedidoRepository;
        this.validatorRepository = validatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderedProductRepository = orderedProductRepository;
        this.attributeRepository = attributeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User("admin@admin.com", passwordEncoder.encode("admin"));
        admin.setRole("ADMIN");
        userRepository.save(admin);

        User user = new User("yo@mail.com", passwordEncoder.encode("1234"));
        user.setRole("USER");
        user.setName("YO");
        user.setSurname("ejemplo");
        user.setAge(23);
        user.setPicture("http://example.com/profile-pic.jpg");
        user.setGender("female");
        user.setAddress("Aca vivo yo");
        userRepository.save(user);

        List<Product> products = List.of(
                new Product("Remera", "Remera lisa blanca", "gucci", 8, 2000, List.of(
                        new Attribute("color", "red"),
                        new Attribute("weight", "1"),
                        new Attribute("type", "solid")
                )),
                new Product("Pantalon", "Pantalon negro", "gucci", 5, 2000, List.of(
                        new Attribute("color", "black"),
                        new Attribute("weight", "2"),
                        new Attribute("type", "solid")
                )),
                new Product("Botella de agua", "Botella de agua comun y corriente", "Smart", 100, 2000, List.of(
                        new Attribute("weight", "2"),
                        new Attribute("type", "liquid")
                ))
        );
        productRepository.saveAll(products);

        TypeCombinationValidation rule1 = new TypeCombinationValidation("color", List.of("red", "blue"));
        validatorRepository.save(rule1);
        QuantityValidation rule2 = new QuantityValidation(10);
        validatorRepository.save(rule2);
        SumMaxValidation rule3 = new SumMaxValidation("weight", 10);
        validatorRepository.save(rule3);

        Pedido pedido = new Pedido();
        pedido.setEstado(PedidoState.CONFIRMADO);
        pedido.setFecha(LocalDateTime.of(2024, 11, 18, 0, 0));
        pedido.setUser(user);
        pedido = pedidoRepository.save(pedido);
        List<Attribute> productAttributes = new ArrayList<>();
        productAttributes.add(new Attribute("Color", "Red"));
        productAttributes.add(new Attribute("Size", "L"));
        OrderedProduct op = new OrderedProduct();
        op.setName(products.get(0).getName());
        op.setCantidad(2);
        op.setAttributes(productAttributes);
        op.setBrand(products.get(0).getBrand());
        op.setDescription(products.get(0).getDescription());
        op.setPedido(pedido);
        List<OrderedProduct> orderedProduct = new ArrayList<>();
        orderedProduct.add(op);
        pedido.setProducts(orderedProduct);
        pedidoRepository.save(pedido);

    }
}