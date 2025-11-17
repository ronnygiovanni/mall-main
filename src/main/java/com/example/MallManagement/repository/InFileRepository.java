package com.example.MallManagement.repository;

import com.example.MallManagement.model.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class InFileRepository<T extends Identifiable> implements RepositoryInterface<T> {

    private final String filePath;
    private final Class<T> type;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<T> data = new ArrayList<>();

    public InFileRepository(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
        loadData();
    }

    private void loadData() {
        try {
            File file = ResourceUtils.getFile("classpath:data/" + filePath);
            if (file.exists()) {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
                data = objectMapper.readValue(file, listType);
            }
        } catch (IOException e) {
            System.out.println("Could not load data from " + filePath + ": " + e.getMessage());
            data = new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            File file = ResourceUtils.getFile("classpath:data/" + filePath);
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(T entity) {
        // Update if exists, otherwise add
        Optional<T> existing = data.stream().filter(e -> e.getId().equals(entity.getId())).findFirst();
        if (existing.isPresent()) {
            int index = data.indexOf(existing.get());
            data.set(index, entity);
        } else {
            // Simple ID generation if missing
            if (entity.getId() == null || entity.getId().isEmpty()) {
                entity.setId(String.valueOf(System.currentTimeMillis()));
            }
            data.add(entity);
        }
        saveData(); // Auto-save to JSON [cite: 53]
    }

    @Override
    public List<T> findAll() {
        return data;
    }

    @Override
    public T findById(String id) {
        return data.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void delete(String id) {
        data.removeIf(e -> e.getId().equals(id));
        saveData(); // Auto-save to JSON [cite: 53]
    }
}