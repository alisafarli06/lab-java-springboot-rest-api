package com.ironhack.labrestapi.service;

import com.ironhack.labrestapi.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final Map<Long, Customer> customers = new HashMap<>();
    private Long nextId = 1L;

    public CustomerService() {
        //seed customer1
        Customer c1 = new Customer();
        c1.setId(nextId++);
        c1.setName("John Doe");
        c1.setEmail("alisada12@gmail.com");
        c1.setAge(30);
        c1.setAddress("123 Main St");
        customers.put(c1.getId(), c1);

        //seed customer2
        Customer c2 = new Customer();
        c2.setId(nextId++);
        c2.setName("Jane Smith");
        c2.setEmail("Jane1232@gmail.com");
        c2.setAge(25);
        c2.setAddress("456 Elm St");
        customers.put(c2.getId(), c2);
    }

    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    public Customer create(Customer customer) {
        customer.setId(nextId++);
        customers.put(customer.getId(), customer);
        return customer;
    }

    public Customer findByEmail(String email) {
        for (Customer customer : customers.values()) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }

        throw new RuntimeException("Customer  not found");
    }

    public Customer findById(Long id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        return customer;
    }

    public Customer update(Long id, Customer updatedCustomer) {
        Customer existingCustomer = findById(id);
        if (existingCustomer == null) {
            throw new RuntimeException("Customer not found");
        }
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setAge(updatedCustomer.getAge());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        return existingCustomer;
    }

    public void delete(Long id) {
        findById(id);
        customers.remove(id);
    }
}
