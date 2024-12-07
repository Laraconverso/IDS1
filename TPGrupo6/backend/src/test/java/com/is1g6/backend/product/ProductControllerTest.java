package com.is1g6.backend.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.is1g6.backend.controller.ProductController;
import com.is1g6.backend.dto.ProductDTO;
import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.Product;
import com.is1g6.backend.security.JwtService;
import com.is1g6.backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ProductController.class)
@WithMockUser(username = "testuser", roles = {"USER"})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product("Mesa", "Mesa de comedor", "Muebles", 2, 2000, Collections.emptyList());
        Product product2 = new Product("Silla", "Silla de oficina", "Muebles", 5, 2000, Collections.emptyList());

        given(productService.getAllProducts()).willReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Mesa"))
                .andExpect(jsonPath("$[1].name").value("Silla"));
    }

    @Test
    public void testGetProductById() throws Exception {
        ProductDTO productDTO = new ProductDTO("Mesa", "Mesa de comedor", "Muebles", 2, 2000, Collections.emptyList());

        given(productService.getProductDTOById(1L)).willReturn(Optional.of(productDTO));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mesa"));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        given(productService.getProductDTOById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO("Mesa", "Mesa de comedor", "Muebles", 2, 2000, Collections.emptyList());

        ProductDTO createdProductDTO = new ProductDTO("Mesa", "Mesa de comedor", "Muebles", 2, 2000, Collections.emptyList());

        given(productService.createProduct(Mockito.any(ProductDTO.class))).willReturn(createdProductDTO);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mesa"))
                .andExpect(jsonPath("$.description").value("Mesa de comedor"))
                .andExpect(jsonPath("$.brand").value("Muebles"))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

}
