package model.denormalised;

import jakarta.persistence.*;

@Entity
@Table(name = "students_denormalised")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "student_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("STUDENT")
public class Student {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String emailAddress;
    private double gpa;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)   // Relationship mapping
    @JoinColumn(name = "address_id", unique = true) // Foreign key Column
    private Address address;

    public Student(){}

    public Student(String firstName, String lastName, String emailAddress, double gpa){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.gpa = gpa;
    }

    public Student(String firstName, String lastName, String emailAddress, double gpa, Address address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.gpa = gpa;
        this.address = address;
    }

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
                (address != null ? ", Address: {" + address.toStringForStudent() + "}" : "") + // Avoid infinite loop with Address.toString()
                ']';
    }
}
