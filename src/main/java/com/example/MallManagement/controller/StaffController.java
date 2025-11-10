package com.example.MallManagement.controller;

import com.example.MallManagement.model.MaintenanceStaff;
import com.example.MallManagement.model.SecurityStaff;
import com.example.MallManagement.model.Staff;
import com.example.MallManagement.service.MaintenanceStaffService;
import com.example.MallManagement.service.SecurityStaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final SecurityStaffService securityService;
    private final MaintenanceStaffService maintenanceService;

    public StaffController(SecurityStaffService securityService, MaintenanceStaffService maintenanceService) {
        this.securityService = securityService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public String listStaff(Model model) {
        List<Staff> allStaff = new ArrayList<>();
        allStaff.addAll(securityService.findAll());
        allStaff.addAll(maintenanceService.findAll());
        model.addAttribute("staffList", allStaff);
        return "staff/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new SecurityStaff("", "", "", 0));
        return "staff/form";
    }

    @PostMapping
    public String createStaff(@RequestParam String name,
                              @RequestParam int salary,
                              @RequestParam String staffType) {

        if ("Maintenance".equals(staffType)) {
            // Create a concrete MaintenanceStaff object
            MaintenanceStaff mStaff = new MaintenanceStaff(null, name, MaintenanceStaff.Type.Electrical, salary);
            maintenanceService.add(mStaff);
        } else {
            // Create a concrete SecurityStaff object (badgeNo is blank for now)
            SecurityStaff sStaff = new SecurityStaff(null, name, "", salary);
            securityService.add(sStaff);
        }
        return "redirect:/staff";
    }

    @PostMapping("/{id}/delete")
    public String deleteStaff(@RequestParam String staffType, @PathVariable String id) {
        if ("Maintenance".equals(staffType)) {
            maintenanceService.delete(id);
        } else {
            securityService.delete(id);
        }
        return "redirect:/staff";
    }
}
