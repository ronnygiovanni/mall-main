package com.example.MallManagement.service;

import com.example.MallManagement.model.ElectricalAsset;
import com.example.MallManagement.repository.ElectricalAssetRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectricalAssetService {
    private final ElectricalAssetRepository repo;

    public ElectricalAssetService(ElectricalAssetRepository repo) { this.repo = repo; }

    public List<ElectricalAsset> findAll() { return repo.findAll(); }

    public List<ElectricalAsset> findAll(ElectricalAsset.Type type, ElectricalAsset.Status status, Long floorId, Long assignmentId, Sort sort) {
        Specification<ElectricalAsset> spec = Specification.where(null);

        if (type != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("type"), type));
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

    public ElectricalAsset findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(ElectricalAsset asset) { repo.save(asset); }
    public void delete(Long id) { repo.deleteById(id); }
}