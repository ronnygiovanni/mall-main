package com.example.MallManagement.repository;

import com.example.MallManagement.model.SecurityStaff;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityStaffRepository extends InFileRepository<SecurityStaff> {
    public SecurityStaffRepository() {
        super("security_staff.json", SecurityStaff.class);
    }
}