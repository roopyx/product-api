package com.lavv.springboot.productapi.product;

import com.lavv.springboot.productapi.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapToResponse())
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(UUID id) {
        return productRepository.findById(id)
                .map(mapToResponse())
                .orElseThrow(() -> new ResourceNotFound(
                "product with id [" + id + "] not found"
        ));
    }

    public void deleteProductById(UUID id) {
        boolean exists = productRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFound("product with id [" + id + "] not found");
        }
        productRepository.deleteById(id);
    }

    public UUID saveNewProduct(NewProductRequest product) {
        UUID id = UUID.randomUUID();
        Product p = new Product(
                id,
                product.name(),
                product.description(),
                product.price(),
                product.imageUrl(),
                product.stockLevel()
        );
        productRepository.save(p);
        return id;
    }

    private Function<Product, ProductResponse> mapToResponse() {
        return p -> new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getImageUrl(),
                p.getStockLevel(),
                p.getCreatedAt(),
                p.getUpdatedAt(),
                p.getDeletedAt()
        );
    }

    public void updateProduct(UUID id, UpdateProductRequest productUpdateRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(
                        "product with id [" + id + "] not found"
                ));

        if (productUpdateRequest.name() != null && !productUpdateRequest.name().equals(product.getName())) {
            product.setName(productUpdateRequest.name());
        }
        if (productUpdateRequest.description() != null && !productUpdateRequest.description().equals(product.getDescription())) {
            product.setDescription(productUpdateRequest.description());
        }
        if (productUpdateRequest.price() != null && !productUpdateRequest.price().equals(product.getPrice())) {
            product.setPrice(productUpdateRequest.price());
        }
        if (productUpdateRequest.stockLevel() != null && !productUpdateRequest.stockLevel().equals(product.getStockLevel())) {
            product.setStockLevel(productUpdateRequest.stockLevel());
        }
        if (productUpdateRequest.imageUrl() != null && !productUpdateRequest.imageUrl().equals(product.getImageUrl())) {
            product.setImageUrl(productUpdateRequest.imageUrl());
        }

        productRepository.save(product);
    }
}
