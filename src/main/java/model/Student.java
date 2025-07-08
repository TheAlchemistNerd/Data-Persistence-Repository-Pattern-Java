package model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
// This strategy creates a separate table for each concrete class
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id; // primary key

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String emailAddress;
    private double gpa;

    public Student(){}


    public Student(String firstName, String lastName, String emailAddress, double gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student [ID: " + id +
                ", Type: " + getClass().getSimpleName() + // Get the actual class name (Student, Undergrad, GradStudent)
                ", Name: " + firstName + " " + lastName +
                ", Email: " + emailAddress +
                ", GPA: " + gpa +
                ']';
    }
}
