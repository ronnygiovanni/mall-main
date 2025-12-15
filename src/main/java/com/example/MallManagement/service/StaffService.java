package com.example.MallManagement.service;

import com.example.MallManagement.model.Staff;
import com.example.MallManagement.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return staffRepository.findAll(sort);
    }

    public List<Staff> findStaffByCriteria(String name, String sortBy, String sortDir) {
        if (name != null && !name.isEmpty()) {
            return staffRepository.findByNameContainingIgnoreCase(name);
        } else {
            return getAllStaff(sortBy, sortDir);
        }
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    public void saveStaff(Staff staff) {
        staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}