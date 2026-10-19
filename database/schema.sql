CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- 1. Users Table (Authentication)
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('STUDENT', 'INSTRUCTOR', 'ADMIN') NOT NULL
);

-- 2. Students Table
CREATE TABLE IF NOT EXISTS Students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    roll_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    semester INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- 3. Courses Table
CREATE TABLE IF NOT EXISTS Courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(15) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    min_attendance_pct DECIMAL(5, 2) DEFAULT 75.00
);

-- 4. Attendance Logs Table
CREATE TABLE IF NOT EXISTS Attendance_Logs (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'EXCUSED') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE,
    UNIQUE KEY unique_daily_entry (student_id, course_id, date)
);

-- 5. SQL View for Real-time Attendance Percentage
CREATE OR REPLACE VIEW Attendance_Summary AS
SELECT 
    a.student_id,
    s.name AS student_name,
    c.course_id,
    c.course_code,
    COUNT(CASE WHEN a.status = 'PRESENT' THEN 1 END) AS days_present,
    COUNT(*) AS total_days,
    ROUND((COUNT(CASE WHEN a.status = 'PRESENT' THEN 1 END) * 100.0 / COUNT(*)), 2) AS attendance_percentage
FROM Attendance_Logs a
JOIN Students s ON a.student_id = s.student_id
JOIN Courses c ON a.course_id = c.course_id
GROUP BY a.student_id, s.name, c.course_id, c.course_code;

-- Insert Test Data
INSERT INTO Users (username, password, role) VALUES 
('rahul101', 'stu123', 'STUDENT');

INSERT INTO Students (user_id, roll_number, name, email, semester) VALUES 
(1, '2026-CS-01', 'Rahul Sharma', 'rahul@univ.edu', 5);

INSERT INTO Courses (course_code, course_name) VALUES 
('CS301', 'Database Management Systems');

INSERT INTO Attendance_Logs (student_id, course_id, date, status) VALUES 
(1, 1, '2026-10-01', 'PRESENT'),
(1, 1, '2026-10-02', 'PRESENT'),
(1, 1, '2026-10-03', 'ABSENT');