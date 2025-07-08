package domain;

public class GradStudent extends Student {
    private static final long serialVersionUID =1L;

    private boolean hasFacultyAdvisor;
    private boolean hasTuitionCredit;

    public GradStudent(String firstName, String lastName, String emailAddress, double gpa,
                       boolean hasFacultyAdvisor, boolean hasTuitionCredit) {
        super(firstName, lastName, emailAddress, gpa);
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