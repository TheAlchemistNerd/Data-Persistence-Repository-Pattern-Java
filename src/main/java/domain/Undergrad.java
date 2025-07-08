package domain;

public class Undergrad extends Student {
    private static final long serialVersionUID = 1L;

    private YearRank yearRank;

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
