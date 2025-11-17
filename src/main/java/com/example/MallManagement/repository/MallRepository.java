package com.example.MallManagement.repository;

import com.example.MallManagement.model.Mall;
import org.springframework.stereotype.Repository;

@Repository
public class MallRepository extends InFileRepository<Mall> {
    public MallRepository() {
        super("malls.json", Mall.class);
    }
}