package com.example.MallManagement.service;

import com.example.MallManagement.model.Customer;
import com.example.MallManagement.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) { this.repo = repo; }

    public List<Customer> findAll() { return repo.findAll(); }

    public List<Customer> findAll(String name, String currency, Sort sort) {
        if ((name != null && !name.isEmpty()) || (currency != null && !currency.isEmpty())) {
            return repo.findByNameAndCurrency(name, currency, sort);
        }
        return repo.findAll(sort);
    }

    public Customer findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Customer customer) { repo.save(customer); }
    public void delete(Long id) { repo.deleteById(id); }
}