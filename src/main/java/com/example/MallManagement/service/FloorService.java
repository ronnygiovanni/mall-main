package com.example.MallManagement.service;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.repository.FloorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FloorService {
    private final FloorRepository repo;

    public FloorService(FloorRepository repo) { this.repo = repo; }

    public List<Floor> findAll() { return repo.findAll(); }
    public Floor findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Floor floor) { repo.save(floor); }
    public void delete(Long id) { repo.deleteById(id); }
}