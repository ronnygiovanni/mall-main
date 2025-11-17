package com.example.MallManagement.repository;

import com.example.MallManagement.model.Purchase;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseRepository extends InFileRepository<Purchase> {
    public PurchaseRepository() {
        super("purchases.json", Purchase.class);
    }
}