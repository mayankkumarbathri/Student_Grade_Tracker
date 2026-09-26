import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CodeAlpha Java Programming Internship - Task 1
 * Student Grade Tracker
 *
 * Lets the user add students, record grades, and view per-student
 * and class-wide summary reports (average, highest, lowest).
 */
public class StudentGradeTracker {

    // ---------- Student model ----------
    static class Student {
        private final String name;
        private final List<Double> grades = new ArrayList<>();

        Student(String name) {
            this.name = name;
        }

        void addGrade(double grade) {
            grades.add(grade);
        }

        String getName() {
            return name;
        }

        List<Double> getGrades() {
            return grades;
        }

        double getAverage() {
            if (grades.isEmpty()) return 0.0;
            double sum = 0.0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        double getHighest() {
            if (grades.isEmpty()) return 0.0;
            double max = grades.get(0);
            for (double g : grades) if (g > max) max = g;
            return max;
        }

        double getLowest() {
            if (grades.isEmpty()) return 0.0;
            double min = grades.get(0);
            for (double g : grades) if (g < min) min = g;
            return min;
        }
    }

    // ---------- Application state ----------
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   CodeAlpha - Student Grade Tracker");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> addGrade();
                case "3" -> viewStudentReport();
                case "4" -> viewClassSummary();
                case "5" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add Student");
        System.out.println("2. Add Grade to Student");
        System.out.println("3. View Individual Student Report");
        System.out.println("4. View Class Summary Report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.\n");
            return;
        }
        if (findStudent(name) != null) {
            System.out.println("A student with that name already exists.\n");
            return;
        }
        students.add(new Student(name));
        System.out.println("Student \"" + name + "\" added.\n");
    }

    private static void addGrade() {
        if (students.isEmpty()) {
            System.out.println("No students yet. Add a student first.\n");
            return;
        }
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        Student s = findStudent(name);
        if (s == null) {
            System.out.println("Student not found.\n");
            return;
        }
        System.out.print("Enter grade (0-100): ");
        String input = scanner.nextLine().trim();
        try {
            double grade = Double.parseDouble(input);
            if (grade < 0 || grade > 100) {
                System.out.println("Grade must be between 0 and 100.\n");
                return;
            }
            s.addGrade(grade);
            System.out.println("Grade " + grade + " added for " + s.getName() + ".\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a numeric grade.\n");
        }
    }

    private static void viewStudentReport() {
        if (students.isEmpty()) {
            System.out.println("No students yet.\n");
            return;
        }
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        Student s = findStudent(name);
        if (s == null) {
            System.out.println("Student not found.\n");
            return;
        }
        System.out.println("\n--- Report for " + s.getName() + " ---");
        if (s.getGrades().isEmpty()) {
            System.out.println("No grades recorded yet.");
        } else {
            System.out.println("Grades: " + s.getGrades());
            System.out.printf("Average: %.2f%n", s.getAverage());
            System.out.printf("Highest: %.2f%n", s.getHighest());
            System.out.printf("Lowest:  %.2f%n", s.getLowest());
        }
        System.out.println();
    }

    private static void viewClassSummary() {
        if (students.isEmpty()) {
            System.out.println("No students yet.\n");
            return;
        }
        System.out.println("\n============ Class Summary Report ============");
        System.out.printf("%-20s %-10s %-10s %-10s %-8s%n", "Name", "Average", "Highest", "Lowest", "Count");
        System.out.println("------------------------------------------------");
        for (Student s : students) {
            if (s.getGrades().isEmpty()) {
                System.out.printf("%-20s %-10s %-10s %-10s %-8d%n", s.getName(), "-", "-", "-", 0);
            } else {
                System.out.printf("%-20s %-10.2f %-10.2f %-10.2f %-8d%n",
                        s.getName(), s.getAverage(), s.getHighest(), s.getLowest(), s.getGrades().size());
            }
        }
        System.out.println("================================================\n");
    }

    private static Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }
}
