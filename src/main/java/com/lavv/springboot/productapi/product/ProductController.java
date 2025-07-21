package com.lavv.springboot.productapi.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") UUID id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") UUID id) {
        productService.deleteProductById(id);
    }

    @PostMapping("/new")
    public UUID saveNewProduct(@RequestBody NewProductRequest product) {
        return productService.saveNewProduct(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable UUID id, @RequestBody UpdateProductRequest productUpdateRequest) {
        productService.updateProduct(id, productUpdateRequest);
    }
}
