package com.example.MallManagement.service;

import com.example.MallManagement.model.MaintenanceTask;
import com.example.MallManagement.repository.MaintenanceTaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaintenanceTaskService {
    private final MaintenanceTaskRepository repo;

    public MaintenanceTaskService(MaintenanceTaskRepository repo) { this.repo = repo; }

    public List<MaintenanceTask> findAll() { return repo.findAll(); }
    public MaintenanceTask findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(MaintenanceTask task) { repo.save(task); }
    public void delete(Long id) { repo.deleteById(id); }
}