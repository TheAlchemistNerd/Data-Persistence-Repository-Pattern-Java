package repository;

import jakarta.persistence.*;
import model.Student;

import java.util.List;

public class JpaStudentRepository implements StudentRepository<Student> {
    private final EntityManagerFactory emf;

    public JpaStudentRepository() {
        // The name "SchoolDBPersistenceUnit" must match the one in persistence.xml
        this.emf = Persistence.createEntityManagerFactory("SchoolDBPersistenceUnit");
    }

    @Override
    public void create(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public List<Student> getAll() {
        EntityManager em = emf.createEntityManager();
        // Note: "FROM Student" uses the class name, not the table name.
        TypedQuery<Student> query = em.createQuery("FROM Student", Student.class);
        List<Student> students = query.getResultList();
        em.close();
        return students;
    }

    @Override
    public void update(Student student) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(student); // merge handles both new and existing entities
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(String emailAddress) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // Find the student by email first
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s" +
                                                      " WHERE s.emailAddress = :email",
                                                      Student.class);
        query.setParameter("email", emailAddress);
        try {
            Student student = query.getSingleResult();
            if(student != null) {
                em.remove(student);
            }
        } catch (NoResultException e) {
            System.out.println("Student with email "
                    + emailAddress + " not found.");
        }
        em.getTransaction().commit();
        em.close();
    }

    // A helper method to close the factory
    // when the application exits
    public void close() {
        emf.close();
    }
}
