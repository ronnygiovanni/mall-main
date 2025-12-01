package com.example.MallManagement.service;

import com.example.MallManagement.model.Shop;
import com.example.MallManagement.repository.ShopRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShopService {
    private final ShopRepository repo;

    public ShopService(ShopRepository repo) { this.repo = repo; }

    public List<Shop> findAll() { return repo.findAll(); }
    public Shop findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Shop shop) { repo.save(shop); }
    public void delete(Long id) { repo.deleteById(id); }
}