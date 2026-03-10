package com.ironhack.labrestapi.service;

import com.ironhack.labrestapi.exception.InvalidPriceRangeException;
import com.ironhack.labrestapi.exception.ProductNotFoundException;
import com.ironhack.labrestapi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final Map<Long, Product> products = new HashMap<>();
    private Long nextId = 1L;


    public ProductService() {
        //seed product1
        Product p1 = new Product();
        p1.setId(nextId++);
        p1.setName("Product 1");
        p1.setPrice(12);
        p1.setCategory("Food");
        p1.setQuantity(5);
        products.put(p1.getId(), p1);


        //seed product2
        Product p2 = new Product();
        p2.setId(nextId++);
        p2.setName("Product 2");
        p2.setPrice(20);
        p2.setCategory("Electronics");
        p2.setQuantity(3);
        products.put(p2.getId(), p2);

        //seed product3
        Product p3 = new Product();
        p3.setId(nextId++);
        p3.setName("Product 3");
        p3.setPrice(15);
        p3.setCategory("Clothing");
        p3.setQuantity(10);
        products.put(p3.getId(), p3);

    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }


    public Product create(Product product) {
        product.setId(nextId++);
        products.put(product.getId(), product);
        return product;
    }


    public List<Product> findByPriceRange(double min, double max) {
        if (min < 0 || max < 0) {
            throw new InvalidPriceRangeException("Price cannot be negative");
        }

        if (min > max) {
            throw new InvalidPriceRangeException("Start price cannot be greater than end price");
        }


        List<Product> productsByRange = new ArrayList<>();

        for (Product product : products.values()) {
            if (product.getPrice() >= min && product.getPrice() <= max) {
                productsByRange.add(product);
            }

        }
        return productsByRange;
    }


    public List<Product> findByCategory(String category) {
        List<Product> productsByCategory = new ArrayList<>();

        for (Product product : products.values()) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory;

    }

    public Product findByName(String name) {
        for (Product product : products.values()) {
            if (product.getName().equalsIgnoreCase(name)) {

                return product;
            }

        }
        throw new ProductNotFoundException("Product not found: " + name);


    }

    public Product update(Long id, Product product) {
        Product existingProduct = products.get(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        } else {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setQuantity(product.getQuantity());
            return existingProduct;
        }

    }

    public void delete(Long id) {
        Product existingProduct = products.get(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        } else {
            products.remove(id);
        }

    }


}
