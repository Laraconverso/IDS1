package com.is1g6.backend.product;

import com.is1g6.backend.model.Product;
import com.is1g6.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        Product product = new Product("Product A", "Description A", "Brand A", 2, 2000, Collections.emptyList());
        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product A");
        assertThat(savedProduct.getDescription()).isEqualTo("Description A");
        assertThat(savedProduct.getBrand()).isEqualTo("Brand A");
        assertThat(savedProduct.getCantidad()).isEqualTo(2);
    }

    @Test
    public void testFindProductByName() {
        Product product = new Product("Product B", "Description B", "Brand B", 5, 2000, Collections.emptyList());
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findByName("Product B");
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getDescription()).isEqualTo("Description B");
        assertThat(foundProduct.get().getBrand()).isEqualTo("Brand B");
        assertThat(foundProduct.get().getCantidad()).isEqualTo(5);
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product("Product C", "Description C", "Brand C", 3, 2000, Collections.emptyList());
        productRepository.save(product);
        productRepository.delete(product);

        Optional<Product> foundProduct = productRepository.findByName("Product C");
        assertThat(foundProduct).isEmpty();
    }
}
