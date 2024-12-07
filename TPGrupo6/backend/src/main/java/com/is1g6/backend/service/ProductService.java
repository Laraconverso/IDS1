package com.is1g6.backend.service;

import com.is1g6.backend.dto.ProductDTO;
import com.is1g6.backend.model.Attribute;
import com.is1g6.backend.model.Product;
import com.is1g6.backend.repository.AttributeRepository;
import com.is1g6.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    public ProductService(ProductRepository productRepository, AttributeRepository attributeRepository) {
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
    }

    public Product createProduct(Product product) {
        attributeRepository.saveAll(product.getAttributes());
        return productRepository.save(product);
    }
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        product = productRepository.save(product);
        return convertToDTO(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setBrand(productDetails.getBrand());
        product.setCantidad(productDetails.getCantidad());
        product.setPrice(productDetails.getPrice());
        product.setAttributes(productDetails.getAttributes());
        productRepository.save(product);
        return convertToDTO(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<ProductDTO> getAllProductsDTO() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        //dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setCantidad(product.getCantidad());
        dto.setPrice(product.getPrice());
        dto.setAttributes(product.getAttributes());
        return dto;
    }


    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setCantidad(dto.getCantidad());
        product.setPrice(dto.getPrice());
        product.setAttributes(dto.getAttributes());
        return product;
    }

}
