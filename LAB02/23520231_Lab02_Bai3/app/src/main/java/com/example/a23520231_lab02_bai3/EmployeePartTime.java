package com.example.a23520231_lab02_bai3;

public class EmployeePartTime extends Employee {
    @Override
    public double tinhLuong() { return 150; }
    @Override
    public String toString() {
        return super.toString() + " --> PartTime: " + tinhLuong();
    }
}
