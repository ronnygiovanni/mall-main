package com.example.MallManagement.model;

// 1. REMOVED: import java.awt.*;
// 2. ADDED: Imports for java.util List and ArrayList
import java.util.List;
import java.util.ArrayList;
// (We can add StaffAssignment to make the list type-safe, but let's keep it simple)

public class MaintenanceStaff extends Staff implements Identifiable{

    // 3. CHANGED: Made the List type safe (optional but good practice)
    private List assignments;
    private Type type;

    public enum Type{
        Electrical, Cleaning
    };


    public MaintenanceStaff(String id, String name, Type type, int salary) {
        super(id, name, salary);
        this.type = type;

        // 4. THIS IS THE MAIN FIX:
        //    Changed "new List()" (the GUI component)
        //    to "new ArrayList<>()" (the data collection)
        this.assignments = new ArrayList<>();
    }

    // 5. CHANGED: Updated getter/setter to match the List type
    public List getAssignments() {
        return assignments;
    }

    public void setAssignments(List assignments) {
        this.assignments = assignments;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}