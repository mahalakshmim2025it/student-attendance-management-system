package com.app.model;

public class Student extends User {
    private int studentId;
    private String rollNumber;
    private String name;
    private String email;
    private int semester;

    // Constructor
    public Student(int userId, String username, String password, String role, 
                   int studentId, String rollNumber, String name, String email, int semester) {
        super(userId, username, password, role); // Calls the parent User constructor
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.semester = semester;
    }

    // Getters
    public int getStudentId() { return studentId; }
    public String getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getSemester() { return semester; }

    public void displayInfo() {
        System.out.println("Student: " + name + " | Roll No: " + rollNumber + " | Semester: " + semester);
    }
}