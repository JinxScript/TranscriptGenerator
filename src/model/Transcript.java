/**
 *
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Transcript {
    private Student student;
    private List<Module> modules;
    private double SGPA;
    private double CGPA;

    // Constructor
    public Transcript(Student student) {
        this.student = student;
        this.modules = new ArrayList<>();
    }

    // Add module to the transcript
    public void addModule(Module module) {
        modules.add(module);
    }

    // Calculate SGPA
    public void calculateSGPA() {
        double totalPoints = 0;
        int totalCredits = 0;
        for (Module module : modules) {
            totalPoints += module.getModuleMark() * module.getNumberOfCredits();
            totalCredits += module.getNumberOfCredits();
        }
        this.SGPA = totalCredits > 0 ? totalPoints / totalCredits : 0;
    }

    // Calculate CGPA
    public void calculateCGPA() {
        // For simplicity, using SGPA as CGPA (modify logic if required)
        this.CGPA = this.SGPA;
    }

    // Generate Transcript in String format
    public String generateTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Student Transcript\n");
        transcript.append("Full Name: ").append(student.getFullnames()).append("\n");
        transcript.append("Programme: ").append(student.getProgramme()).append("\n");
        transcript.append("Year of Study: ").append(student.getYearOfStudy()).append("\n");
        transcript.append("Student ID: ").append(student.getStudentId()).append("\n\n");
        transcript.append("Modules:\n");
        for (Module module : modules) {
            transcript.append(module.toString()).append("\n");
        }
        transcript.append("\nSGPA: ").append(String.format("%.2f", SGPA));
        transcript.append("\nCGPA: ").append(String.format("%.2f", CGPA));
        return transcript.toString();
    }

    // Getters and Setters
    public Student getStudent() {
        return student;
    }

    public List<Module> getModules() {
        return modules;
    }

    public double getSGPA() {
        return SGPA;
    }

    public double getCGPA() {
        return CGPA;
    }
}
