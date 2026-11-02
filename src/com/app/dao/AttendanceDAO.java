package com.app.dao;

import com.app.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDAO {

    // Record a daily attendance entry
    public boolean markAttendance(int studentId, int courseId, String date, String status) {
        String query = "INSERT INTO Attendance_Logs (student_id, course_id, date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setDate(3, java.sql.Date.valueOf(date)); // Expects YYYY-MM-DD
            pstmt.setString(4, status); // 'PRESENT' or 'ABSENT'

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Display summary from Attendance_Summary view
    // Display summary from Attendance_Summary view with threshold warnings
    public void printAttendanceSummary(int courseId) {
    String query = "SELECT * FROM Attendance_Summary WHERE course_id = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, courseId);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\n==================================================");
        System.out.println("           ATTENDANCE SUMMARY REPORT             ");
        System.out.println("==================================================");
        System.out.printf("%-5s | %-18s | %-8s | %-6s | %-10s\n", "ID", "Student Name", "Present", "Total", "Percentage");
        System.out.println("--------------------------------------------------");

        boolean hasRecords = false;
        while (rs.next()) {
            hasRecords = true;
            int studentId = rs.getInt("student_id");
            String name = rs.getString("student_name");
            int daysPresent = rs.getInt("days_present");
            int totalDays = rs.getInt("total_days");
            double percentage = rs.getDouble("attendance_percentage");

            // Flag low attendance (< 75%)
            String warning = (percentage < 75.0) ? " ⚠️ [LOW]" : "";

            System.out.printf("%-5d | %-18s | %-8d | %-6d | %6.2f%%%s\n", 
                studentId, name, daysPresent, totalDays, percentage, warning);
        }

        if (!hasRecords) {
            System.out.println("No attendance logs found for this course ID.");
        }
        System.out.println("==================================================");

    } catch (SQLException e) {
        System.out.println("❌ Error fetching attendance summary: " + e.getMessage());
    }
}
}