package com.example.MallManagement.repository;

import com.example.MallManagement.model.MaintenanceTask;
import org.springframework.stereotype.Repository;

@Repository
public class MaintenanceTaskRepository extends InFileRepository<MaintenanceTask> {
    public MaintenanceTaskRepository() {
        super("maintenance_tasks.json", MaintenanceTask.class);
    }
}