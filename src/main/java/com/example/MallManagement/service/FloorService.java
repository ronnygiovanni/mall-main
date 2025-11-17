package com.example.MallManagement.service;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.repository.FloorRepository;
import org.springframework.stereotype.Service;

@Service
public class FloorService extends com.example.MallManagement.service.Service<Floor> {
    public FloorService(FloorRepository repo) {
        super(repo);
    }
}