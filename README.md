# Student Attendance & Performance Management System

A modular, object-oriented Java and MySQL desktop application designed to manage student records, track daily course attendance, and generate automated performance summary reports.

---

## 🛠️ Features

- **Object-Oriented Architecture:** Designed using clean inheritance with a base `User` class and `Student` subclass.
- **DAO Design Pattern:** Decouples database operations from business logic using dedicated Data Access Objects (`StudentDAO`, `CourseDAO`, `AttendanceDAO`).
- **Real-Time Summary Views:** Leverages MySQL dynamic views (`Attendance_Summary`) to calculate presence counts and aggregate percentages on the fly.
- **Automated Threshold Warnings:** Automatically flags students falling below the **75% minimum attendance requirement** with warning indicators.
- **Interactive CLI:** Terminal-driven menu allowing faculty to view students, inspect summary reports, and log daily attendance seamlessly.

---

## 🚀 Tech Stack

- **Language:** Java (JDK 21)
- **Database:** MySQL
- **Connector:** JDBC (`mysql-connector-j-9.x.x.jar`)
- **IDE:** Visual Studio Code

---

## 🗄️ Database Setup

1. Open **MySQL Workbench** or your preferred SQL client.
2. Execute the `schema.sql` script to create the database schema, tables (`Users`, `Students`, `Courses`, `Attendance_Logs`), and the `Attendance_Summary` view.
3. Update your database credentials in `src/com/app/config/DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
   private static final String USER = "your_mysql_username";
   private static final String PASSWORD = "your_mysql_password";
