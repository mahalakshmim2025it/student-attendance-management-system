package com.app.dao;

import com.app.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<String> getAllCourseCodes() {
        List<String> courses = new ArrayList<>();
        String query = "SELECT course_code FROM Courses";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                courses.add(rs.getString("course_code"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
}