package com.example.MallManagement.service;

import com.example.MallManagement.model.Purchase;
import com.example.MallManagement.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository repo;

    public PurchaseService(PurchaseRepository repo) { this.repo = repo; }

    public List<Purchase> findAll() { return repo.findAll(); }
    public Purchase findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Purchase purchase) { repo.save(purchase); }
    public void delete(Long id) { repo.deleteById(id); }
}