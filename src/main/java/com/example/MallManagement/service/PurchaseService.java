package com.example.MallManagement.service;

import com.example.MallManagement.model.Purchase;
import com.example.MallManagement.repository.PurchaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository repo;

    public PurchaseService(PurchaseRepository repo) { this.repo = repo; }

    public List<Purchase> findAll() { return repo.findAll(); }

    public List<Purchase> findAll(Double minAmount, Double maxAmount, Long customerId, Long shopId, Sort sort) {
        Specification<Purchase> spec = Specification.where(null);

        if (minAmount != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("amount"), minAmount));
        }

        if (maxAmount != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("amount"), maxAmount));
        }

        if (customerId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("customer").get("id"), customerId));
        }

        if (shopId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("shop").get("id"), shopId));
        }

        return repo.findAll(spec, sort);
    }

    public Purchase findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Purchase purchase) { repo.save(purchase); }
    public void delete(Long id) { repo.deleteById(id); }
}