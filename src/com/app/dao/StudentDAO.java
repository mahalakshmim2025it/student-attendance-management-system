package com.app.dao;

import com.app.config.DatabaseConnection;
import com.app.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Fetch all students from the database
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.student_id, s.roll_number, s.name, s.email, s.semester, " +
                       "u.user_id, u.username, u.password, u.role " +
                       "FROM Students s JOIN Users u ON s.user_id = u.user_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getInt("student_id"),
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("semester")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}