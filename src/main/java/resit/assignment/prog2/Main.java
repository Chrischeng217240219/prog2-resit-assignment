package resit.assignment.prog2;

import java.io.*;
import java.util.*;

// Student类
class Student {
    private String studentID;
    private String studentName;
    private int age;
    private char gender;
    private int grade;
    private String yearOfAdmission;
    private String yearOfGraduation;

    public Student(String studentID, String studentName, int age, char gender, int grade, 
                  String yearOfAdmission, String yearOfGraduation) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
        this.yearOfAdmission = yearOfAdmission;
        this.yearOfGraduation = yearOfGraduation;
    }

    // Getters and Setters
    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public char getGender() { return gender; }
    public void setGender(char gender) { this.gender = gender; }
    
    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }
    
    public String getYearOfAdmission() { return yearOfAdmission; }
    public void setYearOfAdmission(String yearOfAdmission) { this.yearOfAdmission = yearOfAdmission; }
    
    public String getYearOfGraduation() { return yearOfGraduation; }
    public void setYearOfGraduation(String yearOfGraduation) { this.yearOfGraduation = yearOfGraduation; }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", grade=" + grade +
                ", yearOfAdmission='" + yearOfAdmission + '\'' +
                ", yearOfGraduation='" + yearOfGraduation + '\'' +
                '}';
    }
}

class StudentCollection {
    private ArrayList<Student> students;

    public StudentCollection() {
        this.students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudents(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    String studentID = data[0].trim();
                    String studentName = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    char gender = data[3].trim().charAt(0);
                    int grade = Integer.parseInt(data[4].trim());
                    String yearOfAdmission = data[5].trim();
                    String yearOfGraduation = data[6].trim();
                    
                    students.add(new Student(studentID, studentName, age, gender, grade, 
                                            yearOfAdmission, yearOfGraduation));
                }
            }
            System.out.println("Students imported successfully from " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        if (students.isEmpty()) {
            return "No students in the collection.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-20s %-5s %-6s %-5s %-10s %-10s%n", 
                "ID", "Name", "Age", "Gender", "Grade", "Admission", "Graduation"));
        sb.append("--------------------------------------------------------------------------------\n");
        
        for (Student student : students) {
            sb.append(String.format("%-10s %-20s %-5d %-6c %-5d %-10s %-10s%n",
                    student.getStudentID(),
                    student.getStudentName(),
                    student.getAge(),
                    student.getGender(),
                    student.getGrade(),
                    student.getYearOfAdmission(),
                    student.getYearOfGraduation()));
        }
        return sb.toString();
    }
}

class StudentOperation {
    private StudentCollection studentCollection;
    private Scanner scanner;

    public StudentOperation(StudentCollection studentCollection, Scanner scanner) {
        this.studentCollection = studentCollection;
        this.scanner = scanner;
    }

    public void addStudent() {
        System.out.println("Enter student ID:");
        String studentID = scanner.nextLine();
        System.out.println("Enter student name:");
        String studentName = scanner.nextLine();
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        System.out.println("Enter gender (M/F):");
        char gender = scanner.next().charAt(0);
        System.out.println("Enter grade (1-5):");
        int grade = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter year of admission:");
        String yearOfAdmission = scanner.nextLine();
        System.out.println("Enter year of graduation:");
        String yearOfGraduation = scanner.nextLine();

        studentCollection.getStudents().add(
            new Student(studentID, studentName, age, gender, grade, yearOfAdmission, yearOfGraduation)
        );
        System.out.println("Student added successfully.");
    }

    public void removeStudent() {
        System.out.println("Enter student ID to remove:");
        String idToRemove = scanner.nextLine();
        
        ArrayList<Student> students = studentCollection.getStudents();
        boolean found = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID().equals(idToRemove)) {
                students.remove(i);
                System.out.println("Student removed successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("This ID does not exist.");
        }
    }

    public void updateStudentDetails() {
        System.out.println("Enter student ID to update:");
        String idToUpdate = scanner.nextLine();
        
        ArrayList<Student> students = studentCollection.getStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getStudentID().equals(idToUpdate)) {
                found = true;
                System.out.println("Enter new name (or press enter to skip):");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    student.setStudentName(newName);
                }

                System.out.println("Enter new age (or 0 to skip):");
                int newAge = scanner.nextInt();
                if (newAge != 0) {
                    student.setAge(newAge);
                }

                System.out.println("Enter new gender (M/F, or press enter to skip):");
                scanner.nextLine(); // Consume newline
                String genderInput = scanner.nextLine();
                if (!genderInput.isEmpty()) {
                    student.setGender(genderInput.charAt(0));
                }

                System.out.println("Enter new grade (1-5, or 0 to skip):");
                int newGrade = scanner.nextInt();
                if (newGrade != 0) {
                    student.setGrade(newGrade);
                }
                scanner.nextLine(); // Consume newline

                System.out.println("Enter new year of admission (or press enter to skip):");
                String newAdmission = scanner.nextLine();
                if (!newAdmission.isEmpty()) {
                    student.setYearOfAdmission(newAdmission);
                }

                System.out.println("Enter new year of graduation (or press enter to skip):");
                String newGraduation = scanner.nextLine();
                if (!newGraduation.isEmpty()) {
                    student.setYearOfGraduation(newGraduation);
                }

                System.out.println("Student details updated successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("This ID does not exist.");
        }
    }

    public void searchStudentByID() {
        System.out.println("Enter student ID to search:");
        String idToSearch = scanner.nextLine();
        
        ArrayList<Student> students = studentCollection.getStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getStudentID().equals(idToSearch)) {
                System.out.println("Student found:");
                System.out.println(String.format("%-10s %-20s %-5s %-6s %-5s %-10s %-10s", 
                        "ID", "Name", "Age", "Gender", "Grade", "Admission", "Graduation"));
                System.out.println("----------------------------------------------------------------------");
                System.out.println(String.format("%-10s %-20s %-5d %-6c %-5d %-10s %-10s",
                        student.getStudentID(),
                        student.getStudentName(),
                        student.getAge(),
                        student.getGender(),
                        student.getGrade(),
                        student.getYearOfAdmission(),
                        student.getYearOfGraduation()));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("This ID does not exist.");
        }
    }

    public void printGraduateStudents() {
        Graduates graduates = new Graduates();
        graduates.printGraduates(studentCollection.getStudents());
    }
}

// Graduates类
class Graduates {
    public void printGraduates(ArrayList<Student> students) {
        try (FileWriter fw = new FileWriter("GraduateStudents.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write("Following students have graduated so far.\n");
            bw.write("| Student ID | Student Name    | Age   | Gender | Year of Admission | Year of Graduation |\n");
            bw.write("|------------|-----------------|-------|--------|-------------------|--------------------|\n");
            
            for (Student student : students) {
                // 检查年份是否有效且小于等于当前年份
                try {
                    int gradYear = Integer.parseInt(student.getYearOfGraduation());
                    if (gradYear <= 2024) { // 假设当前年份是2024
                        bw.write(String.format("| %-10s | %-15s | %-5d | %-6c | %-17s | %-18s |\n",
                                student.getStudentID(),
                                student.getStudentName(),
                                student.getAge(),
                                student.getGender(),
                                student.getYearOfAdmission(),
                                student.getYearOfGraduation()));
                    }
                } catch (NumberFormatException e) {
                    // 如果年份格式无效，跳过该学生
                    System.out.println("Invalid graduation year for student: " + student.getStudentID());
                }
            }
            System.out.println("Graduate students details written to GraduateStudents.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

// Main类
public class Main {
    public static void main(String[] args) {
        StudentCollection studentCollection = new StudentCollection();
        // 检查文件是否存在
        File file = new File("StudentDetails.csv");
        if (file.exists()) {
            studentCollection.addStudents("StudentDetails.csv");
        } else {
            System.out.println("Warning: StudentDetails.csv not found. Starting with empty student list.");
        }
        
        Scanner scanner = new Scanner(System.in);
        StudentOperation studentOperation = new StudentOperation(studentCollection, scanner);
        
        int choice;
        do {
            System.out.println("\nOptions:");
            System.out.println("Type 1 to view all the students' details.");
            System.out.println("Type 2 to add new student to the list.");
            System.out.println("Type 3 to search student details.");
            System.out.println("Type 4 to update a student's details.");
            System.out.println("Type 5 to remove a student.");
            System.out.println("Type 6 to print graduate students.");
            System.out.println("Type 7 to QUIT.");
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.println(studentCollection);
                        break;
                    case 2:
                        studentOperation.addStudent();
                        break;
                    case 3:
                        studentOperation.searchStudentByID();
                        break;
                    case 4:
                        studentOperation.updateStudentDetails();
                        break;
                    case 5:
                        studentOperation.removeStudent();
                        break;
                    case 6:
                        studentOperation.printGraduateStudents();
                        break;
                    case 7:
                        System.out.println("Exiting program. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
                choice = 0;
            }
        } while (choice != 7);
        
        scanner.close();
    }
}