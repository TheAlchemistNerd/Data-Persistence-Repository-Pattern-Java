package model.normalised;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "grad_students") // Specific table for GradStudent attributes
@PrimaryKeyJoinColumn(name = "student_id") // PK of this table is also FK to students.id
public class GradStudent extends Student {
    private boolean hasFacultyAdvisor;
    private boolean hasTuitionCredit;

    public GradStudent(){}

    public GradStudent(String firstName, String lastName, String emailAddress, double gpa,
                       boolean hasFacultyAdvisor, boolean hasTuitionCredit) {
        super(firstName, lastName, emailAddress, gpa);
        this.hasFacultyAdvisor = hasFacultyAdvisor;
        this.hasTuitionCredit = hasTuitionCredit;
    }

    public GradStudent(String firstName, String lastName, String emailAddress, double gpa, boolean hasFacultyAdvisor, boolean hasTuitionCredit, Address address) {
        super(firstName, lastName, emailAddress, gpa, address);
        this.hasFacultyAdvisor = hasFacultyAdvisor;
        this.hasTuitionCredit = hasTuitionCredit;
    }

    public boolean hasFacultyAdvisor() { return hasFacultyAdvisor; }
    public void setFacultyAdvisor(boolean hasFacultyAdvisor) { this.hasFacultyAdvisor = hasFacultyAdvisor; }
    public boolean hasTuitionCredit() { return hasTuitionCredit; }
    public void setTuitionCredit(boolean hasTuitionCredit) { this.hasTuitionCredit = hasTuitionCredit; }

    @Override
    public String toString() {
        return super.toString().replace("]", ", Advisor: " + hasFacultyAdvisor + ", Credit: " + hasTuitionCredit + "]");
    }
}
