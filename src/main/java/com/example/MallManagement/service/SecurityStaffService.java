package com.example.MallManagement.service;

import com.example.MallManagement.model.SecurityStaff;
import com.example.MallManagement.repository.SecurityStaffRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SecurityStaffService {
    private final SecurityStaffRepository repo;

    public SecurityStaffService(SecurityStaffRepository repo) { this.repo = repo; }

    public List<SecurityStaff> findAll() { return repo.findAll(); }
    public SecurityStaff findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(SecurityStaff staff) { repo.save(staff); }
    public void delete(Long id) { repo.deleteById(id); }
}