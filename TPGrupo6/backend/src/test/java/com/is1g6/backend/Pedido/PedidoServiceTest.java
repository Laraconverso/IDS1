package com.is1g6.backend.service;

import com.is1g6.backend.dto.PedidoDTO;
import com.is1g6.backend.dto.ProductPedidoDTO;
import com.is1g6.backend.model.*;
import com.is1g6.backend.repository.PedidoRepository;
import com.is1g6.backend.repository.ProductRepository;
import com.is1g6.backend.repository.OrderedProductRepository;
import com.is1g6.backend.repository.UserRepository;
import com.is1g6.backend.exceptions.BadRequestException;
import com.is1g6.backend.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderedProductRepository orderedProductRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoDTO pedidoDTO;
    private Pedido pedido;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testUser");

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setUsername(testUser.getUsername());
        pedidoDTO.setEstado(PedidoState.CONFIRMADO);
        pedidoDTO.setFecha(LocalDateTime.now());
        pedidoDTO.setProducts(Collections.singletonList(new ProductPedidoDTO(1L, 2)));

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUser(testUser);
        pedido.setEstado(PedidoState.CONFIRMADO);
        pedido.setFecha(LocalDateTime.now());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testCreatePedido() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCantidad(10);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setUsername("testUser");
        pedidoDTO.setEstado(PedidoState.CONFIRMADO);
        pedidoDTO.setFecha(LocalDateTime.now());
        pedidoDTO.setProducts(Collections.singletonList(new ProductPedidoDTO(1L, 2)));

        PedidoDTO createdPedido = pedidoService.createPedido(pedidoDTO);

        assertNotNull(createdPedido);
        assertEquals("testUser", createdPedido.getUsername());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(orderedProductRepository, times(1)).save(any());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testCancelPedidoSuccess() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCantidad(10);

        Pedido pedido = new Pedido();
        pedido.setUser(testUser);
        pedido.setEstado(PedidoState.CONFIRMADO);
        pedido.setFecha(LocalDateTime.now());
        pedido.setProducts(Collections.singletonList(new OrderedProduct(1L, "Test Product",
                "Desc", "Brand", 2, 2000, new ArrayList<>())));

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        pedidoService.cancelPedido(1L);

        assertEquals(PedidoState.CANCELADO, pedido.getEstado());
        verify(pedidoRepository, times(1)).save(pedido);
    }


    @Test
    public void testCancelPedidoFailPedidoNotFound() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.cancelPedido(1L);
        });
        assertEquals("Pedido not found", exception.getMessage());
    }


    @Test
    public void testCancelPedidoFailPedidoOlderThan24Hours() {
        pedido.setFecha(LocalDateTime.now().minusDays(1));
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            pedidoService.cancelPedido(1L);
        });
        assertEquals("Pedido is older than 24 hours, cannot cancel", exception.getMessage());
    }

    @Test
    public void testConvertToEntity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        when(userRepository.getByUsername("testUser")).thenReturn(testUser);

        Pedido convertedPedido = pedidoService.convertToEntity(pedidoDTO);

        assertNotNull(convertedPedido);
        assertNotNull(convertedPedido.getUser());
        assertEquals(testUser.getUsername(), convertedPedido.getUser().getUsername());
        verify(productRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).getByUsername("testUser");
    }


    @Test
    public void testUpdatePedidoSuccess() {
        Long pedidoId = 1L;

        Pedido existingPedido = new Pedido();
        existingPedido.setId(pedidoId);
        existingPedido.setFecha(LocalDateTime.now().minusDays(1));
        existingPedido.setEstado(PedidoState.CONFIRMADO);
        existingPedido.setUser(testUser);
        existingPedido.setProducts(Collections.emptyList());

        Pedido pedidoDetails = new Pedido();
        pedidoDetails.setFecha(LocalDateTime.now());
        pedidoDetails.setEstado(PedidoState.PROCESADO);
        pedidoDetails.setUser(testUser);
        OrderedProduct product = new OrderedProduct();
        product.setId(1L);
        pedidoDetails.setProducts(Collections.singletonList(product));

        List<OrderedProduct> orderedProducts = Collections.singletonList(product);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(existingPedido));
        when(orderedProductRepository.findAllById(anyList())).thenReturn(orderedProducts);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(existingPedido);

        Pedido updatedPedido = pedidoService.updatePedido(pedidoId, pedidoDetails);

        assertNotNull(updatedPedido);
        assertEquals(pedidoDetails.getFecha(), updatedPedido.getFecha());
        assertEquals(PedidoState.PROCESADO, updatedPedido.getEstado());
        assertEquals(testUser, updatedPedido.getUser());
        assertEquals(orderedProducts, updatedPedido.getProducts());

        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(orderedProductRepository, times(1)).findAllById(anyList());
        verify(pedidoRepository, times(1)).save(existingPedido);
    }

    @Test
    public void testUpdatePedidoNotFound() {
        Long pedidoId = 1L;

        Pedido pedidoDetails = new Pedido();
        pedidoDetails.setFecha(LocalDateTime.now());
        pedidoDetails.setEstado(PedidoState.PROCESADO);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.updatePedido(pedidoId, pedidoDetails);
        });

        assertEquals("Pedido not found", exception.getMessage());

        verify(pedidoRepository, times(1)).findById(pedidoId);
        verifyNoMoreInteractions(pedidoRepository, orderedProductRepository);
    }



}
