package com.example.MallManagement.controller;

import com.example.MallManagement.model.SecurityStaff;
import com.example.MallManagement.model.Staff;
import com.example.MallManagement.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping
    public String getAllStaff(Model model,
                              @RequestParam(defaultValue = "id") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              @RequestParam(required = false) String name) {
        List<Staff> staff;
        if (name != null && !name.isEmpty()) {
            staff = staffService.findStaffByCriteria(name, sortBy, sortDir);
        } else {
            staff = staffService.getAllStaff(sortBy, sortDir);
        }
        model.addAttribute("staff", staff);
        model.addAttribute("sortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("name", name);
        return "staff/index";
    }

    @GetMapping("/new")
    public String showNewStaffForm(Model model) {
        model.addAttribute("staff", new SecurityStaff());
        return "staff/form";
    }

    @PostMapping
    public String saveStaff(@ModelAttribute Staff staff) {
        staffService.saveStaff(staff);
        return "redirect:/staff";
    }

    @GetMapping("/edit/{id}")
    public String showEditStaffForm(@PathVariable Long id, Model model) {
        model.addAttribute("staff", staffService.getStaffById(id));
        return "staff/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staff";
    }
}