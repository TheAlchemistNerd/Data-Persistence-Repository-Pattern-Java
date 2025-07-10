package model.denormalised;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import model.YearRank;

@Entity
@DiscriminatorValue("UNDERGRAD")
public class Undergrad extends Student {

    @Enumerated(EnumType.STRING)
    private YearRank yearRank;

    private Undergrad(){}

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
        // Call the superclass toString() and add specific properties
        return super.toString().replace("]", ", Rank: " + yearRank + "]");
    }
}
