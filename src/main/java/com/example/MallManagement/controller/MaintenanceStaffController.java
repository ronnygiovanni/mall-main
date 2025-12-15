package com.example.MallManagement.controller;

import com.example.MallManagement.model.MaintenanceStaff;
import com.example.MallManagement.service.MaintenanceStaffService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/maintenance-staff")
public class MaintenanceStaffController {

    private final MaintenanceStaffService service;

    public MaintenanceStaffController(MaintenanceStaffService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(defaultValue = "") String name,
                       @RequestParam(required = false) MaintenanceStaff.Type type) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("staffList", service.findAll(name, type, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("name", name);
        model.addAttribute("type", type);
        model.addAttribute("types", MaintenanceStaff.Type.values());
        return "maintenance-staff/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("staff", new MaintenanceStaff());
        model.addAttribute("types", MaintenanceStaff.Type.values());
        return "maintenance-staff/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("staff", service.findById(id));
        model.addAttribute("types", MaintenanceStaff.Type.values());
        return "maintenance-staff/form";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("staff", service.findById(id));
        return "maintenance-staff/details";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("staff") MaintenanceStaff staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("types", MaintenanceStaff.Type.values());
            return "maintenance-staff/form";
        }
        service.save(staff);
        return "redirect:/maintenance-staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/maintenance-staff";
    }
}