package view;

import control.StudentController;
import model.Module;
import model.Student;
import model.Transcript;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class StudentView {

    private JTextField fullNameField;
    private JTextField programmeField;
    private JTextField yearOfStudyField;
    private JTextField studentIdField;
    private JTextField dateOfBirthField;

    private JTextField moduleCodeField;
    private JTextField moduleNameField;
    private JTextField moduleMarkField;
    private JTextField numberOfCreditsField;
    private JTextField moduleYearField;
    private JTextField moduleSemesterField;

    private final JTextArea transcriptArea;
    private final List<Module> modules = new ArrayList<>();
    private Student student;
    private final StudentController studentController;

    public StudentView() {
        this.studentController = new StudentController(); // Initialize controller

        // Setup the frame
        JFrame frame = new JFrame("Student Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Student Management Portal", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panels
        JPanel personalDetailsPanel = createPersonalDetailsPanel();
        JPanel modulePanel = createModulePanel();
        JPanel buttonPanel = createButtonPanel();

        // Transcript Area
        transcriptArea = new JTextArea(10, 30);
        transcriptArea.setEditable(false);
        JScrollPane transcriptScrollPane = new JScrollPane(transcriptArea);

        // Add components to frame
        frame.add(title, BorderLayout.NORTH);
        frame.add(personalDetailsPanel, BorderLayout.WEST);
        frame.add(modulePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(transcriptScrollPane, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createPersonalDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        fullNameField = new JTextField(20);
        programmeField = new JTextField(20);
        yearOfStudyField = new JTextField(20);
        studentIdField = new JTextField(20);
        dateOfBirthField = new JTextField(20);

        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Programme:"));
        panel.add(programmeField);
        panel.add(new JLabel("Year of Study:"));
        panel.add(yearOfStudyField);
        panel.add(new JLabel("Student ID:"));
        panel.add(studentIdField);
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(dateOfBirthField);

        return panel;
    }

    private JPanel createModulePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        moduleCodeField = new JTextField(20);
        moduleNameField = new JTextField(20);
        moduleMarkField = new JTextField(20);
        numberOfCreditsField = new JTextField(20);
        moduleYearField = new JTextField(20);
        moduleSemesterField = new JTextField(20);

        panel.add(new JLabel("Module Code:"));
        panel.add(moduleCodeField);
        panel.add(new JLabel("Module Name:"));
        panel.add(moduleNameField);
        panel.add(new JLabel("Module Mark:"));
        panel.add(moduleMarkField);
        panel.add(new JLabel("Credits:"));
        panel.add(numberOfCreditsField);
        panel.add(new JLabel("Module Year:"));
        panel.add(moduleYearField);
        panel.add(new JLabel("Module Semester:"));
        panel.add(moduleSemesterField);

        return panel;
    }

   private JPanel createButtonPanel() {
    JPanel panel = new JPanel();

    JButton saveDetailsButton = new JButton("Save Details");
    JButton addModuleButton = new JButton("Add Module");
    JButton calculateSGPABtn = new JButton("Calculate SGPA");
    JButton generateTranscriptButton = new JButton("Generate Transcript");
    JButton generateTxtTranscriptButton = new JButton("Generate Transcript (TXT)");
    JButton generatePdfTranscriptButton = new JButton("Generate Transcript (PDF)");

    JButton logoutButton = new JButton("Logout");

    
    saveDetailsButton.addActionListener(e -> savePersonalDetails());
    addModuleButton.addActionListener(e -> addModule());
    calculateSGPABtn.addActionListener(e -> calculateSGPA());
     generateTxtTranscriptButton.addActionListener(e -> generateTranscriptAsTxt());
        generatePdfTranscriptButton.addActionListener(e -> generateTranscriptAsPdf());
    generateTranscriptButton.addActionListener(e -> generateTranscript());
    
    logoutButton.addActionListener(e -> logout());

    panel.add(saveDetailsButton);
    panel.add(addModuleButton);
    panel.add(calculateSGPABtn);
    panel.add(generateTranscriptButton);
            panel.add(generateTxtTranscriptButton);
        panel.add(generatePdfTranscriptButton);
    panel.add(logoutButton);

    return panel;
}

private void logout() {
    // Confirm logout
    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
    if (choice == JOptionPane.YES_OPTION) {
        // Close the current frame
        SwingUtilities.getWindowAncestor(fullNameField).dispose();
        // Redirect to LoginView
        new LoginView();
    }
}


    private void savePersonalDetails() {
        try {
            String fullName = fullNameField.getText().trim();
            String programme = programmeField.getText().trim();
            int yearOfStudy = Integer.parseInt(yearOfStudyField.getText().trim());
            String studentId = studentIdField.getText().trim();
            LocalDate dob = LocalDate.parse(dateOfBirthField.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);

            student = new Student(fullName, programme, yearOfStudy, studentId, dob.toString());
            studentController.saveStudent(student); // Save to database
            JOptionPane.showMessageDialog(null, "Personal details saved successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please check your details.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

private void addModule() {
    try {
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Please save personal details first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate input fields
        String moduleCode = moduleCodeField.getText().trim();
        String moduleName = moduleNameField.getText().trim();
        String moduleMarkStr = moduleMarkField.getText().trim();
        int credits = Integer.parseInt(numberOfCreditsField.getText().trim());
        int year = Integer.parseInt(moduleYearField.getText().trim());
        int semester = Integer.parseInt(moduleSemesterField.getText().trim());

        if (moduleCode.isEmpty() || moduleName.isEmpty() || moduleMarkStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all module details, including marks.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double moduleMark = Double.parseDouble(moduleMarkStr);

        // Create module and save to database
        Module module = new Module(moduleCode, moduleName, moduleMark, credits, year, semester);
        studentController.saveModule(student.getStudentId(), module);

        // Add to in-memory list
        modules.add(module);
        JOptionPane.showMessageDialog(null, "Module added successfully!");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for marks, credits, year, and semester.", "Input Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "An error occurred while adding the module.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}



// GENERATING A TRANSCRIPT AS TXT
 private void generateTranscriptAsTxt() {
    if (student == null) {
        JOptionPane.showMessageDialog(null, "No student details available to generate transcript.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String fileName = student.getStudentId() + "_transcript.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        writer.write("TRANSCRIPT OF ACADEMIC RECORD\n");
        writer.write("_________________________________________________________________________________________________\n");
        writer.write("Student Name: " + student.getFullnames() + "\n");
        writer.write("Student ID: " + student.getStudentId() + "\n");
        writer.write("Date of Birth: " + student.getDateOfBirth() + "\n\n");

        // Get unique years and semesters from the modules
        Set<Integer> years = new HashSet<>();
        Set<Integer> semesters = new HashSet<>();
        for (Module module : modules) {
            years.add(module.getModuleYear());
            semesters.add(module.getModuleSemester());
        }

        // Iterate over years and semesters
        for (Integer year : years) {
            for (Integer semester : semesters) {
                // Replace with the student's program dynamically
                writer.write(year + " - " + (year + 1) + " SEMESTER " + semester + " " + student.getProgramme() + "\n");
                writer.write("__________________________________________________________________________________________________\n");
                writer.write(String.format("%-20s %-40s %-8s\n", "MODULE CODE", "MODULE DESCRIPTION", "GRADE","CREDITS"));
                writer.write("____________________________________________________________________________________________________\n");

                // Write Modules for the current year and semester
                boolean moduleWritten = false;
                for (Module module : modules) {
                    if (module.getModuleYear() == year && module.getModuleSemester() == semester) {
                        writer.write(String.format("%-20s %-40s %-8.2f %-8s %-8d\n", 
                                                   module.getModuleCode(),
                                                   module.getModuleName(),
                                                   module.getModuleMark(),
                                                   module.getGradeLetter(),
                                                   module.getNumberOfCredits()));
                    }
                }
            }
        }

        // Calculate SGPA and CGPA
        Transcript transcript = new Transcript(student);
        modules.forEach(transcript::addModule);
        transcript.calculateSGPA();
        transcript.calculateCGPA();

       // writer.write("\nResults: Academic Good Standing\n");
        writer.write("SGPA: " + String.format("%.2f", transcript.getSGPA()) + "\n");
        writer.write("CGPA: " + String.format("%.2f", transcript.getCGPA()) + "\n");
        writer.close();

        JOptionPane.showMessageDialog(null, "Transcript generated successfully as: " + fileName);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error generating transcript: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

// GENERATING A TRANSCRIPT AS PDF
private void generateTranscriptAsPdf() {
    if (student == null) {
        JOptionPane.showMessageDialog(null, "No student details available to generate transcript.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String fileName = student.getStudentId() + "_transcript.pdf";

    try {
        // Initialize the PDF document
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        // Add header
        document.add(new com.itextpdf.text.Paragraph("TRANSCRIPT OF ACADEMIC RECORD"));
        document.add(new com.itextpdf.text.Paragraph("_____________________________________________________"));
        document.add(new com.itextpdf.text.Paragraph("Student Name: " + student.getFullnames()));
        document.add(new com.itextpdf.text.Paragraph("Student ID: " + student.getStudentId()));
        document.add(new com.itextpdf.text.Paragraph("Date of Birth: " + student.getDateOfBirth()));
        document.add(new com.itextpdf.text.Paragraph("\n\n"));

        // Get unique years and semesters from the modules
        Set<Integer> years = new HashSet<>();
        Set<Integer> semesters = new HashSet<>();
        for (Module module : modules) {
            years.add(module.getModuleYear());
            semesters.add(module.getModuleSemester());
        }

        // Iterate over years and semesters
        for (Integer year : years) {
            for (Integer semester : semesters) {
                // Replace with the student's program dynamically
                document.add(new com.itextpdf.text.Paragraph(year + " - " + (year + 1) + " SEMESTER " + semester + " " + student.getProgramme()));
                document.add(new com.itextpdf.text.Paragraph("________________________________________________________"));
                document.add(new com.itextpdf.text.Paragraph(String.format("%-20s %-40s %-8s %-8s %-8s", 
                    "MODULE CODE", "MODULE DESCRIPTION", "MARKS", "GRADE", "CREDITS")));

                // Write Modules for the current year and semester
                boolean moduleWritten = false;
                for (Module module : modules) {
                    if (module.getModuleYear() == year && module.getModuleSemester() == semester) {
                        document.add(new com.itextpdf.text.Paragraph(String.format("%-20s %-40s %-8.2f %-8s %-8d", 
                            module.getModuleCode(),
                            module.getModuleName(),
                            module.getModuleMark(),
                            module.getGradeLetter(),
                            module.getNumberOfCredits())));
                        moduleWritten = true;
                    }
                }

                if (!moduleWritten) {
                    document.add(new com.itextpdf.text.Paragraph("No modules found for this semester."));
                }
            }
        }

        // Add SGPA and CGPA
        Transcript transcript = new Transcript(student);
        modules.forEach(transcript::addModule);
        transcript.calculateSGPA();
        transcript.calculateCGPA();

        document.add(new com.itextpdf.text.Paragraph("\nResults: Academic Good Standing"));
        document.add(new com.itextpdf.text.Paragraph("SGPA: " + String.format("%.2f", transcript.getSGPA())));
        document.add(new com.itextpdf.text.Paragraph("CGPA: " + String.format("%.2f", transcript.getCGPA())));

        document.close();
        JOptionPane.showMessageDialog(null, "Transcript generated successfully as: " + fileName);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}




  private void calculateSGPA() {
    Transcript transcript = new Transcript(student);
    modules.forEach(transcript::addModule);

    transcript.calculateSGPA(); // Recalculates SGPA for all added modules
    JOptionPane.showMessageDialog(null, "SGPA: " + String.format("%.2f", transcript.getSGPA()));
}


    private void generateTranscript() {
        Transcript transcript = new Transcript(student);
        modules.forEach(transcript::addModule);

        transcript.calculateSGPA();
        transcript.calculateCGPA();

        transcriptArea.setText(transcript.generateTranscript());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentView::new);
    }
}
