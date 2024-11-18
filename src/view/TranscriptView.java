
/**
 *
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package view;

import model.Module;
import model.Student;
import model.Transcript;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TranscriptView {
    private final JTextArea transcriptArea; // Display area for transcript
    private final JTextField studentIdField; // Input for student ID
    private final JButton viewTranscriptButton; // Button to view transcript
    private final JButton saveTranscriptButton; // Button to save transcript

    private final List<Student> students = new ArrayList<>(); // List of students
    private final List<Module> modules = new ArrayList<>(); // List of modules

    public TranscriptView() {
        JFrame frame = new JFrame("Transcript Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Transcript Management Portal", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Transcript area
        transcriptArea = new JTextArea(15, 30);
        transcriptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transcriptArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transcript"));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        studentIdField = new JTextField(20);
        inputPanel.add(new JLabel("Enter Student ID:"));
        inputPanel.add(studentIdField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        viewTranscriptButton = new JButton("View Transcript");
        saveTranscriptButton = new JButton("Save Transcript");
        buttonPanel.add(viewTranscriptButton);
        buttonPanel.add(saveTranscriptButton);

        // Add listeners
        viewTranscriptButton.addActionListener(e -> viewTranscript());
        saveTranscriptButton.addActionListener(e -> saveTranscript());

        // Add components to frame
        frame.add(title, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void viewTranscript() {
        String studentId = studentIdField.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Search for the student
        Student student = findStudentById(studentId);

        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create transcript for the student
        Transcript transcript = new Transcript(student);
        modules.forEach(transcript::addModule); // Add modules to the transcript

        transcript.calculateSGPA();
        transcript.calculateCGPA();

        transcriptArea.setText(transcript.generateTranscript());
    }

    private void saveTranscript() {
        String transcriptText = transcriptArea.getText();

        if (transcriptText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No transcript to save. Please generate a transcript first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter("transcript.txt")) {
            writer.write(transcriptText);
            JOptionPane.showMessageDialog(null, "Transcript saved successfully as transcript.txt.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving transcript: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Student findStudentById(String studentId) {
        // Dummy logic for demonstration; replace with actual database or backend call
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TranscriptView::new);
    }
}
