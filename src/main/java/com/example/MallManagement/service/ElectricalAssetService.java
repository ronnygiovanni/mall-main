package com.example.MallManagement.service;

import com.example.MallManagement.model.ElectricalAsset;
import com.example.MallManagement.repository.ElectricalAssetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ElectricalAssetService {
    private final ElectricalAssetRepository repo;

    public ElectricalAssetService(ElectricalAssetRepository repo) { this.repo = repo; }

    public List<ElectricalAsset> findAll() { return repo.findAll(); }
    public ElectricalAsset findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(ElectricalAsset asset) { repo.save(asset); }
    public void delete(Long id) { repo.deleteById(id); }
}