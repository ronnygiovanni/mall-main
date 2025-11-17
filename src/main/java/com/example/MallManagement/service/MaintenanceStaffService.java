package com.example.MallManagement.service;

import com.example.MallManagement.model.MaintenanceStaff;
import com.example.MallManagement.repository.MaintenanceStaffRepository;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceStaffService extends com.example.MallManagement.service.Service<MaintenanceStaff> {
    public MaintenanceStaffService(MaintenanceStaffRepository repo) {
        super(repo);
    }
}