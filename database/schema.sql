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
    COUNT(CASE WHEN a.status = 'PRESENT' OR a.status = 'EXCUSED' THEN 1 END) AS days_present,
    COUNT(*) AS total_days,
    ROUND((COUNT(CASE WHEN a.status = 'PRESENT' OR a.status = 'EXCUSED' THEN 1 END) * 100.0 / COUNT(*)), 2) AS attendance_percentage
FROM Attendance_Logs a
JOIN Students s ON a.student_id = s.student_id
JOIN Courses c ON a.course_id = c.course_id
GROUP BY a.student_id, s.name, c.course_id, c.course_code;

DESCRIBE Attendance_Summary;

INSERT INTO Courses (course_code, course_name) VALUES 
('CS23333', 'OOP using Java'),
('CS23332', 'Database Management Systems');

INSERT INTO Users (username, password, role) VALUES 
('rahul_s', 'stu123', 'STUDENT'),
('priya_r', 'stu123', 'STUDENT'),
('arun_k', 'stu123', 'STUDENT'),
('sneha_g', 'stu123', 'STUDENT'),
('vikram_p', 'stu123', 'STUDENT'),
('ananya_i', 'stu123', 'STUDENT'),
('karthik_r', 'stu123', 'STUDENT'),
('divya_n', 'stu123', 'STUDENT'),
('manoj_k', 'stu123', 'STUDENT'),
('pooja_h', 'stu123', 'STUDENT'),
('sanjay_v', 'stu123', 'STUDENT'),
('neha_s', 'stu123', 'STUDENT'),
('rohit_d', 'stu123', 'STUDENT'),
('swathi_m', 'stu123', 'STUDENT'),
('kiran_ra', 'stu123', 'STUDENT');

INSERT INTO Students (user_id, roll_number, name, email, semester) VALUES 
(1,  '2026-CS-01', 'Rahul Sharma', 'rahul@univ.edu', 3),
(2,  '2026-CS-02', 'Priya Ramesh', 'priya@univ.edu', 3),
(3,  '2026-CS-03', 'Arun Kumar', 'arun@univ.edu', 3),
(4,  '2026-CS-04', 'Sneha Gupta', 'sneha@univ.edu', 3),
(5,  '2026-CS-05', 'Vikram Patel', 'vikram@univ.edu', 3),
(6,  '2026-CS-06', 'Ananya Iyer', 'ananya@univ.edu', 3),
(7,  '2026-CS-07', 'Karthik Raja', 'karthik@univ.edu', 3),
(8,  '2026-CS-08', 'Divya Nair', 'divya@univ.edu', 3),
(9,  '2026-CS-09', 'Manoj Kumar', 'manoj@univ.edu', 3),
(10, '2026-CS-10', 'Pooja Hegde', 'pooja@univ.edu', 3),
(11, '2026-CS-11', 'Sanjay Verma', 'sanjay@univ.edu', 3),
(12, '2026-CS-12', 'Neha Singh', 'neha@univ.edu', 3),
(13, '2026-CS-13', 'Rohit Das', 'rohit@univ.edu', 3),
(14, '2026-CS-14', 'Swathi Menon', 'swathi@univ.edu', 3),
(15, '2026-CS-15', 'Kiran Rao', 'kiran@univ.edu', 3);