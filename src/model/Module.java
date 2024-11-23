/**
 *
 * @author Omolemo Tshwaolesele
 * @studentID 22001043
 */
package model;

public class Module {
    private String moduleCode;
    private String moduleName;
    private double moduleMark;
    private int numberOfCredits;
    private int moduleYear;
    private int moduleSemester;

    // Constructor
    public Module(String moduleCode, String moduleName, double moduleMark, int numberOfCredits, int moduleYear, int moduleSemester) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.moduleMark = moduleMark;
        this.numberOfCredits = numberOfCredits;
        this.moduleYear = moduleYear;
        this.moduleSemester = moduleSemester;
    }

    // Getters and Setters
    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public double getModuleMark() {
        return moduleMark;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public int getModuleYear() {
        return moduleYear;
    }

    public int getModuleSemester() {
        return moduleSemester;
    }

    // Method to determine the grade letter
    public String getGradeLetter() {
        if (moduleMark >= 90) return "A+";
        else if (moduleMark >= 80) return "A";
        else if (moduleMark >= 70) return "B";
        else if (moduleMark >= 60) return "C";
        else if (moduleMark >= 50) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleCode='" + moduleCode + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", moduleMark=" + moduleMark +
                ", numberOfCredits=" + numberOfCredits +
                ", moduleYear=" + moduleYear +
                ", moduleSemester=" + moduleSemester +
                ", grade='" + getGradeLetter() + '\'' +
                '}';
    }
}
