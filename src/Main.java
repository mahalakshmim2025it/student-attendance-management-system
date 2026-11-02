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
            System.out.println("3. Mark Attendance (Individual)");
            System.out.println("4. Enter Batch Attendance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

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
                        System.out.print("Enter Course ID to view summary (1: CS23333, 2: CS23332): ");
                        int summaryCourseId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        attendanceDAO.printAttendanceSummary(summaryCourseId);
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int sId = scanner.nextInt();
                        System.out.print("Enter Course ID: ");
                        int cId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter Status ([P]resent / [A]bsent): ");
                        String inputStatus = scanner.nextLine().trim().toUpperCase();

                        // Map short inputs to full ENUM values
                        String finalStatus = "";
                        if (inputStatus.equals("P") || inputStatus.equals("PRESENT")) {
                            finalStatus = "PRESENT";
                        } else if (inputStatus.equals("A") || inputStatus.equals("ABSENT")) {
                            finalStatus = "ABSENT";
                        } else {
                            System.out.println("❌ Invalid status. Please enter P or A.");
                            break;
                        }

                        boolean success = attendanceDAO.markAttendance(sId, cId, date, finalStatus);
                        if (success) {
                            System.out.println("✅ Attendance recorded successfully!");
                        } else {
                            System.out.println("❌ Failed to record attendance. Check IDs and try again.");
                        }
                        break;
                    
                    case 4:
                        System.out.println("\n--- Batch Attendance Mode ---");
                        System.out.print("Enter Course ID for this class (1: CS23333, 2: CS23332): ");
                        int batchCourseId = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String batchDate = scanner.nextLine();

                        // Fetch all students so we can loop through them
                        List<Student> allStudents = studentDAO.getAllStudents();
                        
                        if (allStudents.isEmpty()) {
                            System.out.println("No students found in the system!");
                            break;
                        }

                        System.out.println("\nMarking attendance. Enter [P]resent, [A]bsent, or [S]kip:");

                        for (Student student : allStudents) {
                            System.out.print("Student: " + student.getName() + " (" + student.getRollNumber() + ") [P/A/S]: ");
                            String input = scanner.nextLine().trim().toUpperCase();
                            
                            String status = ""; // Declared properly here
                            if (input.equals("P") || input.equals("PRESENT")) {
                                status = "PRESENT";
                            } else if (input.equals("A") || input.equals("ABSENT")) {
                                status = "ABSENT";
                            } else if (input.equals("S") || input.equals("SKIP")) {
                                System.out.println("  ↳ Skipped.");
                                continue; // Skip this student
                            } else {
                                System.out.println("⚠️ Invalid input. Skipping student.");
                                continue;
                            }

                            attendanceDAO.markAttendance(student.getStudentId(), batchCourseId, batchDate, status);
                        }
                        System.out.println("✅ Batch attendance recording completed!");
                        break;

                    case 5:
                        System.out.println("Exiting system. Goodbye!");
                        break;

                    default:
                        System.out.println("⚠️ Invalid choice. Please enter a number between 1 and 5.");
                }
            } else {
                System.out.println("⚠️ Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid token
            }

        } while (choice != 5);

        scanner.close();
    }
}
