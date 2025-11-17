package com.example.MallManagement.service;

import com.example.MallManagement.model.Identifiable;
import com.example.MallManagement.repository.RepositoryInterface;
import java.util.List;

public class Service<E extends Identifiable> implements ServiceInterface<E> {

    protected final RepositoryInterface<E> repository;

    public Service(RepositoryInterface<E> repository) {
        this.repository = repository;
    }

    @Override
    public void add(E entity) {
        repository.save(entity);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }
}