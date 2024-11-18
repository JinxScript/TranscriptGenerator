package control;

import model.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GradeCalculator {
    private final DatabaseConnector dbConnector;

    public GradeCalculator() {
        dbConnector = new DatabaseConnector();
    }

    public List<Module> fetchModules(String studentId) {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT m.module_code, m.module_name, sm.module_mark, m.credits, m.year, m.semester " +
                       "FROM student_modules sm " +
                       "JOIN modules m ON sm.module_id = m.module_id " +
                       "WHERE sm.student_id = ?";

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
                        resultSet.getInt("year"),
                        resultSet.getInt("semester")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modules;
    }

    public double calculateSGPA(List<Module> modules) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Module module : modules) {
            totalPoints += module.getModuleMark() * module.getNumberOfCredits();
            totalCredits += module.getNumberOfCredits();
        }

        return totalCredits > 0 ? totalPoints / totalCredits : 0;
    }
}
