package com.example.MallManagement.service;

import com.example.MallManagement.model.Mall;
import com.example.MallManagement.repository.MallRepository;
import org.springframework.stereotype.Service;

@Service
public class MallService extends com.example.MallManagement.service.Service<Mall> {
    public MallService(MallRepository repo) {
        super(repo);
    }
}