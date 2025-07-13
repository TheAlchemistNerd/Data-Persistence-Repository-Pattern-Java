package model.normalised;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import model.YearRank;

@Entity
@Table(name = "undergrads") // Specific table for Undergrad attributes
@PrimaryKeyJoinColumn(name = "student_id") // PK of this table is also FK to students.id
public class Undergrad extends Student {
    @Enumerated(EnumType.STRING)
    private YearRank yearRank;

    public Undergrad(){}

    public Undergrad(String firstName, String lastName,
                     String emailAddress, double gpa,
                     YearRank yearRank) {
        super(firstName, lastName, emailAddress, gpa);
        this.yearRank = yearRank;
    }

    public Undergrad(String firstName, String lastName, String emailAddress, double gpa, YearRank yearRank, Address address) {
        super(firstName, lastName, emailAddress, gpa, address);
        this.yearRank = yearRank;
    }

    public YearRank getYearRank() { return yearRank; }
    public void setYearRank(YearRank yearRank) { this.yearRank = yearRank; }

    @Override
    public String toString() {
        return super.toString().replace("]", ", Rank: " + yearRank + "]");
    }
}
