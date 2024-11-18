/**
 *
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package model;

public class Student {

    private String fullnames;
    private String programme;
    private int yearOfStudy;
    private String studentId;
    private String dateOfBirth;

    // Constructor
    public Student(String fullnames, String programme, int yearOfStudy, String studentId, String dateOfBirth) {
        this.fullnames = fullnames;
        this.programme = programme;
        this.yearOfStudy = yearOfStudy;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters
    public String getFullnames() {
        return fullnames;
    }

    public void setFullnames(String fullnames) {
        this.fullnames = fullnames;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student{"
                + "fullnames='" + fullnames + '\''
                + ", programme='" + programme + '\''
                + ", yearOfStudy=" + yearOfStudy
                + ", studentId='" + studentId + '\''
                + ", dateOfBirth='" + dateOfBirth + '\''
                + '}';
    }
}
