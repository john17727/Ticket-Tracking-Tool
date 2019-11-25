package com.mock;

public class Ticket {

    private String id;

    private String title;
    private String client;
    private String status;
    private int priority;
    private String severity;
    private String assignedTo;
    private String description;
    private String solution;
    private long date;

    public Ticket(String title, String status, int priority, String severity, String assignedTo, String client, String description, String solution, long date) {
        id = "";
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.severity = severity;
        this.assignedTo = assignedTo;
        this.client = client;
        this.description = description;
        this.solution = solution;
        this.date = date;
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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void replaceSpaces() {
        this.title = title.replace(' ', '_');
        this.assignedTo = assignedTo.replace(' ', '_');
        this.client = client.replace(' ', '_');
        this.description = description.replace(' ', '_');
        this.solution = solution.replace(' ', '_');
    }

    public void replaceUnderscores() {
        this.title = title.replace('_', ' ');
        this.assignedTo = assignedTo.replace('_', ' ');
        this.client = client.replace('_', ' ');
        this.description = description.replace('_', ' ');
        this.solution = solution.replace('_', ' ');
    }
}
