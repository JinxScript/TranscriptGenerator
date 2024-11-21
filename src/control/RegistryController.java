package control;

import model.Module;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistryController {
    private final DatabaseConnector dbConnector;

    public RegistryController() {
        dbConnector = new DatabaseConnector();
    }

    // Add or update a student in the database
    public void addStudent(Student student) {
        String query = "INSERT INTO students (student_id, full_name, programme, year_of_study, date_of_birth) " +
                       "VALUES (?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), programme = VALUES(programme), " +
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
            System.out.println("Error while adding/updating student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a module for a specific student
    public void addModule(String studentId, Module module) {
        String query = "INSERT INTO student_modules (student_id, module_code, module_name, module_mark, credits, module_year, module_semester) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentId);
            statement.setString(2, module.getModuleCode());
            statement.setString(3, module.getModuleName());
            statement.setDouble(4, module.getModuleMark());
            statement.setInt(5, module.getNumberOfCredits());
            statement.setInt(6, module.getModuleYear());
            statement.setInt(7, module.getModuleSemester());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding module: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Fetch all students from the database
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getString("full_name"),
                        resultSet.getString("programme"),
                        resultSet.getInt("year_of_study"),
                        resultSet.getString("student_id"),
                        resultSet.getDate("date_of_birth").toString()
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching all students: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    // Find a student by ID
    public Student findStudentById(String studentId) {
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
            System.out.println("Error while fetching student by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Fetch all modules for a specific student
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
            System.out.println("Error while fetching modules: " + e.getMessage());
            e.printStackTrace();
        }
        return modules;
    }
}
