package com.example.MallManagement.service;

import com.example.MallManagement.model.MaintenanceTask;
import com.example.MallManagement.repository.MaintenanceTaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MaintenanceTaskService {
    private final MaintenanceTaskRepository repo;

    public MaintenanceTaskService(MaintenanceTaskRepository repo) { this.repo = repo; }

    public List<MaintenanceTask> findAll() { return repo.findAll(); }

    public List<MaintenanceTask> findAll(String description, MaintenanceTask.Status status, Long floorId, Long assignmentId, Sort sort) {
        Specification<MaintenanceTask> spec = Specification.where(null);

        if (StringUtils.hasText(description)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if (floorId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("floor").get("id"), floorId));
        }

        if (assignmentId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("assignment").get("id"), assignmentId));
        }

        return repo.findAll(spec, sort);
    }

    public MaintenanceTask findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(MaintenanceTask task) { repo.save(task); }
    public void delete(Long id) { repo.deleteById(id); }
}