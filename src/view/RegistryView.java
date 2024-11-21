/**
 * Registry View for managing student data and transcripts.
 * 
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package view;

import control.RegistryController;
import model.Module;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RegistryView {
    private JTextArea studentListArea; // To display all students
    private JTextArea transcriptArea; // To display transcripts
    private JTextField studentIdField; // Input for student ID
    private final RegistryController registryController; // Controller for database operations

    public RegistryView() {
        registryController = new RegistryController();

        // Main frame setup
        JFrame frame = new JFrame("Registry Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Registry Management Portal", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Panels
        JPanel studentListPanel = createStudentListPanel();
        JPanel transcriptPanel = createTranscriptPanel();
        JPanel buttonPanel = createButtonPanel();

        // Add components to the frame
        frame.add(title, BorderLayout.NORTH);
        frame.add(studentListPanel, BorderLayout.WEST);
        frame.add(transcriptPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createStudentListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Student List Area
        studentListArea = new JTextArea(20, 30);
        studentListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(studentListArea);

        panel.add(new JLabel("All Students"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTranscriptPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Transcript Area
        transcriptArea = new JTextArea(20, 30);
        transcriptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transcriptArea);

        // Student ID Input
        studentIdField = new JTextField(20);
        JPanel idInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idInputPanel.add(new JLabel("Enter Student ID:"));
        idInputPanel.add(studentIdField);

        panel.add(idInputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Buttons
        JButton viewStudentsButton = new JButton("View All Students");
        JButton viewTranscriptButton = new JButton("View Transcript");
        JButton saveTranscriptButton = new JButton("Save Transcript");

        // Add button listeners
        viewStudentsButton.addActionListener(e -> viewAllStudents());
        viewTranscriptButton.addActionListener(e -> viewTranscript());
        saveTranscriptButton.addActionListener(e -> saveTranscript());

        panel.add(viewStudentsButton);
        panel.add(viewTranscriptButton);
        panel.add(saveTranscriptButton);

        return panel;
    }

    private void viewAllStudents() {
        studentListArea.setText(""); // Clear the area
        List<Student> students = registryController.getAllStudents();
        if (students.isEmpty()) {
            studentListArea.append("No students found.\n");
            return;
        }
        for (Student student : students) {
            studentListArea.append(student.toString() + "\n");
        }
    }

    private void viewTranscript() {
        transcriptArea.setText(""); // Clear the area
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = registryController.findStudentById(studentId);
        if (student == null) {
            transcriptArea.append("Student not found.\n");
            return;
        }

        List<Module> modules = registryController.getModulesByStudentId(studentId);
        if (modules.isEmpty()) {
            transcriptArea.append("No modules found for this student.\n");
            return;
        }

        transcriptArea.append("Transcript for Student: " + student.getFullnames() + " (ID: " + studentId + ")\n");
        transcriptArea.append("Programme: " + student.getProgramme() + "\n");
        transcriptArea.append("Year of Study: " + student.getYearOfStudy() + "\n\n");
        transcriptArea.append("Modules:\n");

        for (Module module : modules) {
            transcriptArea.append(module.toString() + "\n");
        }
    }

    private void saveTranscript() {
        String transcript = transcriptArea.getText();
        if (transcript.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No transcript to save.", "Save Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String studentId = studentIdField.getText().trim();
        String filename = "transcript_" + studentId + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(transcript);
            JOptionPane.showMessageDialog(null, "Transcript saved to " + filename, "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving transcript: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistryView::new);
    }
}
