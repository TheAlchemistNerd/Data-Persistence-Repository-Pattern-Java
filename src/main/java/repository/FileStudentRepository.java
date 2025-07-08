package repository;

import domain.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileStudentRepository implements StudentRepository<Student> {
    private final String fileName;

    public FileStudentRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void create(Student student) {
        List<Student> students = getAll();
        students.add(student);
        saveAll(students);
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        // Check if the file exists and is not empty to avoid EOFException on first read
        File file = new File(fileName);
        if(!file.exists() || file.length() == 0) {
            return students;
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // For a real app, use a proper logging framework
            e.printStackTrace();
        }
        return students;
    }

    private void saveAll(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student studentToUpdate) {
        List<Student> students = getAll();
        // Find the student by email and replace them
        List<Student> updatedList = students.stream()
                .map(s -> s.getEmailAddress().equals(studentToUpdate.getEmailAddress()) ? studentToUpdate : s)
                .collect(Collectors.toList());
        saveAll(updatedList);
    }

    @Override
    public void delete(String emailAddress) {
        List<Student> students = getAll();
        // Remove the student that matches the email address
        students.removeIf(student -> student.getEmailAddress().equals(emailAddress));
        saveAll(students);
    }
}
