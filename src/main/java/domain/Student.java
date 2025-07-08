package domain;

import java.io.Serializable;

public class Student  implements Serializable {
    // A unique version ID for serialization
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String emailAddress;

    private double gpa;

    public Student(String firstName, String lastName,
                   String emailAddress, double gpa)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.gpa = gpa;
    }

    // Getters and Setters for all fields
    public String getFirstName() { return firstName; }
    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return emailAddress.equals(student.emailAddress);
    }

    @Override
    public String toString() {
        return "Student [ Type: " + getClass().getSimpleName() + // Get the actual class name (Student, Undergrad, GradStudent)
                ", Name: " + firstName + " " + lastName +
                ", Email: " + emailAddress +
                ", GPA: " + gpa +
                ']';
    }
}
