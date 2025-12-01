package com.example.MallManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Entity
public class MaintenanceStaff extends Staff {

    public enum Type { Electrical, Cleaning }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Type is required")
    private Type type;

    public MaintenanceStaff() {}
    public MaintenanceStaff(String name, int salary, Type type) {
        super(name, salary);
        this.type = type;
    }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}