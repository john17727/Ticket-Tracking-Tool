package com.mock;

public class Ticket {
    private String title;
    private String status;
    private int priority;
    private String severity;
    private String assignedTo;

    public Ticket(String title, String status, int priority, String severity, String assignedTo) {
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.severity = severity;
        this.assignedTo = assignedTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
