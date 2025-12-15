package com.example.MallManagement.service;

import com.example.MallManagement.model.MaintenanceStaff;
import com.example.MallManagement.repository.MaintenanceStaffRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MaintenanceStaffService {
    private final MaintenanceStaffRepository repo;

    public MaintenanceStaffService(MaintenanceStaffRepository repo) { this.repo = repo; }

    public List<MaintenanceStaff> findAll() { return repo.findAll(); }

    public List<MaintenanceStaff> findAll(String name, MaintenanceStaff.Type type, Sort sort) {
        Specification<MaintenanceStaff> spec = Specification.where(null);

        if (StringUtils.hasText(name)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (type != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("type"), type));
        }

        return repo.findAll(spec, sort);
    }

    public MaintenanceStaff findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(MaintenanceStaff staff) { repo.save(staff); }
    public void delete(Long id) { repo.deleteById(id); }
}