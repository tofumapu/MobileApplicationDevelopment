package com.example.a23520231_lab02_bai3;

public class Employee {
    private String id;
    private String name;
    public double tinhLuong() { return 0; }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

