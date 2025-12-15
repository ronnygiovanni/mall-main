package com.example.MallManagement.service;

import com.example.MallManagement.model.Mall;
import com.example.MallManagement.repository.MallRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MallService {
    private final MallRepository repo;

    public MallService(MallRepository repo) { this.repo = repo; }

    public List<Mall> findAll() { return repo.findAll(); }

    public List<Mall> findAll(String name, String city, Sort sort) {
        Specification<Mall> spec = Specification.where(null);

        if (StringUtils.hasText(name)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (StringUtils.hasText(city)) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
        }

        return repo.findAll(spec, sort);
    }

    public Mall findById(Long id) { return repo.findById(id).orElse(null); }
    public void save(Mall mall) { repo.save(mall); }
    public void delete(Long id) { repo.deleteById(id); }
}