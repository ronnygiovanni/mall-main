package com.example.MallManagement.repository;

import com.example.MallManagement.model.Shop;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepository extends InFileRepository<Shop> {
    public ShopRepository() {
        super("shops.json", Shop.class);
    }
}