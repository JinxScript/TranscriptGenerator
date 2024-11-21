
package control;
///ADD DISPOSE FUNCCTION


import model.Student;
import model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private final DatabaseConnector dbConnector;

    public StudentController() {
        dbConnector = new DatabaseConnector();
    }

    // Save or update a student in the database
    public void saveStudent(Student student) {
        String query = "INSERT INTO students (student_id, full_name, programme, year_of_study, date_of_birth) " +
                       "VALUES (?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE " +
                       "full_name = VALUES(full_name), programme = VALUES(programme), " +
                       "year_of_study = VALUES(year_of_study), date_of_birth = VALUES(date_of_birth)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getFullnames());
            statement.setString(3, student.getProgramme());
            statement.setInt(4, student.getYearOfStudy());
            statement.setDate(5, Date.valueOf(student.getDateOfBirth()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving student: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    // Add a module for a specific student
  public void saveModule(String studentId, Module module) {
    // SQL Queries
    String insertModuleQuery = "INSERT INTO modules (module_code, module_name, credits, year, semester) " +
                                "VALUES (?, ?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE module_name = VALUES(module_name), credits = VALUES(credits), " +
                                "year = VALUES(year), semester = VALUES(semester)";
    String linkStudentToModuleQuery = "INSERT INTO student_modules (student_id, module_id) " +
                                       "VALUES (?, (SELECT module_id FROM modules WHERE module_code = ?))";

    try (Connection connection = dbConnector.getConnection()) {
        // Insert or update the module in the modules table
        try (PreparedStatement moduleStmt = connection.prepareStatement(insertModuleQuery)) {
            moduleStmt.setString(1, module.getModuleCode());
            moduleStmt.setString(2, module.getModuleName());
            moduleStmt.setInt(3, module.getNumberOfCredits());
            moduleStmt.setInt(4, module.getModuleYear());
            moduleStmt.setString(5, String.valueOf(module.getModuleSemester()));
            int rowsAffected = moduleStmt.executeUpdate();
            System.out.println("Module table updated: Rows affected = " + rowsAffected);
        }

        // Link the student to the module in the student_modules table
        try (PreparedStatement studentModuleStmt = connection.prepareStatement(linkStudentToModuleQuery)) {
            studentModuleStmt.setString(1, studentId);
            studentModuleStmt.setString(2, module.getModuleCode());
            int rowsAffected = studentModuleStmt.executeUpdate();
            System.out.println("Student-Module link created: Rows affected = " + rowsAffected);
        }

        System.out.println("Module saved and linked to student successfully.");
    } catch (SQLException e) {
        System.out.println("Error while saving module: " + e.getMessage());
        e.printStackTrace();
    }
}



    // Retrieve a student by ID
    public Student getStudentById(String studentId) {
        String query = "SELECT * FROM students WHERE student_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("full_name"),
                        resultSet.getString("programme"),
                        resultSet.getInt("year_of_study"),
                        resultSet.getString("student_id"),
                        resultSet.getDate("date_of_birth").toString()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving student: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all modules for a student
    public List<Module> getModulesByStudentId(String studentId) {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM student_modules WHERE student_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                modules.add(new Module(
                        resultSet.getString("module_code"),
                        resultSet.getString("module_name"),
                        resultSet.getDouble("module_mark"),
                        resultSet.getInt("credits"),
                        resultSet.getInt("module_year"),
                        resultSet.getInt("module_semester")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving modules: " + e.getMessage());
            e.printStackTrace();
        }
        return modules;
    }

    // Check if a student exists
    public boolean studentExists(String studentId) {
        String query = "SELECT 1 FROM students WHERE student_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error while checking student existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
