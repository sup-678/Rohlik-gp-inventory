package io.rohlik.gp.product.controller;

import io.rohlik.gp.product.dto.ProductDTO;
import io.rohlik.gp.product.input.ProductInput;
import io.rohlik.gp.product.entity.ProductEntity;
import io.rohlik.gp.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ProductDTO createProduct(@RequestBody ProductInput product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductInput product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}