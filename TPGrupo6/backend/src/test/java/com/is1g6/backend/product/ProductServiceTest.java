package com.is1g6.backend.service;

import com.is1g6.backend.dto.ProductDTO;
import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.Product;
import com.is1g6.backend.repository.AttributeRepository;
import com.is1g6.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AttributeRepository attributeRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        Product product = new Product("Product A", "Description A", "Brand A", 2, 2000, Collections.emptyList());
        ProductDTO productDTO = new ProductDTO("Product A", "Description A", "Brand A", 2, 2000, Collections.emptyList());

        when(productRepository.save(product)).thenReturn(product);
        when(attributeRepository.saveAll(product.getAttributes())).thenReturn(Collections.emptyList());

        ProductDTO createdProductDTO = productService.createProduct(productDTO);

        assertThat(createdProductDTO).isNotNull();
        assertThat(createdProductDTO.getName()).isEqualTo("Product A");
        assertThat(createdProductDTO.getDescription()).isEqualTo("Description A");
        assertThat(createdProductDTO.getBrand()).isEqualTo("Brand A");
        assertThat(createdProductDTO.getCantidad()).isEqualTo(2);
    }

    @Test
    public void testGetProductById() {
        Product product = new Product("Product B", "Description B", "Brand B", 5, 2000, Collections.emptyList());
        Optional<Product> productOptional = Optional.of(product);

        when(productRepository.findById(1L)).thenReturn(productOptional);

        Optional<Product> foundProduct = productService.getProductById(1L);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Product B");
        assertThat(foundProduct.get().getDescription()).isEqualTo("Description B");
        assertThat(foundProduct.get().getBrand()).isEqualTo("Brand B");
        assertThat(foundProduct.get().getCantidad()).isEqualTo(5);
    }

    @Test
    public void testGetProductDTOById() {
        Product product = new Product("Product C", "Description C", "Brand C", 3, 2000, Collections.emptyList());
        Optional<Product> productOptional = Optional.of(product);

        when(productRepository.findById(1L)).thenReturn(productOptional);

        Optional<ProductDTO> foundProductDTO = productService.getProductDTOById(1L);

        assertThat(foundProductDTO).isPresent();
        assertThat(foundProductDTO.get().getName()).isEqualTo("Product C");
        assertThat(foundProductDTO.get().getDescription()).isEqualTo("Description C");
        assertThat(foundProductDTO.get().getBrand()).isEqualTo("Brand C");
        assertThat(foundProductDTO.get().getCantidad()).isEqualTo(3);
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product("Product D", "Description D", "Brand D", 4, 2000, Collections.emptyList());
        ProductDTO productDTO = new ProductDTO("Updated Product D", "Updated Description D", "Updated Brand D", 5, 2000, Collections.emptyList());
        Product updatedProduct = new Product("Updated Product D", "Updated Description D", "Updated Brand D", 5, 2000, Collections.emptyList());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        ProductDTO updatedProductDTO = productService.updateProduct(1L, productDTO);

        assertThat(updatedProductDTO).isNotNull();
        assertThat(updatedProductDTO.getName()).isEqualTo("Updated Product D");
        assertThat(updatedProductDTO.getDescription()).isEqualTo("Updated Description D");
        assertThat(updatedProductDTO.getBrand()).isEqualTo("Updated Brand D");
        assertThat(updatedProductDTO.getCantidad()).isEqualTo(5);
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product("Product F", "Description F", "Brand F", 7, 2000, Collections.emptyList());
        Product product2 = new Product("Product G", "Description G", "Brand G", 8, 2000, Collections.emptyList());

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Product F");
        assertThat(products.get(1).getName()).isEqualTo("Product G");
    }

    @Test
    public void testGetAllProductsDTO() {
        Product product1 = new Product("Product H", "Description H", "Brand H", 9, 2000, Collections.emptyList());
        Product product2 = new Product("Product I", "Description I", "Brand I", 10, 2000, Collections.emptyList());

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDTO> productDTOs = productService.getAllProductsDTO();

        assertThat(productDTOs).hasSize(2);
        assertThat(productDTOs.get(0).getName()).isEqualTo("Product H");
        assertThat(productDTOs.get(1).getName()).isEqualTo("Product I");
    }
}
