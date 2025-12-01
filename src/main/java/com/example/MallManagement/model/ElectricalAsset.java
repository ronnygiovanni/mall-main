package com.example.MallManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class ElectricalAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum Type { Lift, AC, Light, Escalator }
    public enum Status { Working, Down }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type is required")
    private Type type;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Working;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    @NotNull(message = "Floor is required")
    private Floor floor;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private StaffAssignment assignment;

    public ElectricalAsset() {}
    public ElectricalAsset(Type type, Status status, Floor floor, StaffAssignment assignment) {
        this.type = type;
        this.status = status;
        this.floor = floor;
        this.assignment = assignment;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Floor getFloor() { return floor; }
    public void setFloor(Floor floor) { this.floor = floor; }
    public StaffAssignment getAssignment() { return assignment; }
    public void setAssignment(StaffAssignment assignment) { this.assignment = assignment; }
}