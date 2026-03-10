package com.ironhack.labrestapi.controller;

import com.ironhack.labrestapi.exception.InvalidApiKeyException;
import com.ironhack.labrestapi.model.Product;
import com.ironhack.labrestapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
    private static final String API_KEY = "123456";
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private void validateApiKey(String apiKey) {
        if (!API_KEY.equals(apiKey)) {
            throw new InvalidApiKeyException("Invalid API Key");
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestHeader(value = "API-Key") String apiKey, @RequestParam(required = false) String name, @RequestParam(required = false) String category, @RequestParam(required = false) Double min, @RequestParam(required = false) Double max) {

        validateApiKey(apiKey);

        if (name != null) {
            List<Product> productsByName = new ArrayList<>();
            productsByName.add(productService.findByName(name));
            return ResponseEntity.ok(productsByName);
        }
        if (category != null) {
            return ResponseEntity.ok(productService.findByCategory(category));
        }
        if (min != null && max != null) {
            return ResponseEntity.ok(productService.findByPriceRange(min, max));
        }
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestHeader(value = "API-Key") String apiKey, @Valid @RequestBody Product product) {
        validateApiKey(apiKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestHeader(value = "API-Key") String apiKey, @PathVariable Long id, @Valid @RequestBody Product product) {
        validateApiKey(apiKey);
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@RequestHeader(value = "API-Key") String apiKey, @PathVariable Long id) {
        validateApiKey(apiKey);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

