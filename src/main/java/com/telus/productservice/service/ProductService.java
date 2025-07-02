package com.telus.productservice.service;

import com.telus.productservice.dto.ProductDTO;
import com.telus.productservice.dto.mapper.ProductMapper;
import com.telus.productservice.model.Product;
import com.telus.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Create new product
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    // Get all products
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get product by ID
    public Optional<ProductDTO> getProductById(String id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDTO);
    }

    // Update product
    public Optional<ProductDTO> updateProduct(String id, ProductDTO dto) {
        return productRepository.findById(id)
                .map(existing -> {
                    Product updated = ProductMapper.toEntity(dto);
                    updated.setId(id); // Preserve existing ID
                    Product saved = productRepository.save(updated);
                    return ProductMapper.toDTO(saved);
                });
    }

    // Delete product
    public boolean deleteProduct(String id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }
} 