package com.example.MallManagement.service;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.repository.FloorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorService {
    private final FloorRepository repo;

    public FloorService(FloorRepository repo) { this.repo = repo; }

    public List<Floor> findAll() { return repo.findAll(); }

    public List<Floor> findAll(Integer number, Long mallId, Sort sort) {
        Specification<Floor> spec = Specification.where(null);

        if (number != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("number"), number));
        }

        if (mallId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("mall").get("id"), mallId));
        }

        return repo.findAll(spec, sort);
    }

    public Floor findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Floor floor) { repo.save(floor); }
    public void delete(Long id) { repo.deleteById(id); }
}