package com.example.MallManagement.repository;

import com.example.MallManagement.model.MaintenanceStaff;
import org.springframework.stereotype.Repository;

@Repository
public class MaintenanceStaffRepository extends InFileRepository<MaintenanceStaff> {
    public MaintenanceStaffRepository() {
        super("maintenance_staff.json", MaintenanceStaff.class);
    }
}