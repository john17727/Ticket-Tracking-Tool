package com.mock;

public class Ticket {
    private String title;
    private String status;
    private int priority;
    private String severity;
    private String assignedTo;
    private String client;
    private String description;
    private long date;

    public Ticket(String title, String status, int priority, String severity, String assignedTo) {
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.severity = severity;
        this.assignedTo = assignedTo;
        this.client = "A Client";
        this.description = "Description";
        this.date = 0;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
