package com.example.MallManagement.repository;

import com.example.MallManagement.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This Repository handles ALL staff (both Security and Maintenance)
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
}