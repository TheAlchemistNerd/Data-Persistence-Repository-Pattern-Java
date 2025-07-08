import domain.GradStudent;    // Imports domain.GradStudent
import domain.Student;       // Imports domain.Student
import domain.Undergrad;     // Imports domain.Undergrad
import domain.YearRank;      // Imports domain.YearRank

import model.*; // Imports all classes from the 'model' package.

import repository.FileStudentRepository;
import repository.JpaStudentRepository;
import repository.StudentRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("--- Demonstrating FileStudentRepository (using domain classes) ---");

        String fileName = "students.dat";
        StudentRepository<Student> fileStudentRepository = new FileStudentRepository(fileName);

        System.out.println("--- Creating Students in File ---");
        fileStudentRepository.create(new Undergrad("Jane", "Doe",
                "jane.doe@example.com",
                3.8, YearRank.SOPHOMORE));
        fileStudentRepository.create(new GradStudent("David", "Johnson",
                "david.j@example.com",
                4.0, true, true));
        fileStudentRepository.create(new Student("John", "Smith",
                "john.smith@example.com",
                3.5));

        System.out.println("\n--- Retrieving All Students from File ---");
        List<Student> allDomainStudents = fileStudentRepository.getAll();
        allDomainStudents.forEach(System.out::println); // This now calls domain.Student's toString()

        System.out.println("\n--- Updating a Student in File ---");
        Student janeToUpdate = new Undergrad("Jane", "Doe", "jane.doe@example.com",
                3.9, YearRank.JUNIOR);
        fileStudentRepository.update(janeToUpdate);

        System.out.println("\n--- Verifying Update in File ---");
        fileStudentRepository.getAll().forEach(System.out::println); // This now calls domain.Student's toString()

        System.out.println("\n--- Deleting a Student from File ---");
        fileStudentRepository.delete("john.smith@example.com");

        System.out.println("\n--- Verifying Deletion from File ---");
        fileStudentRepository.getAll().forEach(System.out::println); // This now calls domain.Student's toString()


        System.out.println("\n\n--- Demonstrating JpaStudentRepository (using model classes) ---");

        JpaStudentRepository jpaStudentRepository = new JpaStudentRepository();

        System.out.println("--- Creating Students in Database ---");
        jpaStudentRepository.create(new model.Undergrad("Peter", "Jones",
                "peter.j@example.com", 3.2, model.YearRank.FRESHMAN));
        jpaStudentRepository.create(new model.GradStudent("Mary", "Jane",
                "mary.j@example.com", 3.9, false, true));
        jpaStudentRepository.create(new model.Student("Alice", "Wonder",
                "alice.w@example.com", 3.7));


        System.out.println("\n--- Retrieving All Students from Database ---");
        List<model.Student> allModelStudents = jpaStudentRepository.getAll();
        allModelStudents.forEach(System.out::println); // THIS IS THE SIMPLIFIED LINE!

        System.out.println("\n--- Updating a Student in Database ---");
        model.Student peterToUpdate = allModelStudents.stream()
                .filter(s -> s.getEmailAddress().equals("peter.j@example.com"))
                .findFirst()
                .orElse(null);

        if (peterToUpdate != null) {
            peterToUpdate.setGpa(3.5);
            if (peterToUpdate instanceof model.Undergrad) {
                ((model.Undergrad) peterToUpdate).setYearRank(model.YearRank.SOPHOMORE);
            }
            jpaStudentRepository.update(peterToUpdate);
            System.out.println("Updated Peter Jones's GPA and rank.");
        } else {
            System.out.println("Peter Jones not found for update.");
        }


        System.out.println("\n--- Verifying Update from Database ---");
        jpaStudentRepository.getAll().forEach(System.out::println); // THIS IS THE SIMPLIFIED LINE!

        System.out.println("\n--- Deleting a Student from Database ---");
        jpaStudentRepository.delete("mary.j@example.com");

        System.out.println("\n--- Verifying Deletion from Database ---");
        jpaStudentRepository.getAll().forEach(System.out::println); // THIS IS THE SIMPLIFIED LINE!

        jpaStudentRepository.close();
    }
}