package com.example.MallManagement.model;
import java.util.ArrayList; // Correct Import!
import java.util.List;

public class MaintenanceStaff extends Staff implements Identifiable {
    private List<String> assignments;
    private Type type;
    public enum Type { Electrical, Cleaning };

    public MaintenanceStaff() { super(null,null,0); this.assignments = new ArrayList<>(); }
    public MaintenanceStaff(String id, String name, Type type, int salary) {
        super(id, name, salary);
        this.type = type;
        this.assignments = new ArrayList<>();
    }
    public List<String> getAssignments() { return assignments; }
    public void setAssignments(List<String> assignments) { this.assignments = assignments; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
}