package com.example.MallManagement.service;

import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.repository.StaffAssignmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepository repo;

    public StaffAssignmentService(StaffAssignmentRepository repo) { this.repo = repo; }

    public List<StaffAssignment> findAll() { return repo.findAll(); }
    public StaffAssignment findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(StaffAssignment assignment) { repo.save(assignment); }
    public void delete(Long id) { repo.deleteById(id); }
}