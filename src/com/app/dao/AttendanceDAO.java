package com.app.dao;

import com.app.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDAO {

    // Record a daily attendance entry
    public boolean markAttendance(int studentId, int courseId, String date, String status) {
        String query = "INSERT INTO Attendance_Logs (student_id, course_id, log_date, status) VALUES (?, ?, ?, ?)";

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
    public void printAttendanceSummary() {
        String query = "SELECT * FROM Attendance_Summary";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n----------------- ATTENDANCE SUMMARY -----------------");
            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                String name = rs.getString("student_name");
                String course = rs.getString("course_code");
                long totalDays = rs.getLong("total_days");
                long daysPresent = rs.getLong("days_present");
                double percentage = rs.getDouble("attendance_percentage");

                // Check 75% attendance threshold
                String statusTag = "";
                if (percentage < 75.0) {
                    statusTag = " ** [LOW ATTENDANCE]";
                }

                System.out.printf("Student: %-15s | Course: %-8s | Classes: %d/%d | Percentage: %.2f%%%s\n",
                        name, course, daysPresent, totalDays, percentage, statusTag);
            }
            if (!hasRecords) {
                System.out.println("No attendance records found yet.");
            }
            System.out.println("------------------------------------------------------\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}