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

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public double getModuleMark() {
        return moduleMark;
    }

    public void setModuleMark(double moduleMark) {
        this.moduleMark = moduleMark;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public int getModuleYear() {
        return moduleYear;
    }

    public void setModuleYear(int moduleYear) {
        this.moduleYear = moduleYear;
    }

    public int getModuleSemester() {
        return moduleSemester;
    }

    public void setModuleSemester(int moduleSemester) {
        this.moduleSemester = moduleSemester;
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
                '}';
    }
}
