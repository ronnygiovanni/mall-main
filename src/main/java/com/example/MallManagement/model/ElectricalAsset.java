package com.example.MallManagement.model;
public class ElectricalAsset implements Identifiable {
    private String id;
    private Type type;
    private Status status;
    private String floorId;       // Foreign Key
    private String assignmentId;  // Foreign Key

    public enum Type { Lift, AC, Light, Escalator };
    public enum Status { Working, Down };

    public ElectricalAsset() {}
    public ElectricalAsset(String id, String floorId, String assignmentId, Type type, Status status) {
        this.id = id; this.floorId = floorId; this.assignmentId = assignmentId;
        this.type = type; this.status = status;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFloorId() { return floorId; }
    public void setFloorId(String floorId) { this.floorId = floorId; }
    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}