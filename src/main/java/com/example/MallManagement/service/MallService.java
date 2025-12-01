package com.example.MallManagement.service;

import com.example.MallManagement.model.Mall;
import com.example.MallManagement.repository.MallRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MallService {
    private final MallRepository repo;

    public MallService(MallRepository repo) { this.repo = repo; }

    public List<Mall> findAll() { return repo.findAll(); }
    public Mall findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Mall mall) { repo.save(mall); }
    public void delete(Long id) { repo.deleteById(id); }
}