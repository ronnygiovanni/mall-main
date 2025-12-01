package com.example.MallManagement.service;

import com.example.MallManagement.model.MaintenanceStaff;
import com.example.MallManagement.repository.MaintenanceStaffRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaintenanceStaffService {
    private final MaintenanceStaffRepository repo;

    public MaintenanceStaffService(MaintenanceStaffRepository repo) { this.repo = repo; }

    public List<MaintenanceStaff> findAll() { return repo.findAll(); }
    public MaintenanceStaff findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(MaintenanceStaff staff) { repo.save(staff); }
    public void delete(Long id) { repo.deleteById(id); }
}