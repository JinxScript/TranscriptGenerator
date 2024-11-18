package view;

import control.GradeCalculator;
import model.Module;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentView {
    private final JTextArea transcriptArea;
    private final JTextField studentIdField;
    private final GradeCalculator gradeCalculator;

    public StudentView() {
        gradeCalculator = new GradeCalculator();

        // Setup frame
        JFrame frame = new JFrame("Student Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Student Transcript Portal", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Transcript area
        transcriptArea = new JTextArea(15, 30);
        transcriptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transcriptArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transcript"));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        studentIdField = new JTextField();
        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(studentIdField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton viewTranscriptButton = new JButton("View Transcript");
        buttonPanel.add(viewTranscriptButton);

        // Add button listener
        viewTranscriptButton.addActionListener(e -> viewTranscript());

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

        try {
            List<Module> modules = gradeCalculator.fetchModules(studentId);

            if (modules == null || modules.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No modules found for this student.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double sgpa = gradeCalculator.calculateSGPA(modules);
            transcriptArea.setText("Transcript for Student ID: " + studentId + "\n");
            transcriptArea.append("Modules:\n");
            for (Module module : modules) {
                transcriptArea.append(module.toString() + "\n");
            }
            transcriptArea.append(String.format("\nSGPA: %.2f\n", sgpa));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while fetching the transcript. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
