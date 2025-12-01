package com.example.MallManagement.service;

import com.example.MallManagement.model.Customer;
import com.example.MallManagement.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    public List<Customer> findAll() { return repo.findAll(); }
    public Customer findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Customer customer) { repo.save(customer); }
    public void delete(Long id) { repo.deleteById(id); }
}