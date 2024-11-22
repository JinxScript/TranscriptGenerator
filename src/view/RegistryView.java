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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegistryView {
    private JTextArea studentListArea; // To display all students
    private JTextArea transcriptArea; // To display transcripts
    private JTextField studentIdField; // Input for student ID
    private final RegistryController registryController; // Controller for database operations

    public RegistryView() {
        registryController = new RegistryController();

        JFrame frame = new JFrame("Registry Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel studentListPanel = createStudentListPanel();
        JPanel transcriptPanel = createTranscriptPanel();
        JPanel buttonPanel = createButtonPanel();

        frame.add(new JLabel("Registry Management Portal", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(studentListPanel, BorderLayout.WEST);
        frame.add(transcriptPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createStudentListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        studentListArea = new JTextArea(20, 30);
        studentListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(studentListArea);

        panel.add(new JLabel("All Students"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTranscriptPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        transcriptArea = new JTextArea(20, 30);
        transcriptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transcriptArea);

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
    JButton saveTranscriptTxtButton = new JButton("Save Transcript as TXT");
    JButton saveTranscriptPdfButton = new JButton("Save Transcript as PDF");
    JButton logoutButton = new JButton("Logout");

    // Add button listeners
    viewStudentsButton.addActionListener(e -> viewAllStudents());
    viewTranscriptButton.addActionListener(e -> viewTranscript());
    saveTranscriptTxtButton.addActionListener(e -> generateTranscriptAsTxt());
    saveTranscriptPdfButton.addActionListener(e -> generateTranscriptAsPdf());
    logoutButton.addActionListener(e -> logout());

    panel.add(viewStudentsButton);
    panel.add(viewTranscriptButton);
    panel.add(saveTranscriptTxtButton);
    panel.add(saveTranscriptPdfButton);
    panel.add(logoutButton);

    return panel;
}

private void logout() {
    // Confirm logout
    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
        // Close the current frame
        SwingUtilities.getWindowAncestor(studentListArea).dispose();
        // Redirect to LoginView
        new LoginView();
    }
}


 private void viewAllStudents() {
    studentListArea.setText(""); // Clear the area
    List<Student> students = registryController.getAllStudents();

    if (students.isEmpty()) {
        studentListArea.append("No students found.\n");
        return;
    }

    // Headers with fixed widths
    studentListArea.append(String.format("%-20s %-25s %-10s %-15s %-15s\n", 
            "Full Name", "Programme", "Year", "ID", "Date of Birth"));
    studentListArea.append("--------------------------------------------------------------------------------\n");

    // Rows for each student
    for (Student student : students) {
        studentListArea.append(String.format("%-20s %-25s %-10d %-15s %-15s\n", 
                student.getFullnames(), 
                student.getProgramme(), 
                student.getYearOfStudy(), 
                student.getStudentId(), 
                student.getDateOfBirth()));
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

        Set<Integer> years = new HashSet<>();
        Set<Integer> semesters = new HashSet<>();
        for (Module module : modules) {
            years.add(module.getModuleYear());
            semesters.add(module.getModuleSemester());
        }

        for (Integer year : years) {
            for (Integer semester : semesters) {
                transcriptArea.append(year + " - " + (year + 1) + " SEMESTER " + semester + "\n");
                transcriptArea.append("MODULE CODE\tMODULE NAME\tCREDITS\tMARK\n");
                for (Module module : modules) {
                    if (module.getModuleYear() == year && module.getModuleSemester() == semester) {
                        transcriptArea.append(module.getModuleCode() + "\t" + module.getModuleName() + "\t" +
                                module.getNumberOfCredits() + "\t" + module.getModuleMark() + "\n");
                    }
                }
                transcriptArea.append("\n");
            }
        }
    }

 
    private void generateTranscriptAsTxt() {
        String studentId = studentIdField.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = registryController.findStudentById(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Module> modules = registryController.getModulesByStudentId(studentId);

        String fileName = student.getStudentId() + "_transcript.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("TRANSCRIPT OF ACADEMIC RECORD\n");
            writer.write("_________________________________________________________________________________________________\n");
            writer.write("Student Name: " + student.getFullnames() + "\n");
            writer.write("Student ID: " + student.getStudentId() + "\n");
            writer.write("Date of Birth: " + student.getDateOfBirth() + "\n\n");

            Set<Integer> years = new HashSet<>();
            Set<Integer> semesters = new HashSet<>();
            for (Module module : modules) {
                years.add(module.getModuleYear());
                semesters.add(module.getModuleSemester());
            }

            for (Integer year : years) {
                for (Integer semester : semesters) {
                    writer.write(year + " - " + (year + 1) + " SEMESTER " + semester + " " + student.getProgramme() + "\n");
                    writer.write("__________________________________________________________________________________________________\n");
                    writer.write(String.format("%-20s %-40s %-8s\n", "MODULE CODE", "MODULE DESCRIPTION", "CREDITS"));
                    writer.write("__________________________________________________________________________________________________\n");

                    for (Module module : modules) {
                        if (module.getModuleYear() == year && module.getModuleSemester() == semester) {
                            writer.write(String.format("%-20s %-40s %-8s\n",
                                    module.getModuleCode(),
                                    module.getModuleName(),
                                    module.getNumberOfCredits()));
                        }
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Transcript generated successfully as: " + fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error generating transcript: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void generateTranscriptAsPdf() {
        String studentId = studentIdField.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Student ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = registryController.findStudentById(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Module> modules = registryController.getModulesByStudentId(studentId);

        String fileName = student.getStudentId() + "_transcript.pdf";

        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new com.itextpdf.text.Paragraph("TRANSCRIPT OF ACADEMIC RECORD"));
            document.add(new com.itextpdf.text.Paragraph("_____________________________________________________"));
            document.add(new com.itextpdf.text.Paragraph("Student Name: " + student.getFullnames()));
            document.add(new com.itextpdf.text.Paragraph("Student ID: " + student.getStudentId()));
            document.add(new com.itextpdf.text.Paragraph("Date of Birth: " + student.getDateOfBirth()));
            document.add(new com.itextpdf.text.Paragraph("\n"));

            Set<Integer> years = new HashSet<>();
            Set<Integer> semesters = new HashSet<>();
            for (Module module : modules) {
                years.add(module.getModuleYear());
                semesters.add(module.getModuleSemester());
            }

            for (Integer year : years) {
                for (Integer semester : semesters) {
                    document.add(new com.itextpdf.text.Paragraph(year + " - " + (year + 1) + " SEMESTER " + semester + " " + student.getProgramme()));
                    document.add(new com.itextpdf.text.Paragraph("________________________________________________________"));
                    document.add(new com.itextpdf.text.Paragraph(String.format("%-20s %-40s %-8s",
                            "MODULE CODE", "MODULE DESCRIPTION", "CREDITS")));

                    for (Module module : modules) {
                        if (module.getModuleYear() == year && module.getModuleSemester() == semester) {
                            document.add(new com.itextpdf.text.Paragraph(String.format("%-20s %-40s %-8s",
                                    module.getModuleCode(),
                                    module.getModuleName(),
                                    module.getNumberOfCredits())));
                        }
                    }
                }
            }

            document.close();
            JOptionPane.showMessageDialog(null, "Transcript generated successfully as: " + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistryView::new);
    }
}
