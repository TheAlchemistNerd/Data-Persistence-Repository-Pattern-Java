package model.normalised;

import jakarta.persistence.*;

@Entity
@Table(name = "students") // Main table for common student attributes
@Inheritance(strategy = InheritanceType.JOINED)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String emailAddress;
    private double gpa;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    public Student(){}

    public Student(String firstName, String lastName, String emailAddress, double gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.gpa = gpa;
    }

    public Student(String firstName, String lastName, String emailAddress, double gpa, Address address) {
        this(firstName, lastName, emailAddress, gpa);
        this.address = address;
    }

    // Getters and Setters (existing)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    @Override
    public String toString() {
        return "Student [ID: " + id +
                ", Type: " + getClass().getSimpleName() +
                ", Name: " + firstName + " " + lastName +
                ", Email: " + emailAddress +
                ", GPA: " + gpa +
                (address != null ? ", Address: {" + address.toStringForStudent() + "}" : "") +
                ']';
    }
}
