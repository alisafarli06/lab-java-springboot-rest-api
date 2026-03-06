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

    private final Map<String, Product> products = new HashMap<>();




    public ProductService() {
        //seed product1
        Product p1 = new Product();
        p1.setName("Product 1");
        p1.setPrice(12);
        p1.setCategory("Food");
        p1.setQuantity(5);
        products.put(p1.getName(), p1);


        //seed product2
        Product p2 = new Product();
        p2.setName("Product 2");
        p2.setPrice(20);
        p2.setCategory("Electronics");
        p2.setQuantity(3);
        products.put(p2.getName(), p2);

        //seed product3
        Product p3 = new Product();
        p3.setName("Product 3");
        p3.setPrice(15);
        p3.setCategory("Clothing");
        p3.setQuantity(10);
        products.put(p3.getName(), p3);

    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }


    public Product create(String name, double price, String category, int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setQuantity(quantity);
        products.put(product.getName(), product);
        return product;
    }


    public List<Product> findByPriceRange(double startPrice, double endPrice) {
        if (startPrice<0 || endPrice < 0) {
            throw new InvalidPriceRangeException("Price cannot be negative");
        }

        if (startPrice > endPrice) {
            throw new InvalidPriceRangeException("Start price cannot be greater than end price");
        }


        List<Product> productsByRange = new ArrayList<>();

        for (Product product : products.values()) {
            if (product.getPrice() >= startPrice && product.getPrice() <= endPrice) {
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

    public Product findByName(String Name) {
        for (Product product : products.values()) {
            if (product.getName().equalsIgnoreCase(Name)) {

            return product;
        }

    }
        throw new ProductNotFoundException("Product not found: " + Name);
}









}
