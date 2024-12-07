package com.is1g6.backend.controller;

import com.is1g6.backend.dto.PedidoDTO;
import com.is1g6.backend.model.Pedido;
import com.is1g6.backend.model.PedidoState;
import com.is1g6.backend.security.JwtService;
import com.is1g6.backend.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PedidoService pedidoService;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testGetAllPedidos() throws Exception {
        Pedido pedido = new Pedido(Collections.emptyList(), PedidoState.CONFIRMADO, LocalDateTime.now());
        when(pedidoService.getAllPedidos()).thenReturn(Collections.singletonList(pedido));

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(pedido.getId()))
                .andExpect(jsonPath("$[0].estado").value("CONFIRMADO"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGetPedidoById() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO(Collections.emptyList(), PedidoState.CONFIRMADO, LocalDateTime.now(), "testUser");

        when(pedidoService.getPedidoById(1L)).thenReturn(Optional.of(pedidoDTO));

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CONFIRMADO"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGetPedidoByIdNotFound() throws Exception {
        when(pedidoService.getPedidoById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testCreatePedido() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO(Collections.emptyList(), PedidoState.PROCESADO, LocalDateTime.now(), "testUser");
        when(pedidoService.createPedido(any(PedidoDTO.class))).thenReturn(pedidoDTO);

        mockMvc.perform(post("/pedidos")
                        .contentType("application/json")
                        .content("{\"products\": [], \"estado\": \"PROCESADO\", \"fecha\": \"2024-11-27T12:00:00\", \"username\": \"testUser\"}")
                        .with(csrf()))  // Agrega el token CSRF a la solicitud
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PROCESADO"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

//    @Test
//    @WithMockUser(username = "testUser", roles = "USER")
//    public void testUpdatePedido() throws Exception {
//        // Crear el DTO de entrada correctamente
//        PedidoDTO pedidoDTORequest = new PedidoDTO(Collections.emptyList(), PedidoState.ENVIADO, LocalDateTime.now(), "testUser");
//
//        // Simular que el servicio devuelve el pedido actualizado correctamente
//        when(pedidoService.updatePedido(eq(1L), any(Pedido.class))).thenReturn(new Pedido(Collections.emptyList(), PedidoState.ENVIADO, LocalDateTime.now()));
//        // Simular que el servicio convierte correctamente a DTO
//        when(pedidoService.convertToDTO(any(Pedido.class))).thenReturn(pedidoDTORequest);
//
//        // Realizar la solicitud y verificar la respuesta
//        mockMvc.perform(patch("/pedidos/1")
//                        .contentType("application/json")
//                        .content("{\"estado\": \"ENVIADO\", \"username\": \"testUser\"}")
//                        .with(csrf()))  // Asegúrate de agregar el token CSRF
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.estado").value("ENVIADO"))
//                .andExpect(jsonPath("$.username").value("testUser"));
//    }


    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testDeletePedido() throws Exception {
        doNothing().when(pedidoService).deletePedido(1L);

        mockMvc.perform(delete("/pedidos/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido with id 1 successfully deleted."));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testCancelPedido() throws Exception {
        doNothing().when(pedidoService).cancelPedido(1L);

        mockMvc.perform(put("/pedidos/cancel/1")
                        .with(csrf()))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Pedido successfully canceled"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testFindAllPedidosByUsername() throws Exception {
        Pedido pedido = new Pedido(Collections.emptyList(), PedidoState.CONFIRMADO, LocalDateTime.now());
        when(pedidoService.findAllPedidos("testUser")).thenReturn(Collections.singletonList(pedido));

        mockMvc.perform(get("/pedidos/user/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(pedido.getId()))
                .andExpect(jsonPath("$[0].estado").value("CONFIRMADO"));
    }

}
