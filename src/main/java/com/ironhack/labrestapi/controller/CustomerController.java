package com.ironhack.labrestapi.controller;

import com.ironhack.labrestapi.model.Customer;
import com.ironhack.labrestapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.create(customer.getName(), customer.getEmail(), customer.getAge(), customer.getAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }



    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(
        @RequestParam (required = false) String email
        ) {

        if (email != null) {
            List<Customer> customersByEmail = new ArrayList<>();
            customersByEmail.add(customerService.findByEmail(email));
            return ResponseEntity.ok(customersByEmail);
        }

        return ResponseEntity.ok(customerService.getAllCustomers());


    }



}
