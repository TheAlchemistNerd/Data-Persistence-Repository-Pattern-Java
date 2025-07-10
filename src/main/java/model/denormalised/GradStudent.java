package model.denormalised;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GRADUATE")
public class GradStudent extends Student {

    private boolean hasFacultyAdvisor;
    private boolean hasTuitionCredit;

    private GradStudent(){}

    public GradStudent(String firstName, String lastName,
                       String emailAddress, double gpa,
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
        // Call the superclass toString() and add specific properties
        return super.toString().replace("]", ", Advisor: " + hasFacultyAdvisor + ", Credit: " + hasTuitionCredit + "]");
    }
}
