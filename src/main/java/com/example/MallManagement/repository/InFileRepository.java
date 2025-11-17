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
    private long nextId = 1;

    public InFileRepository(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
        loadData();
        // Initialize nextId based on the highest existing ID
        this.nextId = data.stream()
                .map(Identifiable::getId)
                .filter(id -> id.matches("\\d+")) // Filter for numeric IDs
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L) + 1;
    }

    private void loadData() {
        try {
            File file = ResourceUtils.getFile("classpath:data/" + filePath);
            if (file.exists() && file.length() > 0) {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
                data = objectMapper.readValue(file, listType);
            }
        } catch (Exception e) {
            System.out.println("Data file " + filePath + " not found or empty. Starting new.");
            data = new ArrayList<>();
        }
    }

    private synchronized void saveData() {
        try {
            File file = ResourceUtils.getFile("classpath:data/" + filePath);
            // Ensure directory exists
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            System.err.println("Error saving data to " + filePath + ": " + e.getMessage());
        }
    }

    @Override
    public void save(T entity) {
        Optional<T> existing = data.stream()
                .filter(e -> e.getId() != null && e.getId().equals(entity.getId()))
                .findFirst();

        if (existing.isPresent()) {
            int index = data.indexOf(existing.get());
            data.set(index, entity);
        } else {
            if (entity.getId() == null || entity.getId().isEmpty()) {
                entity.setId(String.valueOf(nextId++));
            }
            data.add(entity);
        }
        saveData();
    }

    @Override
    public List<T> findAll() { return data; }

    @Override
    public T findById(String id) {
        return data.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void delete(String id) {
        data.removeIf(e -> e.getId().equals(id));
        saveData();
    }
}