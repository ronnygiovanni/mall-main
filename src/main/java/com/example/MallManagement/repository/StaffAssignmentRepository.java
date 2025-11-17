package com.example.MallManagement.repository;

import com.example.MallManagement.model.StaffAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class StaffAssignmentRepository extends InFileRepository<StaffAssignment> {
    public StaffAssignmentRepository() {
        super("staff_assignments.json", StaffAssignment.class);
    }
}