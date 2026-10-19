import com.app.dao.AttendanceDAO;
import com.app.dao.StudentDAO;
import com.app.model.Student;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        int choice = 0;
        do {
            System.out.println("\n=== Student Attendance & Performance System ===");
            System.out.println("1. View All Students");
            System.out.println("2. View Attendance Summary Report");
            System.out.println("3. Mark Attendance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        List<Student> students = studentDAO.getAllStudents();
                        System.out.println("\n--- Registered Students ---");
                        for (Student s : students) {
                            s.displayInfo();
                        }
                        break;

                    case 2:
                        attendanceDAO.printAttendanceSummary();
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int sId = scanner.nextInt();
                        System.out.print("Enter Course ID: ");
                        int cId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter Status (PRESENT/ABSENT): ");
                        String status = scanner.nextLine().toUpperCase();

                        boolean success = attendanceDAO.markAttendance(sId, cId, date, status);
                        if (success) {
                            System.out.println("✅ Attendance recorded successfully!");
                        } else {
                            System.out.println("❌ Failed to record attendance. Check IDs and try again.");
                        }
                        break;

                    case 4:
                        System.out.println("Exiting system. Goodbye!");
                        break;

                    default:
                        System.out.println("⚠️ Invalid choice. Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("⚠️ Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid token
            }

        } while (choice != 4);

        scanner.close();
    }
}