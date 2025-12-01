package com.example.MallManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class MaintenanceTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    public enum Status { Planned, Active, Done }

    @Enumerated(EnumType.STRING)
    private Status status = Status.Planned;

    @NotNull(message = "Duration is required")
    private Integer duration; // in minutes

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    @NotNull(message = "Floor is required")
    private Floor floor;

    // Optional: A task might not be assigned yet
    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private StaffAssignment assignment;

    public MaintenanceTask() {}
    public MaintenanceTask(String description, Status status, int duration, Floor floor, StaffAssignment assignment) {
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.floor = floor;
        this.assignment = assignment;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
    public StaffAssignment getAssignment() { return assignment; }
    public void setAssignment(StaffAssignment assignment) { this.assignment = assignment; }
}