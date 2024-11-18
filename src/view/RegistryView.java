package view;

import control.RegistryController;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegistryView {
    private final JTextArea studentListArea;
    private final RegistryController registryController;

    public RegistryView() {
        registryController = new RegistryController();

        // Setup frame
        JFrame frame = new JFrame("Registry Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Registry Management Portal", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Student list area
        studentListArea = new JTextArea(15, 30);
        studentListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(studentListArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student List"));

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton viewStudentsButton = new JButton("View Students");
        JButton addStudentButton = new JButton("Add Student");
        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(addStudentButton);

        // Add button listeners
        viewStudentsButton.addActionListener(e -> displayStudents());
        addStudentButton.addActionListener(e -> openAddStudentDialog());

        // Add components to frame
        frame.add(title, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void displayStudents() {
        List<Student> students = registryController.getAllStudents();
        studentListArea.setText(""); // Clear previous data
        if (students.isEmpty()) {
            studentListArea.append("No students found.\n");
            return;
        }
        for (Student student : students) {
            studentListArea.append(student.toString() + "\n");
        }
    }

    private void openAddStudentDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add Student");
        dialog.setLayout(new GridLayout(6, 2, 5, 5));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        // Input fields
        JTextField fullNameField = new JTextField();
        JTextField programmeField = new JTextField();
        JTextField yearOfStudyField = new JTextField();
        JTextField studentIdField = new JTextField();
        JTextField dateOfBirthField = new JTextField();

        dialog.add(new JLabel("Full Name:"));
        dialog.add(fullNameField);
        dialog.add(new JLabel("Programme:"));
        dialog.add(programmeField);
        dialog.add(new JLabel("Year of Study:"));
        dialog.add(yearOfStudyField);
        dialog.add(new JLabel("Student ID:"));
        dialog.add(studentIdField);
        dialog.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dialog.add(dateOfBirthField);

        // Buttons
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                Student student = new Student(
                        fullNameField.getText().trim(),
                        programmeField.getText().trim(),
                        Integer.parseInt(yearOfStudyField.getText().trim()),
                        studentIdField.getText().trim(),
                        dateOfBirthField.getText().trim()
                );
                registryController.addStudent(student);
                JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                dialog.dispose(); // Close dialog
                displayStudents(); // Refresh student list
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input. Please check details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(saveButton);
        dialog.add(cancelButton);
        dialog.setVisible(true);
    }
}
