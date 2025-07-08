package model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "undergrads")
public class Undergrad extends Student {
    @Enumerated(EnumType.STRING)  // Store the enum name (e.g., "SOPHOMORE") in the DB
    private YearRank yearRank;

    public Undergrad(){}

    public Undergrad(String firstName, String lastName,
                     String emailAddress, double gpa,
                     YearRank yearRank) {
        super(firstName, lastName, emailAddress, gpa);
        this.yearRank = yearRank;
    }

    public YearRank getYearRank() { return yearRank; }
    public void setYearRank(YearRank yearRank) { this.yearRank = yearRank; }

    @Override
    public String toString() {
        // Call the superclass toString() and add specific properties
        return super.toString().replace("]", ", Rank: " + yearRank + "]");
    }
}
