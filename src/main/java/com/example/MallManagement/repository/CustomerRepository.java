package com.example.MallManagement.repository;

import com.example.MallManagement.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository extends InFileRepository<Customer> {
    public CustomerRepository() {
        super("customers.json", Customer.class);
    }
}