/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistryController {
    private final DatabaseConnector dbConnector;

    public RegistryController() {
        dbConnector = new DatabaseConnector();
    }

    // Add a student to the database
    public void addStudent(Student student) {
        String query = "INSERT INTO students (student_id, full_name, programme, year_of_study, date_of_birth) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getFullnames());
            statement.setString(3, student.getProgramme());
            statement.setInt(4, student.getYearOfStudy());
            statement.setDate(5, Date.valueOf(student.getDateOfBirth()));
            statement.executeUpdate();
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
