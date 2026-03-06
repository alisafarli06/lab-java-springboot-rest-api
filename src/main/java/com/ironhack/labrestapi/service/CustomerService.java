package com.ironhack.labrestapi.service;

import com.ironhack.labrestapi.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final Map<Long, Customer> customers= new HashMap<>();
    private Long nextId = 1L;

    public CustomerService() {
        //seed customer1
        Customer c1 = new Customer();
        c1.setName("John Doe");
        c1.setEmail("alisada12@gmail.com");
        c1.setAge(30);
        c1.setAddress("123 Main St");
        customers.put(nextId++, c1);

        //seed customer2
        Customer c2 = new Customer();
        c2.setName("Jane Smith");
        c2.setEmail("Jane1232@gmail.com");
        c2.setAge(25);
        c2.setAddress("456 Elm St");
        customers.put(nextId++, c2);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public Customer create(String name, String email, int age, String address) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAge(age);
        customer.setAddress(address);
        customers.put(nextId++, customer);
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
}
