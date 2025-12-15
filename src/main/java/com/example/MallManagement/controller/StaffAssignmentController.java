package com.example.MallManagement.controller;

import com.example.MallManagement.model.Floor;
import com.example.MallManagement.model.Staff;
import com.example.MallManagement.model.StaffAssignment;
import com.example.MallManagement.repository.StaffRepository;
import com.example.MallManagement.service.FloorService;
import com.example.MallManagement.service.StaffAssignmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff-assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService service;
    private final FloorService floorService;
    private final StaffRepository staffRepository;

    public StaffAssignmentController(StaffAssignmentService service, FloorService floorService, StaffRepository staffRepository) {
        this.service = service;
        this.floorService = floorService;
        this.staffRepository = staffRepository;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       @RequestParam(required = false) StaffAssignment.Shift shift,
                       @RequestParam(required = false) Long floorId,
                       @RequestParam(required = false) Long staffId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        model.addAttribute("assignments", service.findAll(shift, floorId, staffId, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("shift", shift);
        model.addAttribute("floorId", floorId);
        model.addAttribute("staffId", staffId);
        model.addAttribute("shifts", StaffAssignment.Shift.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("staffList", staffRepository.findAll());
        return "staff-assignment/index";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.findById(id));
        return "staff-assignment/details";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("assignment", new StaffAssignment());
        model.addAttribute("shifts", StaffAssignment.Shift.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("staffList", staffRepository.findAll());
        return "staff-assignment/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("assignment", service.findById(id));
        model.addAttribute("shifts", StaffAssignment.Shift.values());
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("staffList", staffRepository.findAll());
        return "staff-assignment/form";
    }

    // FIX: Added ("assignment") to @ModelAttribute so it matches the HTML
    @PostMapping
    public String save(@Valid @ModelAttribute("assignment") StaffAssignment sa, BindingResult result,
                       @RequestParam("floorId") Long floorId,
                       @RequestParam("staffId") Long staffId, Model model) {

        model.addAttribute("shifts", StaffAssignment.Shift.values()); // Reload dropdowns
        model.addAttribute("floors", floorService.findAll());
        model.addAttribute("staffList", staffRepository.findAll());

        if (result.hasErrors()) return "staff-assignment/form";

        Floor floor = floorService.findById(floorId);
        if (floor == null) {
            model.addAttribute("error", "Floor ID " + floorId + " not found.");
            return "staff-assignment/form";
        }
        sa.setFloor(floor);

        Staff staff = staffRepository.findById(staffId).orElse(null);
        if (staff == null) {
            model.addAttribute("error", "Staff ID " + staffId + " not found.");
            return "staff-assignment/form";
        }
        sa.setStaff(staff);

        service.save(sa);
        return "redirect:/staff-assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/staff-assignments";
    }
}