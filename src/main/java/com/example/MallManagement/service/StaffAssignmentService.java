package com.example.MallManagement.service;

import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.repository.StaffAssignmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffAssignmentService {
    private final StaffAssignmentRepository repo;

    public StaffAssignmentService(StaffAssignmentRepository repo) { this.repo = repo; }

    public List<StaffAssignment> findAll() { return repo.findAll(); }

    public List<StaffAssignment> findAll(StaffAssignment.Shift shift, Long floorId, Long staffId, Sort sort) {
        Specification<StaffAssignment> spec = Specification.where(null);

        if (shift != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("shift"), shift));
        }

        if (floorId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("floor").get("id"), floorId));
        }

        if (staffId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("staff").get("id"), staffId));
        }

        return repo.findAll(spec, sort);
    }

    public StaffAssignment findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(StaffAssignment assignment) { repo.save(assignment); }
    public void delete(Long id) { repo.deleteById(id); }
}