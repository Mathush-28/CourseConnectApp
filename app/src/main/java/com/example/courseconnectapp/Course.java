package com.example.courseconnectapp;

public class Course {
    private String name;
    private String cost;
    private String duration;
    private String startDate;
    private String dueDate;
    private String branches;

    public Course(String name, String cost, String duration, String startDate, String dueDate, String branches) {
        this.name = name;
        this.cost = cost;
        this.duration = duration;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.branches = branches;
    }

    public String getName() { return name; }
    public String getCost() { return cost; }
    public String getDuration() { return duration; }
    public String getStartDate() { return startDate; }
    public String getDueDate() { return dueDate; }
    public String getBranches() { return branches; }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Cost: " + cost +
                ", Duration: " + duration +
                ", Start Date: " + startDate +
                ", Due Date: " + dueDate +
                ", Branches: " + branches;
    }
}