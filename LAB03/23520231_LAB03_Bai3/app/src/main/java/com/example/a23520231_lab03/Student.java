package com.example.a23520231_lab03;

public class Student {
    private int id;
    private String name;
    private String studentClass;

    public Student() {}
    public Student(String name, String studentClass) {
        this.name = name;
        this.studentClass = studentClass;
    }

    // Getter - Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

}
