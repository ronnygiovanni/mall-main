package com.example.MallManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class StaffAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Shift { Morning, Evening, Night }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Shift is required")
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    // REMOVED @NotNull here because we set it manually in the Controller
    private Floor floor;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    // REMOVED @NotNull here
    private Staff staff;

    public StaffAssignment() {}
    public StaffAssignment(Floor floor, Staff staff, Shift shift) {
        this.floor = floor;
        this.staff = staff;
        this.shift = shift;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }
    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }
}