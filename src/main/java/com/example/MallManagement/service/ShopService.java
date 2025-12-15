package com.example.MallManagement.service;

import com.example.MallManagement.model.Shop;
import com.example.MallManagement.repository.ShopRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ShopService {
    private final ShopRepository repo;

    public ShopService(ShopRepository repo) { this.repo = repo; }

    public List<Shop> findAll() { return repo.findAll(); }

    public List<Shop> findAll(String name, String ownerName, Long floorId, Sort sort) {
        Specification<Shop> spec = Specification.where(null);

        if (StringUtils.hasText(name)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (StringUtils.hasText(ownerName)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("ownerName")), "%" + ownerName.toLowerCase() + "%"));
        }

        if (floorId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("floor").get("id"), floorId));
        }

        return repo.findAll(spec, sort);
    }

    public Shop findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Shop shop) { repo.save(shop); }
    public void delete(Long id) { repo.deleteById(id); }
}