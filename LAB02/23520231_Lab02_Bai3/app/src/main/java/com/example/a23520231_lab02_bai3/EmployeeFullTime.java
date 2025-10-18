package com.example.a23520231_lab02_bai3;

public class EmployeeFullTime extends Employee {
    @Override
    public double tinhLuong() { return 500; }
    @Override
    public String toString() {
        return super.toString() + " --> FullTime: " + tinhLuong();
    }
}
