package com.example.MallManagement.service;

import com.example.MallManagement.model.SecurityStaff;
import com.example.MallManagement.repository.SecurityStaffRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SecurityStaffService {
    private final SecurityStaffRepository repo;

    public SecurityStaffService(SecurityStaffRepository repo) { this.repo = repo; }

    public List<SecurityStaff> findAll() { return repo.findAll(); }

    public List<SecurityStaff> findAll(String name, String badgeNo, Sort sort) {
        Specification<SecurityStaff> spec = Specification.where(null);

        if (StringUtils.hasText(name)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (StringUtils.hasText(badgeNo)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("badgeNo")), "%" + badgeNo.toLowerCase() + "%"));
        }

        return repo.findAll(spec, sort);
    }

    public SecurityStaff findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(SecurityStaff staff) { repo.save(staff); }
    public void delete(Long id) { repo.deleteById(id); }
}